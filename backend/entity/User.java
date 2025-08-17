package com.example.dashboard_api.entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 Nerede Kullanılır?
 UserService.loadUserByUsername() → bu User nesnesini döner.
 AuthenticationService.register() → yeni kullanıcıyı bu sınıfla oluşturur.
 JwtService.generateToken() → user.getUsername() üzerinden token üretir.
 */

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false) //Kullanıcının şifresi. Şifreler hashlenmiş saklanmalı.
    private String password;

    @Column(nullable = false)
    private String role = "ROLE_USER"; // Varsayılan rol

    // --- GETTER & SETTER ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // --- Spring Security gereklilikleri ---

    /**
     * GrantedAuthority: Roller/izinler.
     * Artık kullanıcıya özel dinamik rol döner (örn. ROLE_ADMIN, ROLE_USER)
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }

    /**
     * Kullanıcı adı olarak email döner.
     * Şimdilik her zaman true dönüyoruz. Yani tüm kullanıcılar aktif ve geçerli kabul ediliyor.
     */
    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
