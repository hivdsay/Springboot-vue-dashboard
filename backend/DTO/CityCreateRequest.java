package com.example.dashboard_api.DTO;

import jakarta.validation.constraints.NotBlank;

//Bu sınıf yeni şehir oluşturulurken kullanıcıdan alınan veriyi temsil eder.
public class CityCreateRequest {

    @NotBlank(message = "city ID cannot be blank") // city hem ID hem de veri olduğu için kullanıcı post isteği göndermek zorunda.
    private String city;

    @NotBlank(message = "Name is required") // Bu alanlar girilmek zorunda.
    private String name;

    @NotBlank(message = "Country is required")
    private String country;

    private String area; //opsiyonel kullanıcı isterse girer.
    private String zone; //opsiyonel
    private String state; //opsiyonel

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
