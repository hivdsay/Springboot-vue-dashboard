package com.example.dashboard_api.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class City {

    @Id
    @Column(nullable = false, unique = true)
    private String city;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String country;

    private String area;
    private String zone;
    private String state;

    /**
     * Bu şehirdeki tüm limanlar.
     * - OneToMany: Bir şehir, birçok limana sahip olabilir.
     * - mappedBy = "city": Port entity’sindeki 'city' alanına referans verir.
     * - CascadeType.ALL: City silinirse limanlar da silinsin mi?
     * - orphanRemoval = true: City’den silinen port'lar otomatik silinsin.
     */
    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Port> ports;

    // --- Getter & Setter ---

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

    public List<Port> getPorts() {
        return ports;
    }

    public void setPorts(List<Port> ports) {
        this.ports = ports;
    }
}
