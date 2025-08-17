package com.example.dashboard_api.controller;

import com.example.dashboard_api.DTO.AuthenticationRequest;
import com.example.dashboard_api.DTO.AuthenticationResponse;
import com.example.dashboard_api.DTO.RegisterRequest;
import com.example.dashboard_api.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Kullanıcı kayıt ve giriş işlemleri için API uçlarını yöneten controller.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    /**
     * Yeni kullanıcı kaydı için istek alır ve JWT token döner.
     *
     * @param request username ve password içeren DTO
     * @return JWT token içeren cevap
     */
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        AuthenticationResponse response = authenticationService.register(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Kullanıcı giriş isteğini alır, şifre doğruysa token üretir.
     *
     * @param request kullanıcı adı ve şifre içeren DTO
     * @return JWT token içeren cevap
     */
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse response = authenticationService.authenticate(request);
        return ResponseEntity.ok(response);
    }
}
