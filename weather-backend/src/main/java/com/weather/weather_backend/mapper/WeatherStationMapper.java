package com.weather.weather_backend.mapper;


import com.weather.weather_backend.dto.weather_station.WeatherStationRequest;
import com.weather.weather_backend.dto.weather_station.WeatherStationResponse;
import com.weather.weather_backend.entity.WeatherStation;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface WeatherStationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "city", ignore = true)
    WeatherStation toEntity(WeatherStationRequest request);

    @Mapping(target = "cityId", source = "city.id")
    @Mapping(target = "cityName", source = "city.name")
    @Mapping(target = "regionId", source = "city.region.id")
    @Mapping(target = "regionName", source = "city.region.name")
    WeatherStationResponse toResponse(WeatherStation entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "city", ignore = true)
    void updateEntity(WeatherStationRequest request,
                      @MappingTarget WeatherStation entity);
}