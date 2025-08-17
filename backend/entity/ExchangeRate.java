package com.example.dashboard_api.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * ExchangeRate entity'si döviz kuru bilgisini temsil eder.
 *
 * <p>Veritabanı tablosu: exchange_rates</p>
 *
 * Alanlar:
 * - id: otomatik artan benzersiz ID (primary key)
 * - exchangeDate: kurun geçerli olduğu tarih
 * - fromCurr: hangi para biriminden
 * - toCurr: hangi para birimine
 * - rate: dönüşüm oranı
 * - rateType: alış/satış gibi oran tipi
 */
@Entity
@Table(name = "exchange_rates")
public class ExchangeRate {

    /**
     * Benzersiz ID (otomatik olarak üretilir)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Kur tarihi (örnek: 2025-07-10)
     */
    @Column(name = "exchange_date", nullable = false)
    private LocalDate exchangeDate;

    /**
     * Dönüştürülen para birimi (örnek: USD)
     */
    @Column(name = "from_curr", nullable = false)
    private String fromCurr;

    /**
     * Hedef para birimi (örnek: EUR)
     */
    @Column(name = "to_curr", nullable = false)
    private String toCurr;

    /**
     * Kur oranı (örnek: 32.756000)
     */
    @Column(nullable = false, precision = 19, scale = 6)
    private BigDecimal rate;

    /**
     * Oran tipi (örnek: alış/satış/merkez)
     */
    @Column(name = "rate_type", nullable = false)
    private String rateType;

    // Getter ve Setter'lar

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
