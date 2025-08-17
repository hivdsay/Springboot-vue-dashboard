package com.example.dashboard_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Tüm @RestController sınıflarında oluşabilecek istisnalara karşı global hata yakalama mekanizması sağlar.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Şehir bulunamadığında fırlatılan istisna için hata yanıtı döner.
     *
     * @param ex CityNotFoundException nesnesi
     * @return 404 Not Found yanıtı ve mesaj
     */
    @ExceptionHandler(CityNotFoundException.class)
    public ResponseEntity<String> handleCityNotFound(CityNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    /**
     * Port bulunamadığında fırlatılan istisna için hata yanıtı döner.
     *
     * @param ex PortNotFoundException nesnesi
     * @return 404 Not Found yanıtı ve mesaj
     */
    @ExceptionHandler(PortNotFoundException.class)
    public ResponseEntity<String> handlePortNotFound(PortNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    /**
     * Kullanıcı geçersiz veri gönderirse 400 hatası dönülür.
     *
     * @param ex IllegalArgumentException nesnesi
     * @return 400 Bad Request yanıtı ve hata mesajı
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleBadRequest(IllegalArgumentException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Bad Request: " + ex.getMessage());
    }

    /**
     * Beklenmeyen tüm diğer hatalar için genel hata yakalaması yapar.
     *
     * @param ex yakalanamayan istisna nesnesi
     * @return 500 Internal Server Error yanıtı ve mesaj
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneral(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An error occurred: " + ex.getMessage());
    }
}

