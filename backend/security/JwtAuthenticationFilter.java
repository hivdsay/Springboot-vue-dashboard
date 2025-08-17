package com.example.dashboard_api.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.example.dashboard_api.service.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Her HTTP isteğinde çalışarak gelen JWT token'ı kontrol eder ve kullanıcıyı doğrular.
 *
 * 1. Header’da JWT var mı?
 * 2. JWT geçerli mi?
 * 3. Token’daki kullanıcı gerçekten var mı?
 * 4. Giriş yapılmış gibi işaretle
 * 5. Zinciri devam ettir (doFilter)
 */
@Component // Spring bu sınıfı otomatik olarak bileşen olarak tanır
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenManagerService jwtService;
    private final UserService userService;

    public JwtAuthenticationFilter(TokenManagerService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization"); // Authorization: Bearer xxxxx.yyyyy.zzzzz
        final String jwt;
        final String username;

        // Eğer header yoksa veya "Bearer " ile başlamıyorsa → doğrulama yapılmadan devam edilir
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // "Bearer " sonrası token'ı ayıkla
        jwt = authHeader.substring(7);

        // Token’dan kullanıcı adını (bizde email) çıkar
        username = jwtService.extractUsername(jwt);

        // Eğer token geçerli ve kimlik doğrulama henüz yapılmamışsa
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Kullanıcıyı veritabanından al
            UserDetails userDetails = userService.loadUserByUsername(username);

            // Token gerçekten bu kullanıcıya mı ait ve süresi geçmemiş mi → kontrol et
            if (jwtService.isTokenValid(jwt, userDetails)) {

                // Kimlik doğrulamasını tamamla ve güvenli kullanıcı olarak işaretle
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                // Ek güvenlik bilgilerini bağla (IP vs.)
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Güvenlik bağlamına bu kullanıcıyı yerleştir
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Filtre zincirine devam et (önemli!)
        filterChain.doFilter(request, response);
    }
}
