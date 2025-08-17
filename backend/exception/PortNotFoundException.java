package com.example.dashboard_api.exception;

/**
 * Belirtilen ID ile bir port kaydı bulunamadığında fırlatılan özel istisna.
 * RuntimeException türevidir, bu nedenle zorunlu olarak yakalanması gerekmez.
 */
public class PortNotFoundException extends RuntimeException {

    /**
     * Hata mesajını alan constructor.
     *
     * @param message hataya özel açıklama metni
     */
    public PortNotFoundException(String message) {
        super(message);
    }
}
