package com.example.dashboard_api.DTO;

import java.util.List;

/**
 * CityPortSummary:
 *  city: Şehir adı
 *  ports: O şehirdeki liman isimlerinden oluşan liste
 */
public class CityPortSummary {
    private String city;
    private List<String> ports;

    /**
     * @param city  Şehir adı (örn. "London")
     * @param ports O şehirdeki tüm limanların isimleri (["Heathrow", "Gatwick", …])
     */
    public CityPortSummary(String city, List<String> ports) {
        this.city = city;
        this.ports = ports;
    }

    /** Şehir adını döner */
    public String getCity() {
        return city;
    }

    /** Liman isimleri listesini döner */
    public List<String> getPorts() {
        return ports;
    }
}

