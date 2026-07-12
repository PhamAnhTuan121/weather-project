package com.weather.weather_backend.repository;

import com.weather.weather_backend.entity.Region;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {

    Optional<Region> findByName(String name);

    boolean existsByName(String name);

    Page<Region> findByNameContainingIgnoreCase(
            String keyword,
            Pageable pageable
    );
}