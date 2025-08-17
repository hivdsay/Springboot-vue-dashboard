package com.example.dashboard_api.DTO;

import jakarta.validation.constraints.NotBlank;

/**
 * Port oluşturma isteği için DTO (Data Transfer Object).
 * ID olan code, dışarıdan geldiği için dahil edilir.
 */
public class PortCreateRequest {

    @NotBlank(message = "Port code is required.")
    private String code;

    @NotBlank(message = "Port name is required.")
    private String name;

    /**
     * Şehrin ID’si (city tablosundaki 'city' alanı)
     */
    @NotBlank(message = "City ID is required.")
    private String cityId;


    // Getters & Setters
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }
}
