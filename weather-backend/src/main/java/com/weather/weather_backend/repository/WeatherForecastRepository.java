package com.weather.weather_backend.repository;

import com.weather.weather_backend.entity.WeatherForecast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDateTime;
import java.util.List;

public interface WeatherForecastRepository extends
        JpaRepository<WeatherForecast, Long>,
        JpaSpecificationExecutor<WeatherForecast> {

    List<WeatherForecast> findByStationIdOrderByForecastTimeAsc(Long stationId);

    List<WeatherForecast> findByCityIdOrderByForecastTimeAsc(Long cityId);

    void deleteByStationId(Long stationId);

    boolean existsByStationIdAndForecastTime(
            Long stationId,
            LocalDateTime forecastTime
    );
}