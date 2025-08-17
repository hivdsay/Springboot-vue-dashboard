package com.example.dashboard_api.controller;

import com.example.dashboard_api.DTO.ExchangeRateCreateRequest;
import com.example.dashboard_api.DTO.ExchangeRateResponse;
import com.example.dashboard_api.DTO.ExchangeRateUpdateRequest;
import com.example.dashboard_api.service.ExchangeRateService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * ExchangeRate API controller'idir.
 * Tüm REST endpointlerini dış dünyaya açar (create, read, update, delete, filter).
 */
@RestController
@RequestMapping("/api/exchange-rates")
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;

    public ExchangeRateController(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    /**
     * Yeni ExchangeRate kaydı oluşturur.
     */
    @PostMapping
    public ResponseEntity<ExchangeRateResponse> create(@RequestBody ExchangeRateCreateRequest request) {
        ExchangeRateResponse response = exchangeRateService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Tüm ExchangeRate kayıtlarını getirir.
     */
    @GetMapping
    public ResponseEntity<List<ExchangeRateResponse>> getAll() {
        List<ExchangeRateResponse> list = exchangeRateService.getAll();
        return ResponseEntity.ok(list);
    }

    /**
     * Belirtilen ID ile tek ExchangeRate verisini getirir.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ExchangeRateResponse> getById(@PathVariable Long id) {
        ExchangeRateResponse response = exchangeRateService.getById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * ExchangeRate kaydını günceller.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ExchangeRateResponse> update(@PathVariable Long id,
                                                       @RequestBody ExchangeRateUpdateRequest request) {
        ExchangeRateResponse updated = exchangeRateService.update(id, request);
        return ResponseEntity.ok(updated);
    }

    /**
     * Belirtilen ID ile ExchangeRate siler.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        exchangeRateService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getExchangeRateCount() {
        long count = exchangeRateService.getExchangeRateCount();
        return ResponseEntity.ok(count);
    }


    /**
     * Dinamik filtreleme
     */
    @GetMapping("/filter")
    public ResponseEntity<List<ExchangeRateResponse>> filter(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false) String fromCurr,
            @RequestParam(required = false) String toCurr,
            @RequestParam(required = false) String rateType
    ) {
        List<ExchangeRateResponse> filtered = exchangeRateService.filter(date, fromCurr, toCurr, rateType);
        return ResponseEntity.ok(filtered);
    }
}
