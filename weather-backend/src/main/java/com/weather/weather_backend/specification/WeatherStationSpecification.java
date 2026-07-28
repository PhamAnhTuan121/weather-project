package com.weather.weather_backend.specification;

import com.weather.weather_backend.entity.StationStatus;
import com.weather.weather_backend.entity.WeatherStation;

import org.springframework.data.jpa.domain.Specification;

public class WeatherStationSpecification {

    private WeatherStationSpecification() {
    }

    public static Specification<WeatherStation> keyword(String keyword) {

        return (root, query, cb) -> {

            if (keyword == null || keyword.isBlank()) {
                return cb.conjunction();
            }

            String value = "%" + keyword.trim().toLowerCase() + "%";

            return cb.or(
                    cb.like(cb.lower(root.get("stationCode")), value),
                    cb.like(cb.lower(root.get("stationName")), value),
                    cb.like(cb.lower(root.get("provider")), value)
            );
        };
    }

    public static Specification<WeatherStation> cityId(Long cityId) {

        return (root, query, cb) -> {

            if (cityId == null) {
                return cb.conjunction();
            }

            return cb.equal(root.get("city").get("id"), cityId);
        };
    }

    public static Specification<WeatherStation> status(StationStatus status) {

        return (root, query, cb) -> {

            if (status == null) {
                return cb.conjunction();
            }

            return cb.equal(root.get("status"), status);
        };
    }
}