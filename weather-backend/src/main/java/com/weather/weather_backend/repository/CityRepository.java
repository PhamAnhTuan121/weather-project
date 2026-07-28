package com.weather.weather_backend.repository;

import com.weather.weather_backend.entity.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    boolean existsByName(String name);

    Page<City> findByNameContainingIgnoreCase(
            String keyword,
            Pageable pageable);

    Page<City> findByRegionId(
            Long regionId,
            Pageable pageable);

    boolean existsByNameAndRegionId(
            String name,
            Long regionId);
}
