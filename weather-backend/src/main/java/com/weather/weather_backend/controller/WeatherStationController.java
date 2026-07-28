package com.weather.weather_backend.controller;

import com.weather.weather_backend.common.response.ApiResult;

import com.weather.weather_backend.dto.weather_station.WeatherStationRequest;
import com.weather.weather_backend.dto.weather_station.WeatherStationResponse;
import com.weather.weather_backend.entity.StationStatus;
import com.weather.weather_backend.service.WeatherStationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/weather-stations")
@RequiredArgsConstructor
@Tag(name = "Weather Station API")
public class WeatherStationController {

    private final WeatherStationService weatherStationService;

    @Operation(summary = "Create weather station")
    @PostMapping
    public ResponseEntity<ApiResult<WeatherStationResponse>> create(
            @Valid @RequestBody WeatherStationRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResult.success(weatherStationService.create(request)));
    }

    @Operation(summary = "Update weather station")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResult<WeatherStationResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody WeatherStationRequest request) {

        return ResponseEntity.ok(
                ApiResult.success(weatherStationService.update(id, request)));
    }

    @Operation(summary = "Delete weather station")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResult<Void>> delete(
            @PathVariable Long id) {

        weatherStationService.delete(id);

        return ResponseEntity.ok(ApiResult.success(null));
    }

    @Operation(summary = "Get weather station by id")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResult<WeatherStationResponse>> findById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResult.success(weatherStationService.findById(id)));
    }

    @Operation(summary = "Search weather stations")
    @GetMapping
    public ResponseEntity<ApiResult<Page<WeatherStationResponse>>> search(

            @RequestParam(required = false)
            String keyword,

            @RequestParam(required = false)
            Long cityId,

            @RequestParam(required = false)
            StationStatus status,

            Pageable pageable) {

        return ResponseEntity.ok(
                ApiResult.success(
                        weatherStationService.search(
                                keyword,
                                cityId,
                                status,
                                pageable
                        )));
    }
}