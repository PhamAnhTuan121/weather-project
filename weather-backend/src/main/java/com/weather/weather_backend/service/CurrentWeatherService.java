package com.weather.weather_backend.service;


import com.weather.weather_backend.dto.current_weather.CurrentWeatherRequest;
import com.weather.weather_backend.dto.current_weather.CurrentWeatherResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface CurrentWeatherService {

    CurrentWeatherResponse create(CurrentWeatherRequest request);

    CurrentWeatherResponse update(Long id, CurrentWeatherRequest request);

    void delete(Long id);

    CurrentWeatherResponse findById(Long id);

    Page<CurrentWeatherResponse> findAll(Pageable pageable);

    Page<CurrentWeatherResponse> search(
            Long cityId,
            Long stationId,
            BigDecimal temperatureFrom,
            BigDecimal temperatureTo,
            LocalDateTime observationFrom,
            LocalDateTime observationTo,
            Pageable pageable
    );

    CurrentWeatherResponse getLatestByCity(Long cityId);

    CurrentWeatherResponse getLatestByStation(Long stationId);

    CurrentWeatherResponse syncByStation(Long stationId);

    void syncAllStations();
}