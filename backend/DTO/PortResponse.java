package com.example.dashboard_api.DTO;

/**
 * Port verisinin API cevaplarında (response) kullanıldığı DTO'dur.
 */
public class PortResponse {

    /**
     * Otomatik üretilen ID
     */

    private String code;
    private String name;
    private String cityId;
    private String cityName;

    // -------------------------------------------
    // 🧰 Getter - Setter
    // -------------------------------------------


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

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}

