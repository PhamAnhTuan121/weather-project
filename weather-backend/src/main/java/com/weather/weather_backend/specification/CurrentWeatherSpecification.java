package com.weather.weather_backend.specification;

import com.weather.weather_backend.entity.CurrentWeather;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public final class CurrentWeatherSpecification {

    private CurrentWeatherSpecification() {
    }

    public static Specification<CurrentWeather> cityId(Long cityId) {

        return (root, query, cb) -> {

            if (cityId == null) {
                return cb.conjunction();
            }

            return cb.equal(root.get("city").get("id"), cityId);
        };
    }

    public static Specification<CurrentWeather> stationId(Long stationId) {

        return (root, query, cb) -> {

            if (stationId == null) {
                return cb.conjunction();
            }

            return cb.equal(root.get("station").get("id"), stationId);
        };
    }

    public static Specification<CurrentWeather> temperatureFrom(BigDecimal value) {

        return (root, query, cb) -> {

            if (value == null) {
                return cb.conjunction();
            }

            return cb.greaterThanOrEqualTo(root.get("temperature"), value);
        };
    }

    public static Specification<CurrentWeather> temperatureTo(BigDecimal value) {

        return (root, query, cb) -> {

            if (value == null) {
                return cb.conjunction();
            }

            return cb.lessThanOrEqualTo(root.get("temperature"), value);
        };
    }

    public static Specification<CurrentWeather> observationFrom(LocalDateTime from) {

        return (root, query, cb) -> {

            if (from == null) {
                return cb.conjunction();
            }

            return cb.greaterThanOrEqualTo(root.get("observationTime"), from);
        };
    }

    public static Specification<CurrentWeather> observationTo(LocalDateTime to) {

        return (root, query, cb) -> {

            if (to == null) {
                return cb.conjunction();
            }

            return cb.lessThanOrEqualTo(root.get("observationTime"), to);
        };
    }
}