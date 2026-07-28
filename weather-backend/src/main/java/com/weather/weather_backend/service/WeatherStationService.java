package com.weather.weather_backend.service;


import com.weather.weather_backend.dto.weather_station.WeatherStationRequest;
import com.weather.weather_backend.dto.weather_station.WeatherStationResponse;
import com.weather.weather_backend.entity.StationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WeatherStationService {

    WeatherStationResponse create(WeatherStationRequest request);

    WeatherStationResponse update(Long id,
                                  WeatherStationRequest request);

    void delete(Long id);

    WeatherStationResponse findById(Long id);

    Page<WeatherStationResponse> findAll(Pageable pageable);

    Page<WeatherStationResponse> search(
            String keyword,
            Long cityId,
            StationStatus status,
            Pageable pageable);

}