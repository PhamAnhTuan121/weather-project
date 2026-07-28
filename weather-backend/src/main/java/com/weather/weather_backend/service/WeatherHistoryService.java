package com.weather.weather_backend.service;

import com.weather.weather_backend.dto.history.HistoryRequest;
import com.weather.weather_backend.dto.history.HistoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface WeatherHistoryService {

    HistoryResponse create(HistoryRequest request);

    HistoryResponse update(Long id, HistoryRequest request);

    void delete(Long id);

    HistoryResponse findById(Long id);

    Page<HistoryResponse> findAll(Pageable pageable);

    Page<HistoryResponse> search(
            Long cityId,
            Long stationId,
            BigDecimal temperatureFrom,
            BigDecimal temperatureTo,
            LocalDateTime observationFrom,
            LocalDateTime observationTo,
            Pageable pageable);

    List<HistoryResponse> getHistoryByCity(Long cityId);

    List<HistoryResponse> getHistoryByStation(Long stationId);

    /**
     * Copy dữ liệu từ CurrentWeather sang WeatherHistory.
     * Được Scheduler gọi định kỳ.
     */
    void archiveCurrentWeather();
}