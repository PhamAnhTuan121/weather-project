package com.weather.weather_backend.service;

import com.weather.weather_backend.dto.region.RegionRequest;
import com.weather.weather_backend.dto.region.RegionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RegionService {

    RegionResponse create(RegionRequest request);

    RegionResponse update(Long id, RegionRequest request);

    void delete(Long id);

    RegionResponse findById(Long id);

    Page<RegionResponse> findAll(Pageable pageable);

    Page<RegionResponse> search(String keyword, Pageable pageable);
}