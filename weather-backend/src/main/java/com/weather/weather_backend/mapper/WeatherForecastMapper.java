package com.weather.weather_backend.mapper;

import com.weather.weather_backend.dto.forecast.ForecastRequest;
import com.weather.weather_backend.dto.forecast.ForecastResponse;
import com.weather.weather_backend.entity.WeatherForecast;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface WeatherForecastMapper {

    @Mapping(target = "cityId", source = "city.id")
    @Mapping(target = "cityName", source = "city.name")
    @Mapping(target = "stationId", source = "station.id")
    @Mapping(target = "stationCode", source = "station.stationCode")
    @Mapping(target = "stationName", source = "station.stationName")
    ForecastResponse toResponse(WeatherForecast entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "city", ignore = true)
    @Mapping(target = "station", ignore = true)
    WeatherForecast toEntity(ForecastRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "city", ignore = true)
    @Mapping(target = "station", ignore = true)
    void updateEntity(ForecastRequest request,
                      @MappingTarget WeatherForecast entity);
}