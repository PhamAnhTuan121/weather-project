package com.weather.weather_backend.service;

import com.weather.weather_backend.dto.city.CityRequest;
import com.weather.weather_backend.dto.city.CityResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CityService {

    CityResponse create(CityRequest request);

    CityResponse update(Long id, CityRequest request);

    void delete(Long id);

    CityResponse findById(Long id);

    Page<CityResponse> findAll(Pageable pageable);

    Page<CityResponse> search(String keyword, Pageable pageable);

    Page<CityResponse> findByRegion(Long regionId, Pageable pageable);

}
