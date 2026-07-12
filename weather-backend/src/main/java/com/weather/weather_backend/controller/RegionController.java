package com.weather.weather_backend.controller;

import com.weather.weather_backend.common.constant.ApiMessage;
import com.weather.weather_backend.common.response.ApiResult;
import com.weather.weather_backend.dto.region.RegionRequest;
import com.weather.weather_backend.dto.region.RegionResponse;
import com.weather.weather_backend.service.RegionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/regions")
@RequiredArgsConstructor
@Tag(name = "Region", description = "Region Management APIs")
public class RegionController {

    private final RegionService regionService;

    @Operation(summary = "Create a new region")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Region created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "409", description = "Region already exists")
    })
    @PostMapping
    public ResponseEntity<ApiResult<RegionResponse>> create(@Valid @RequestBody RegionRequest request) {

        RegionResponse response = regionService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResult.<RegionResponse>builder()
                        .success(true)
                        .message(ApiMessage.CREATED)
                        .data(response)
                        .build());
    }

    @Operation(summary = "Update region")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Region updated successfully"),
            @ApiResponse(responseCode = "404", description = "Region not found"),
            @ApiResponse(responseCode = "409", description = "Region already exists")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ApiResult<RegionResponse>> update(@PathVariable Long id,
                                                            @Valid @RequestBody RegionRequest request) {

        RegionResponse response = regionService.update(id, request);

        return ResponseEntity.ok(
                ApiResult.<RegionResponse>builder()
                        .success(true)
                        .message(ApiMessage.UPDATED)
                        .data(response)
                        .build()
        );
    }

    @Operation(summary = "Delete region")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Region deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Region not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResult<Void>> delete(@PathVariable Long id) {

        regionService.delete(id);

        return ResponseEntity.ok(
                ApiResult.<Void>builder()
                        .success(true)
                        .message(ApiMessage.DELETED)
                        .build()
        );
    }

    @Operation(summary = "Get region by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Region not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResult<RegionResponse>> findById(@PathVariable Long id) {

        RegionResponse response = regionService.findById(id);

        return ResponseEntity.ok(
                ApiResult.<RegionResponse>builder()
                        .success(true)
                        .message(ApiMessage.SUCCESS)
                        .data(response)
                        .build()
        );
    }

    @Operation(summary = "Get all regions")
    @ApiResponse(responseCode = "200", description = "Success")
    @GetMapping
    public ResponseEntity<ApiResult<Page<RegionResponse>>> findAll(Pageable pageable) {

        Page<RegionResponse> response = regionService.findAll(pageable);

        return ResponseEntity.ok(
                ApiResult.<Page<RegionResponse>>builder()
                        .success(true)
                        .message(ApiMessage.SUCCESS)
                        .data(response)
                        .build()
        );
    }

    @Operation(summary = "Search regions by keyword")
    @ApiResponse(responseCode = "200", description = "Success")
    @GetMapping("/search")
    public ResponseEntity<ApiResult<Page<RegionResponse>>> search(
            @RequestParam String keyword,
            Pageable pageable) {

        Page<RegionResponse> response = regionService.search(keyword, pageable);

        return ResponseEntity.ok(
                ApiResult.<Page<RegionResponse>>builder()
                        .success(true)
                        .message(ApiMessage.SUCCESS)
                        .data(response)
                        .build()
        );
    }

}