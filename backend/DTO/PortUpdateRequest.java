package com.example.dashboard_api.DTO;

import jakarta.validation.constraints.NotBlank;

/**
 * Liman güncelleme işlemleri için kullanılan DTO.
 * Güncelleme sırasında port'un code'u genellikle sabit kalır, bu yüzden burada yer almaz.
 * Name, type ve şehir bilgisi güncellenebilir.
 */
public class PortUpdateRequest {

    @NotBlank(message = "Port name is required.")
    private String name;
    private String type;

    @NotBlank(message = "City ID is required.")
    private String cityId;

    // -------------------------------------------
    //  Getter ve Setter
    // -------------------------------------------

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }
}
