package com.example.dashboard_api.service;

import com.example.dashboard_api.DTO.CityPortCountRangeDTO;
import com.example.dashboard_api.DTO.CityPortSummary;
import com.example.dashboard_api.DTO.PortCreateRequest;
import com.example.dashboard_api.DTO.PortResponse;
import com.example.dashboard_api.DTO.PortUpdateRequest;
import com.example.dashboard_api.entity.City;
import com.example.dashboard_api.entity.Port;
import com.example.dashboard_api.exception.PortNotFoundException;
import com.example.dashboard_api.mapper.PortMapper;
import com.example.dashboard_api.repository.CityRepository;
import com.example.dashboard_api.repository.PortRepository;
import com.example.dashboard_api.specification.PortSpecifications;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PortService {

    private final PortRepository portRepository;
    private final CityRepository cityRepository;
    private final ActivityLogService activityLogService;

    public PortResponse createPort(PortCreateRequest request) {
        Port entity = PortMapper.toEntity(request);

        City city = cityRepository.findById(request.getCityId())
                .orElseThrow(() -> new PortNotFoundException("No city founded: " + request.getCityId()));
        entity.setCity(city);

        Port saved = portRepository.save(entity);
        activityLogService.logCreate("PORT", "New port created (id: " + saved.getCode() + ")", saved);
        return PortMapper.toDto(saved);
    }

    public List<PortResponse> getAllPorts() {
        return portRepository.findAll().stream()
                .map(PortMapper::toDto)
                .collect(Collectors.toList());
    }

    public PortResponse getPortByCode(String code) {
        Port port = portRepository.findById(code)
                .orElseThrow(() -> new PortNotFoundException("No port: " + code));
        return PortMapper.toDto(port);
    }

    public PortResponse updatePort(String code, PortUpdateRequest request) {
        Port port = portRepository.findById(code)
                .orElseThrow(() -> new PortNotFoundException("No port : " + code));

        String oldName = port.getName();
        String oldCityName = port.getCity().getName();

        PortMapper.updateEntity(request, port);
        City city = cityRepository.findById(request.getCityId())
                .orElseThrow(() -> new PortNotFoundException("No city: " + request.getCityId()));
        port.setCity(city);

        Port updated = portRepository.save(port);

        activityLogService.logFieldUpdate("PORT", "Port name updated (id: " + updated.getCode() + ")", "name", oldName, updated.getName());
        activityLogService.logFieldUpdate("PORT", "Port city updated (id: " + updated.getCode() + ")", "cityId", oldCityName, updated.getCity().getName());
        return PortMapper.toDto(updated);
    }

    public void deletePort(String code) {
        Port toDelete = portRepository.findById(code)
                .orElseThrow(() -> new PortNotFoundException("No port: " + code));

        activityLogService.logDelete("PORT", "Port deleted (id: " + toDelete.getCode() + ")", toDelete);
        portRepository.delete(toDelete);
    }

    public long getPortCount() {
        return portRepository.count();
    }

    public String getCityWithMostPorts() {
        List<String> result = portRepository.findCityWithMostPorts(PageRequest.of(0, 1));
        return result.isEmpty() ? "NO DATA" : result.get(0);
    }

    public List<CityPortSummary> getCityPortSummary() {
        List<Port> ports = portRepository.findAll();

        return ports.stream()
                .collect(Collectors.groupingBy(
                        port -> port.getCity().getName(),
                        Collectors.mapping(Port::getName, Collectors.toList())
                ))
                .entrySet().stream()
                .map(entry -> new CityPortSummary(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    public List<PortResponse> filterPorts(String name, String city) {
        Specification<Port> spec = (root, query, cb) -> cb.conjunction();

        if (name != null && !name.isEmpty()) {
            spec = spec.and(PortSpecifications.hasName(name));
        }
        if (city != null && !city.isEmpty()) {
            spec = spec.and(PortSpecifications.hasCity(city));
        }

        List<Port> ports = portRepository.findAll(spec);

        if (ports.isEmpty()) {
            throw new PortNotFoundException("No ports found with given filters.");
        }

        return ports.stream()
                .map(PortMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<CityPortCountRangeDTO> getCityCountByPortRange() {
        List<Object[]> rawData = portRepository.findCityCountByExactPortCounts1To10();
        List<CityPortCountRangeDTO> result = new ArrayList<>();

        for (Object[] row : rawData) {
            Integer portCount = ((Number) row[0]).intValue();
            Long cityCount = ((Number) row[1]).longValue();

            result.add(new CityPortCountRangeDTO(
                    portCount.toString(),
                    cityCount
            ));
        }

        return result;
    }
}
