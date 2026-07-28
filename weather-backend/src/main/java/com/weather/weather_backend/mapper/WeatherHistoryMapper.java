package com.weather.weather_backend.mapper;

import com.weather.weather_backend.dto.history.HistoryRequest;
import com.weather.weather_backend.dto.history.HistoryResponse;
import com.weather.weather_backend.entity.WeatherHistory;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface WeatherHistoryMapper {

    @Mapping(target = "cityId", source = "city.id")
    @Mapping(target = "cityName", source = "city.name")
    @Mapping(target = "stationId", source = "station.id")
    @Mapping(target = "stationCode", source = "station.stationCode")
    @Mapping(target = "stationName", source = "station.stationName")
    HistoryResponse toResponse(WeatherHistory entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "city", ignore = true)
    @Mapping(target = "station", ignore = true)
    WeatherHistory toEntity(HistoryRequest request);

    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE
    )
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "city", ignore = true)
    @Mapping(target = "station", ignore = true)
    void updateEntity(
            HistoryRequest request,
            @MappingTarget WeatherHistory entity
    );
}