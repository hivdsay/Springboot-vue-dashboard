package com.example.dashboard_api.service;

import com.example.dashboard_api.DTO.AuthenticationRequest;
import com.example.dashboard_api.DTO.AuthenticationResponse;
import com.example.dashboard_api.DTO.RegisterRequest;
import com.example.dashboard_api.entity.User;
import com.example.dashboard_api.repository.UserRepository;
import com.example.dashboard_api.security.TokenManagerService;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Kullanıcı kayıt ve giriş işlemlerini yöneten servis katmanı.
 * Kullanıcı yeni kayıt olunca onu veritabanına ekler ve token üretir.
 * Kullanıcı giriş yapınca şifresini kontrol eder ve geçerliyse token üretir.
 */
@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenManagerService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            TokenManagerService jwtService,
            AuthenticationManager authenticationManager
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        /** Bu AuthenticationManager, otomatik olarak:

         UserService.loadUserByUsername() metodunu çağırır.

         Veritabanından kullanıcıyı bulur (UserRepository.findByEmail()).

         Şifreyi BCrypt ile karşılaştırır (şifreler encode'lu olduğu için). */
    }

    /**
     * Yeni kullanıcıyı kaydeder ve token üretir.
     * @param request kayıt isteği (isim, email, şifre vs.)
     * @return JWT token içeren cevap
     */
    public AuthenticationResponse register(RegisterRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        // rol eklediğinde: user.setRole(...);

        userRepository.save(user);

        String jwt = jwtService.generateToken(user);
        return new AuthenticationResponse(jwt);
    }

    /**
     * Mevcut kullanıcıyı doğrular ve token üretir.
     *
     * @param request kullanıcı giriş isteği (username + password)
     * @return JWT token içeren cevap
     * Her iki işlem (register ve authenticate) sonunda AuthenticationResponse döner.
     * Bu DTO şu an sadece bir JWT token içerir.
     */
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate( //Kullanıcının kullanıcı adı ve şifresini kontrol eder:
                //authenticationManager.authenticate(...) sayesinde Spring Security bu işi yapar.
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail()) //Kullanıcı geçerliyse onu bulur: userRepository.findByEmail(...)
                .orElseThrow();

        String jwt = jwtService.generateToken(user);
        return new AuthenticationResponse(jwt);
    }
}
