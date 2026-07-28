package com.weather.weather_backend.repository;

import com.weather.weather_backend.entity.CurrentWeather;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CurrentWeatherRepository
        extends JpaRepository<CurrentWeather, Long>,
        JpaSpecificationExecutor<CurrentWeather> {

    Optional<CurrentWeather> findByStationId(Long stationId);

    Optional<CurrentWeather> findTopByCityIdOrderByObservationTimeDesc(
            Long cityId);

    Optional<CurrentWeather> findTopByStationIdOrderByObservationTimeDesc(
            Long stationId);

}