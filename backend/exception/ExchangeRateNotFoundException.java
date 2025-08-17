package com.example.dashboard_api.exception;

/**
 * Belirtilen ID ile ExchangeRate bulunamazsa fırlatılan istisna.
 */
public class ExchangeRateNotFoundException extends RuntimeException {

    /**
     * Hata mesajıyla birlikte istisna oluşturur.
     *
     * @param message Kullanıcıya dönecek hata mesajı
     */
    public ExchangeRateNotFoundException(String message) {
        super(message);
    }
}
