package com.weather.weather_backend.controller;

import com.weather.weather_backend.dto.forecast.ForecastRequest;
import com.weather.weather_backend.dto.forecast.ForecastResponse;
import com.weather.weather_backend.service.WeatherForecastService;
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
@RequestMapping("/api/v1/forecasts")
@RequiredArgsConstructor
public class WeatherForecastController {

    private final WeatherForecastService weatherForecastService;

    @PostMapping
    public ForecastResponse create(
            @Valid @RequestBody ForecastRequest request) {

        return weatherForecastService.create(request);
    }

    @PutMapping("/{id}")
    public ForecastResponse update(
            @PathVariable Long id,
            @Valid @RequestBody ForecastRequest request) {

        return weatherForecastService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {

        weatherForecastService.delete(id);
    }

    @GetMapping("/{id}")
    public ForecastResponse findById(@PathVariable Long id) {

        return weatherForecastService.findById(id);
    }

    @GetMapping
    public Page<ForecastResponse> findAll(Pageable pageable) {

        return weatherForecastService.findAll(pageable);
    }

    @GetMapping("/search")
    public Page<ForecastResponse> search(

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
            LocalDateTime forecastFrom,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime forecastTo,

            Pageable pageable) {

        return weatherForecastService.search(
                cityId,
                stationId,
                temperatureFrom,
                temperatureTo,
                forecastFrom,
                forecastTo,
                pageable);
    }

    @GetMapping("/station/{stationId}")
    public List<ForecastResponse> getForecastByStation(
            @PathVariable Long stationId) {

        return weatherForecastService.getForecastByStation(stationId);
    }

    @GetMapping("/city/{cityId}")
    public List<ForecastResponse> getForecastByCity(
            @PathVariable Long cityId) {

        return weatherForecastService.getForecastByCity(cityId);
    }

    @PostMapping("/sync/{stationId}")
    public List<ForecastResponse> syncForecast(
            @PathVariable Long stationId) {

        return weatherForecastService.syncForecast(stationId);
    }

    @PostMapping("/sync")
    public void syncAllStations() {

        weatherForecastService.syncAllStations();
    }

}