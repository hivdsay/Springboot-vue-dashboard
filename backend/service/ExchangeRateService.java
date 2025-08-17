package com.example.dashboard_api.service;

import com.example.dashboard_api.DTO.ExchangeRateCreateRequest;
import com.example.dashboard_api.DTO.ExchangeRateResponse;
import com.example.dashboard_api.DTO.ExchangeRateUpdateRequest;
import com.example.dashboard_api.entity.ExchangeRate;
import com.example.dashboard_api.exception.ExchangeRateNotFoundException;
import com.example.dashboard_api.mapper.ExchangeRateMapper;
import com.example.dashboard_api.repository.ExchangeRateRepository;
import com.example.dashboard_api.specification.ExchangeRateSpecifications;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ExchangeRateService {

    private final ExchangeRateRepository exchangeRateRepository;
    private final ActivityLogService activityLogService;

    public ExchangeRateResponse create(ExchangeRateCreateRequest request) {
        ExchangeRate entity = ExchangeRateMapper.toEntity(request);
        ExchangeRate saved = exchangeRateRepository.save(entity);

        activityLogService.logCreate("EXCHANGE_RATE", "New exchange rate created (id: " + saved.getId() + ")", saved);
        return ExchangeRateMapper.toDto(saved);
    }

    public List<ExchangeRateResponse> getAll() {
        return exchangeRateRepository.findAll().stream()
                .map(ExchangeRateMapper::toDto)
                .collect(Collectors.toList());
    }

    public ExchangeRateResponse getById(Long id) {
        ExchangeRate entity = exchangeRateRepository.findById(id)
                .orElseThrow(() -> new ExchangeRateNotFoundException("Exchange rate not found: " + id));
        return ExchangeRateMapper.toDto(entity);
    }

    public ExchangeRateResponse update(Long id, ExchangeRateUpdateRequest request) {
        ExchangeRate entity = exchangeRateRepository.findById(id)
                .orElseThrow(() -> new ExchangeRateNotFoundException("Exchange rate not found: " + id));

        String oldFromCurr = entity.getFromCurr();
        String oldToCurr = entity.getToCurr();
        String oldRateType = entity.getRateType();
        BigDecimal oldRate = entity.getRate();
        LocalDate oldDate = entity.getExchangeDate();

        ExchangeRateMapper.updateEntity(request, entity);
        ExchangeRate updated = exchangeRateRepository.save(entity);

        activityLogService.logFieldUpdate("EXCHANGE_RATE", "From currency updated (id: " + updated.getId() + ")", "fromCurr", oldFromCurr, updated.getFromCurr());
        activityLogService.logFieldUpdate("EXCHANGE_RATE", "To currency updated (id: " + updated.getId() + ")", "toCurr", oldToCurr, updated.getToCurr());
        activityLogService.logFieldUpdate("EXCHANGE_RATE", "Rate type updated (id: " + updated.getId() + ")", "rateType", oldRateType, updated.getRateType());
        if (oldRate.compareTo(updated.getRate()) != 0) {
            activityLogService.logFieldUpdate("EXCHANGE_RATE", "Rate value updated (id: " + updated.getId() + ")", "rate",
                    oldRate.toPlainString(), updated.getRate().toPlainString());
        }
        if (!oldDate.equals(updated.getExchangeDate())) {
            activityLogService.logFieldUpdate("EXCHANGE_RATE", "Exchange date updated (id: " + updated.getId() + ")", "exchangeDate",
                    String.valueOf(oldDate), String.valueOf(updated.getExchangeDate()));
        }
        return ExchangeRateMapper.toDto(updated);
    }

    public void delete(Long id) {
        ExchangeRate toDelete = exchangeRateRepository.findById(id)
                .orElseThrow(() -> new ExchangeRateNotFoundException("Exchange rate not found: " + id));

        activityLogService.logDelete("EXCHANGE_RATE", "Exchange rate deleted (id: " + toDelete.getId() + ")", toDelete);
        exchangeRateRepository.delete(toDelete);
    }

    public long getExchangeRateCount() {
        return exchangeRateRepository.count();
    }

    public List<ExchangeRateResponse> filter(LocalDate date, String fromCurr, String toCurr, String rateType) {
        Specification<ExchangeRate> spec = (root, query, cb) -> cb.conjunction();

        if (date != null) spec = spec.and(ExchangeRateSpecifications.hasDate(date));
        if (fromCurr != null && !fromCurr.isBlank()) spec = spec.and(ExchangeRateSpecifications.hasFromCurr(fromCurr));
        if (toCurr != null && !toCurr.isBlank()) spec = spec.and(ExchangeRateSpecifications.hasToCurr(toCurr));
        if (rateType != null && !rateType.isBlank()) spec = spec.and(ExchangeRateSpecifications.hasRateType(rateType));

        return exchangeRateRepository.findAll(spec).stream()
                .map(ExchangeRateMapper::toDto)
                .collect(Collectors.toList());
    }
}
