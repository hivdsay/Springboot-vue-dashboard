package com.example.dashboard_api.repository;

import com.example.dashboard_api.entity.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * ExchangeRate için veritabanı işlemlerini gerçekleştiren repository arayüzü.
 */
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long>, JpaSpecificationExecutor<ExchangeRate> {
    // JpaRepository → CRUD
    // JpaSpecificationExecutor → dinamik filtreleme desteği
}
