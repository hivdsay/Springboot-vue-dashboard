package com.example.dashboard_api.mapper;

import com.example.dashboard_api.DTO.ExchangeRateCreateRequest;
import com.example.dashboard_api.DTO.ExchangeRateUpdateRequest;
import com.example.dashboard_api.DTO.ExchangeRateResponse;
import com.example.dashboard_api.entity.ExchangeRate;

/**
 * ExchangeRate entity'si ile ilgili DTO dönüşümlerini gerçekleştiren yardımcı sınıf.
 */
public class ExchangeRateMapper {

    /**
     * ExchangeRateCreateRequest → ExchangeRate (Entity)
     * Yeni veri oluşturulurken kullanılan dönüşüm.
     */
    public static ExchangeRate toEntity(ExchangeRateCreateRequest dto) {
        ExchangeRate entity = new ExchangeRate();
        entity.setExchangeDate(dto.getExchangeDate());
        entity.setFromCurr(dto.getFromCurr());
        entity.setToCurr(dto.getToCurr());
        entity.setRate(dto.getRate());
        entity.setRateType(dto.getRateType());
        return entity;
    }

    /**
     * ExchangeRateUpdateRequest → Mevcut ExchangeRate güncellemesi.
     * Entity'nin ID’si değişmeyeceğinden sadece veriler güncellenir.
     */
    public static void updateEntity(ExchangeRateUpdateRequest dto, ExchangeRate entity) {
        entity.setExchangeDate(dto.getExchangeDate());
        entity.setFromCurr(dto.getFromCurr());
        entity.setToCurr(dto.getToCurr());
        entity.setRate(dto.getRate());
        entity.setRateType(dto.getRateType());
    }

    /**
     * ExchangeRate (Entity) → ExchangeRateResponse (DTO)
     * Kullanıcıya yanıt olarak gösterilecek verileri dönüştürür.
     */
    public static ExchangeRateResponse toDto(ExchangeRate entity) {
        ExchangeRateResponse dto = new ExchangeRateResponse();
        dto.setId(entity.getId());
        dto.setExchangeDate(entity.getExchangeDate());
        dto.setFromCurr(entity.getFromCurr());
        dto.setToCurr(entity.getToCurr());
        dto.setRate(entity.getRate());
        dto.setRateType(entity.getRateType());
        return dto;
    }
}
