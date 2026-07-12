package com.weather.weather_backend.mapper;

import com.weather.weather_backend.dto.region.RegionRequest;
import com.weather.weather_backend.dto.region.RegionResponse;
import com.weather.weather_backend.entity.Region;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RegionMapper {

    Region toEntity(RegionRequest request);

    RegionResponse toResponse(Region entity);

    void updateEntity(
            RegionRequest request,
            @MappingTarget Region entity
    );

}