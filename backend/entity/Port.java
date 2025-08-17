package com.example.dashboard_api.entity;

import jakarta.persistence.*;

/**
 * Uygulamadaki limanları temsil eder.
 * Her port (liman), bir şehre (City) bağlıdır.
 */
@Entity
public class Port {

    /**
     * Liman kodu. Eşsiz ve boş bırakılamaz.
     */
    @Id
    @Column(nullable = false, unique = true)
    private String code;
    @Column(nullable = false)
    private String name;
    /**
     * Bu limanın bağlı olduğu şehir.
     * city column'ı, City entity'sinin city alanına referans verir.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "city", referencedColumnName = "city", nullable = false)
    private City city;

    // -------------------------------------------
    // Getter ve Setter metodları
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


    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
