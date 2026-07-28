package com.weather.weather_backend.service;

import com.weather.weather_backend.dto.forecast.ForecastRequest;
import com.weather.weather_backend.dto.forecast.ForecastResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface WeatherForecastService {

    ForecastResponse create(ForecastRequest request);

    ForecastResponse update(Long id,
                            ForecastRequest request);

    void delete(Long id);

    ForecastResponse findById(Long id);

    Page<ForecastResponse> findAll(Pageable pageable);

    Page<ForecastResponse> search(
            Long cityId,
            Long stationId,
            BigDecimal temperatureFrom,
            BigDecimal temperatureTo,
            LocalDateTime forecastFrom,
            LocalDateTime forecastTo,
            Pageable pageable);

    List<ForecastResponse> getForecastByStation(Long stationId);

    List<ForecastResponse> getForecastByCity(Long cityId);

    List<ForecastResponse> syncForecast(Long stationId);

    void syncAllStations();

}