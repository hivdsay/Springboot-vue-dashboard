package com.example.dashboard_api.service;

import com.example.dashboard_api.DTO.CityCreateRequest;
import com.example.dashboard_api.DTO.CityResponse;
import com.example.dashboard_api.DTO.CityUpdateRequest;
import com.example.dashboard_api.entity.City;
import com.example.dashboard_api.exception.CityNotFoundException;
import com.example.dashboard_api.mapper.CityMapper;
import com.example.dashboard_api.repository.CityRepository;
import com.example.dashboard_api.specification.CitySpecifications;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;
    private final ActivityLogService activityLogService;

    public CityResponse createCity(CityCreateRequest request) {
        City entity = CityMapper.toEntity(request);
        City saved = cityRepository.save(entity);

        activityLogService.logCreate("CITY", "New city created (id: " + saved.getCity() + ")", saved);
        return CityMapper.toDto(saved);
    }

    public List<CityResponse> getAllCities() {
        return cityRepository.findAll().stream()
                .map(CityMapper::toDto)
                .collect(Collectors.toList());
    }

    public CityResponse getCityById(String cityId) {
        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> new CityNotFoundException("City not found:" + cityId));
        return CityMapper.toDto(city);
    }

    public CityResponse updateCity(String cityId, CityUpdateRequest request) {
        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> new CityNotFoundException("City not found:" + cityId));

        String oldName = city.getName();
        String oldCountry = city.getCountry();
        String oldArea = city.getArea();
        String oldZone = city.getZone();
        String oldState = city.getState();

        CityMapper.updateEntity(request, city);
        City updated = cityRepository.save(city);
        activityLogService.logFieldUpdate("CITY", "City name updated (id: " + updated.getCity() + ")", "name", oldName, updated.getName());
        activityLogService.logFieldUpdate("CITY", "City country updated (id: " + updated.getCity() + ")", "country", oldCountry, updated.getCountry());
        activityLogService.logFieldUpdate("CITY", "City area updated (id: " + updated.getCity() + ")", "area", oldArea, updated.getArea());
        activityLogService.logFieldUpdate("CITY", "City zone updated (id: " + updated.getCity() + ")", "zone", oldZone, updated.getZone());
        activityLogService.logFieldUpdate("CITY", "City state updated (id: " + updated.getCity() + ")", "state", oldState, updated.getState());
        return CityMapper.toDto(updated);
    }

    public void deleteCity(String cityId) {
        City toDelete = cityRepository.findById(cityId)
                .orElseThrow(() -> new CityNotFoundException("City not found:" + cityId));

        activityLogService.logDelete("CITY", "City deleted (id: " + toDelete.getCity() + ")", toDelete);
        cityRepository.delete(toDelete);
    }

    public long getCityCount() {
        return cityRepository.count();
    }

    public List<CityResponse> filterCities(String name, String country, String area, String zone, String state) {
        Specification<City> spec = (root, query, cb) -> cb.conjunction();

        if (name != null) spec = spec.and(CitySpecifications.hasName(name));
        if (country != null) spec = spec.and(CitySpecifications.hasCountry(country));
        if (area != null) spec = spec.and(CitySpecifications.hasArea(area));
        if (zone != null) spec = spec.and(CitySpecifications.hasZone(zone));
        if (state != null) spec = spec.and(CitySpecifications.hasState(state));

        List<City> filteredCities = cityRepository.findAll(spec);
        if (filteredCities.isEmpty()) {
            throw new CityNotFoundException("No cities matched the given filters.");
        }

        return filteredCities.stream()
                .map(CityMapper::toDto)
                .collect(Collectors.toList());
    }
}

