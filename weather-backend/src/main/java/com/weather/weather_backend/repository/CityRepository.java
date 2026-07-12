package com.weather.weather_backend.repository;

import com.weather.weather_backend.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Long> {

    List<City> findByRegionId(Long regionId);

}
