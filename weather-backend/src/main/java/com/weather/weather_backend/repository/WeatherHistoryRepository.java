package com.weather.weather_backend.repository;

import com.weather.weather_backend.entity.WeatherHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDateTime;
import java.util.List;

public interface WeatherHistoryRepository extends
        JpaRepository<WeatherHistory, Long>,
        JpaSpecificationExecutor<WeatherHistory> {

    List<WeatherHistory> findByCityIdOrderByObservationTimeDesc(Long cityId);

    List<WeatherHistory> findByStationIdOrderByObservationTimeDesc(Long stationId);

    boolean existsByStationIdAndObservationTime(
            Long stationId,
            LocalDateTime observationTime
    );

    void deleteByStationId(Long stationId);
}