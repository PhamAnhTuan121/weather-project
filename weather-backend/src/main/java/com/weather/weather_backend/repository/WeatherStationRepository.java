package com.weather.weather_backend.repository;

import com.weather.weather_backend.entity.WeatherStation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WeatherStationRepository extends JpaRepository<WeatherStation, Long> {

    Optional<WeatherStation> findByStationCode(String stationCode);

    List<WeatherStation> findByCityId(Long cityId);

}
