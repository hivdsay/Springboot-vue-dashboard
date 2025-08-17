package com.example.dashboard_api.DTO;

import jakarta.persistence.Column;

public class CityResponse {
/* Kullanıcıya göstermek istediğimiz alanları içerir. Burda tüm verileri gösteriyoruz.
 GET /cities
 GET /cities/{id}
 POST /cities sonucunda dönen cevaplarda kullanılır.
 */

    private String city;
    private String name;
    private String country;
    private String area;
    private String zone;
    private String state;


    // Getter Setter
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
