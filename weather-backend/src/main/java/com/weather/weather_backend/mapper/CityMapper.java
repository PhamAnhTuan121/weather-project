package com.weather.weather_backend.mapper;

import com.weather.weather_backend.dto.city.CityResponse;
import com.weather.weather_backend.entity.City;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CityMapper {

    @Mapping(target = "regionId", source = "region.id")
    @Mapping(target = "regionName", source = "region.name")
    CityResponse toResponse(City city);
}