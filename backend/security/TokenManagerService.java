package com.example.dashboard_api.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/*
Bu servis:

- Kullanıcı için token üretir
- Token'dan kullanıcı bilgilerini (email) alır
- Token geçerli mi, süresi dolmuş mu kontrol eder
 */
@Service
public class TokenManagerService {

    //  Rastgele güçlü bir secret key oluştur (HS256 algoritmasına uygun)
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Token içinden kullanıcı ismini (bizde email) çıkarır.
    // subject → genellikle kullanıcı kimliğini temsil eder.
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /*
     Token’ı çözerek (parse) içindeki tüm veri (claim) alanlarını çıkarır.
     Token’ın imzası doğrulanır.
     ⚠️ Sahte veya bozuk token varsa hata fırlatır!
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Yeni token oluşturmak için çağrılır.
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        // Kullanıcının rolünü token içine ekle
        String role = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("ROLE_USER"); // default

        claims.put("role", role); // 🔐 Rol eklendi
        return createToken(claims, userDetails.getUsername());
    }

    // Token içeriği burada belirlenir:
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(subject) // kullanıcının email’i
                .setIssuedAt(new Date(System.currentTimeMillis())) // ne zaman üretildi
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 saat geçerli
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256) // imzalama (güvenlik)
                .compact(); // .compact() ile string token üretilir.
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);

        /* Token’daki kullanıcı adı ile gelen kullanıcı eşleşiyor mu?
           Token süresi dolmuş mu?
           İkisi de doğruysa token geçerli kabul edilir. */
    }

    // Token’ın son kullanma tarihine bakar.
    // Şu anki zamanla karşılaştırır.
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // ➕ Token içindeki rolü almak istersen bu metodu kullanabilirsin
    public String extractUserRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }
}
