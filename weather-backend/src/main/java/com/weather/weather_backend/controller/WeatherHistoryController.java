package com.weather.weather_backend.controller;

import com.weather.weather_backend.dto.history.HistoryRequest;
import com.weather.weather_backend.dto.history.HistoryResponse;
import com.weather.weather_backend.service.WeatherHistoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/weather-history")
@Tag(name = "Weather History")
public class WeatherHistoryController {

    private final WeatherHistoryService weatherHistoryService;

    @PostMapping
    public HistoryResponse create(
            @Valid @RequestBody HistoryRequest request) {

        return weatherHistoryService.create(request);
    }

    @PutMapping("/{id}")
    public HistoryResponse update(
            @PathVariable Long id,
            @Valid @RequestBody HistoryRequest request) {

        return weatherHistoryService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {

        weatherHistoryService.delete(id);
    }

    @GetMapping("/{id}")
    public HistoryResponse findById(
            @PathVariable Long id) {

        return weatherHistoryService.findById(id);
    }

    @GetMapping
    public Page<HistoryResponse> findAll(
            Pageable pageable) {

        return weatherHistoryService.findAll(pageable);
    }

    @GetMapping("/search")
    public Page<HistoryResponse> search(

            @RequestParam(required = false)
            Long cityId,

            @RequestParam(required = false)
            Long stationId,

            @RequestParam(required = false)
            BigDecimal temperatureFrom,

            @RequestParam(required = false)
            BigDecimal temperatureTo,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime observationFrom,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime observationTo,

            Pageable pageable) {

        return weatherHistoryService.search(
                cityId,
                stationId,
                temperatureFrom,
                temperatureTo,
                observationFrom,
                observationTo,
                pageable);
    }

    @GetMapping("/city/{cityId}")
    public List<HistoryResponse> getHistoryByCity(
            @PathVariable Long cityId) {

        return weatherHistoryService.getHistoryByCity(cityId);
    }

    @GetMapping("/station/{stationId}")
    public List<HistoryResponse> getHistoryByStation(
            @PathVariable Long stationId) {

        return weatherHistoryService.getHistoryByStation(stationId);
    }

    @PostMapping("/archive")
    public void archiveCurrentWeather() {

        weatherHistoryService.archiveCurrentWeather();
    }

}