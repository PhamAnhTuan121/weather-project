package com.weather.weather_backend.controller;

import com.weather.weather_backend.common.response.ApiResult;
import com.weather.weather_backend.dto.current_weather.CurrentWeatherRequest;
import com.weather.weather_backend.dto.current_weather.CurrentWeatherResponse;
import com.weather.weather_backend.service.CurrentWeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/current-weather")
@RequiredArgsConstructor
@Tag(name = "Current Weather API")
public class CurrentWeatherController {

    private final CurrentWeatherService currentWeatherService;

    @Operation(summary = "Create or update current weather")
    @PostMapping
    public ResponseEntity<ApiResult<CurrentWeatherResponse>> create(
            @Valid @RequestBody CurrentWeatherRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResult.success(
                        currentWeatherService.create(request)));
    }

    @Operation(summary = "Update current weather")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResult<CurrentWeatherResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody CurrentWeatherRequest request) {

        return ResponseEntity.ok(
                ApiResult.success(
                        currentWeatherService.update(id, request)));
    }

    @Operation(summary = "Delete current weather")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResult<Void>> delete(
            @PathVariable Long id) {

        currentWeatherService.delete(id);

        return ResponseEntity.ok(ApiResult.success(null));
    }

    @Operation(summary = "Get current weather by id")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResult<CurrentWeatherResponse>> findById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResult.success(
                        currentWeatherService.findById(id)));
    }

    @Operation(summary = "Search current weather")
    @GetMapping
    public ResponseEntity<ApiResult<Page<CurrentWeatherResponse>>> search(

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

        return ResponseEntity.ok(
                ApiResult.success(
                        currentWeatherService.search(
                                cityId,
                                stationId,
                                temperatureFrom,
                                temperatureTo,
                                observationFrom,
                                observationTo,
                                pageable)));
    }

    @Operation(summary = "Get latest weather by city")
    @GetMapping("/city/{cityId}")
    public ResponseEntity<ApiResult<CurrentWeatherResponse>> getLatestByCity(
            @PathVariable Long cityId) {

        return ResponseEntity.ok(
                ApiResult.success(
                        currentWeatherService.getLatestByCity(cityId)));
    }

    @Operation(summary = "Get latest weather by station")
    @GetMapping("/station/{stationId}")
    public ResponseEntity<ApiResult<CurrentWeatherResponse>> getLatestByStation(
            @PathVariable Long stationId) {

        return ResponseEntity.ok(
                ApiResult.success(
                        currentWeatherService.getLatestByStation(stationId)));
    }
}