package com.example.dashboard_api.controller;

import com.example.dashboard_api.DTO.*;
import com.example.dashboard_api.service.PortService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Port (Liman) verilerini yöneten REST API Controller sınıfıdır.
 * <p>
 * CRUD işlemleri ve filtreleme gibi işlemleri HTTP istekleriyle yönetir.
 */
@RestController
@RequestMapping("/api/ports")
public class PortController {

    private final PortService portService;

    public PortController(PortService portService) {
        this.portService = portService;
    }

    /**
     * ✅ Yeni bir liman kaydı oluşturur.
     * @param request İstemciden gelen liman verisi
     * @return Oluşturulan liman verisi ve 201 (CREATED) cevabı
     */
    @PostMapping
    public ResponseEntity<PortResponse> createPort(@RequestBody PortCreateRequest request) {
        PortResponse response = portService.createPort(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * ✅ Tüm liman kayıtlarını getirir.
     * @return Liman listesi ve 200 cevabı
     */
    @GetMapping
    public ResponseEntity<List<PortResponse>> getAllPorts() {
        List<PortResponse> ports = portService.getAllPorts();
        return ResponseEntity.ok(ports);
    }

    /**
     * ✅ Kod ile belirli bir limanı getirir.
     * @param code Port kodu (PK)
     * @return Eşleşen liman
     */
    @GetMapping("/{code}")
    public ResponseEntity<PortResponse> getPortByCode(@PathVariable String code) {
        PortResponse port = portService.getPortByCode(code);
        return ResponseEntity.ok(port);
    }

    /**
     * ✅ Limanı günceller.
     * @param code    Güncellenecek limanın kodu
     * @param request Yeni liman verisi
     * @return Güncellenmiş liman
     */
    @PutMapping("/{code}")
    public ResponseEntity<PortResponse> updatePort(
            @PathVariable String code,
            @RequestBody PortUpdateRequest request
    ) {
        PortResponse updated = portService.updatePort(code, request);
        return ResponseEntity.ok(updated);
    }

    /**
     * ✅ Belirli bir limanı siler.
     * @param code Silinecek limanın kodu
     * @return 204 No Content cevabı
     */
    @DeleteMapping("/{code}")
    public ResponseEntity<Void> deletePort(@PathVariable String code) {
        portService.deletePort(code);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-city") // filtreleme ile yaptığımız grafik için
    public ResponseEntity<List<CityPortSummary>> getPortsGroupedByCity() {
        return ResponseEntity.ok(portService.getCityPortSummary());
    }

    @GetMapping("/count") //Dashboardakki kartlar için
    public ResponseEntity<Long> getPortCount() {
        long count = portService.getPortCount();
        return ResponseEntity.ok(count);
    }
    @GetMapping("/most-port-city") //Dashboardakki kartlar için
    public ResponseEntity<String> getCityWithMostPorts() {
        String cityName = portService.getCityWithMostPorts();
        return ResponseEntity.ok(cityName);
    }
    @GetMapping("/port-distribution") //Dashboardaki grafik için
    public ResponseEntity<List<CityPortCountRangeDTO>> getCityCountByPortRange() {
        return ResponseEntity.ok(portService.getCityCountByPortRange());
    }






    /**
     * ✅ İsteğe bağlı parametrelerle limanları filtreler.
     * @param name (opsiyonel) Liman adı
     * @param city (opsiyonel) Şehir adı
     * @return Filtreye uyan limanlar
     */
    @GetMapping("/filter")
    public ResponseEntity<List<PortResponse>> filterPorts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String city
    ) {
        List<PortResponse> filtered = portService.filterPorts(name, city);
        return ResponseEntity.ok(filtered);
    }

}
