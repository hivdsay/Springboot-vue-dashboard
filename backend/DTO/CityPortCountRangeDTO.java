package com.example.dashboard_api.DTO;

/**
 * Liman sayısı aralıklarına göre şehir sayısını taşımak için kullanılan DTO.
 * Örneğin: {"rangeLabel":"1","cityCount":330}
 */
public class CityPortCountRangeDTO {
    /** Aralık etiketi ( "1","2",…,"10") */
    private String rangeLabel;

    /** O aralıkta kaç şehir olduğu */
    private long cityCount;

    /**
     * DTO oluşturucu.
     * @param rangeLabel  Aralık metni
     * @param cityCount   Bu aralığa düşen şehir sayısı
     */
    public CityPortCountRangeDTO(String rangeLabel, long cityCount) {
        this.rangeLabel = rangeLabel;
        this.cityCount = cityCount;
    }


    public String getRangeLabel() {
        return rangeLabel;
    }


    public void setRangeLabel(String rangeLabel) {
        this.rangeLabel = rangeLabel;
    }


    public long getCityCount() {
        return cityCount;
    }


    public void setCityCount(long cityCount) {
        this.cityCount = cityCount;
    }
}
