package com.weather.weather_backend.mapper;


import com.weather.weather_backend.dto.current_weather.CurrentWeatherRequest;
import com.weather.weather_backend.dto.current_weather.CurrentWeatherResponse;
import com.weather.weather_backend.entity.CurrentWeather;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CurrentWeatherMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "city", ignore = true)
    @Mapping(target = "station", ignore = true)
    CurrentWeather toEntity(CurrentWeatherRequest request);

    @Mapping(target = "cityId", source = "city.id")
    @Mapping(target = "cityName", source = "city.name")
    @Mapping(target = "stationId", source = "station.id")
    @Mapping(target = "stationName", source = "station.stationName")
    CurrentWeatherResponse toResponse(CurrentWeather entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "city", ignore = true)
    @Mapping(target = "station", ignore = true)
    void updateEntity(CurrentWeatherRequest request,
                      @MappingTarget CurrentWeather entity);
}