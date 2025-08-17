package com.example.dashboard_api.repository;

import com.example.dashboard_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * User (kullanıcı) veritabanı işlemlerini yöneten repository arayüzü.
 * Bu repository, veritabanında kullanıcıyı bulmak için Spring Data JPA ile kullanılır.
 *
 * Görevi: E-posta (username) ile kullanıcıyı sorgulamak
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * E-posta adresine göre kullanıcı arar.
     *
     * @param email kullanıcının e-posta adresi
     * @return opsiyonel olarak kullanıcıyı döner
     */
    Optional<User> findByEmail(String email);
}
