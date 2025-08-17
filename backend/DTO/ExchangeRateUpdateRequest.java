package com.example.dashboard_api.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Mevcut döviz kuru bilgisini güncellemek için kullanılan DTO.
 */
public class ExchangeRateUpdateRequest {

    private LocalDate exchangeDate;
    private String fromCurr;
    private String toCurr;
    private BigDecimal rate;
    private String rateType;

    // Getter & Setter'lar
    public LocalDate getExchangeDate() {
        return exchangeDate;
    }

    public void setExchangeDate(LocalDate exchangeDate) {
        this.exchangeDate = exchangeDate;
    }

    public String getFromCurr() {
        return fromCurr;
    }

    public void setFromCurr(String fromCurr) {
        this.fromCurr = fromCurr;
    }

    public String getToCurr() {
        return toCurr;
    }

    public void setToCurr(String toCurr) {
        this.toCurr = toCurr;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public String getRateType() {
        return rateType;
    }

    public void setRateType(String rateType) {
        this.rateType = rateType;
    }
}
