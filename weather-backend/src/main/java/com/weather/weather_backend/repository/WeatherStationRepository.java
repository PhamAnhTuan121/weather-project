package com.weather.weather_backend.repository;

import com.weather.weather_backend.entity.WeatherStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface WeatherStationRepository extends
        JpaRepository<WeatherStation, Long>,
        JpaSpecificationExecutor<WeatherStation> {

    boolean existsByStationCode(String stationCode);

    boolean existsByStationCodeAndIdNot(String stationCode, Long id);

    Optional<WeatherStation> findByStationCode(String stationCode);
}