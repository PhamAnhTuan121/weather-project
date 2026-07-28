package com.weather.weather_backend.controller;

import com.weather.weather_backend.common.response.ApiResult;
import com.weather.weather_backend.dto.city.CityRequest;
import com.weather.weather_backend.dto.city.CityResponse;
import com.weather.weather_backend.service.CityService;
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
@RequestMapping("/api/v1/cities")
@RequiredArgsConstructor
@Tag(name = "City API")
public class CityController {

    private final CityService cityService;

    @Operation(summary = "Create city")
    @PostMapping
    public ResponseEntity<ApiResult<CityResponse>> create(
            @Valid @RequestBody CityRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResult.success(cityService.create(request)));
    }

    @Operation(summary = "Update city")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResult<CityResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody CityRequest request) {

        return ResponseEntity.ok(
                ApiResult.success(cityService.update(id, request)));
    }

    @Operation(summary = "Delete city")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResult<Void>> delete(
            @PathVariable Long id) {

        cityService.delete(id);

        return ResponseEntity.ok(ApiResult.success(null));
    }

    @Operation(summary = "Get city by id")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResult<CityResponse>> findById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResult.success(cityService.findById(id)));
    }

    @Operation(summary = "Get all cities")
    @GetMapping
    public ResponseEntity<ApiResult<Page<CityResponse>>> findAll(
            Pageable pageable) {

        return ResponseEntity.ok(
                ApiResult.success(cityService.findAll(pageable)));
    }

    @Operation(summary = "Search cities")
    @GetMapping("/search")
    public ResponseEntity<ApiResult<Page<CityResponse>>> search(
            @RequestParam String keyword,
            Pageable pageable) {

        return ResponseEntity.ok(
                ApiResult.success(cityService.search(keyword, pageable)));
    }

    @Operation(summary = "Get cities by region")
    @GetMapping("/region/{regionId}")
    public ResponseEntity<ApiResult<Page<CityResponse>>> findByRegion(
            @PathVariable Long regionId,
            Pageable pageable) {

        return ResponseEntity.ok(
                ApiResult.success(
                        cityService.findByRegion(regionId, pageable)));
    }
}