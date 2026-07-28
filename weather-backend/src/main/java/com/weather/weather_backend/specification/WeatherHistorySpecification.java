package com.weather.weather_backend.specification;

import com.weather.weather_backend.entity.WeatherHistory;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class WeatherHistorySpecification {

    private WeatherHistorySpecification() {
    }

    public static Specification<WeatherHistory> cityId(Long cityId) {

        return (root, query, cb) ->
                cityId == null
                        ? null
                        : cb.equal(root.get("city").get("id"), cityId);
    }

    public static Specification<WeatherHistory> stationId(Long stationId) {

        return (root, query, cb) ->
                stationId == null
                        ? null
                        : cb.equal(root.get("station").get("id"), stationId);
    }

    public static Specification<WeatherHistory> temperatureFrom(
            BigDecimal temperatureFrom) {

        return (root, query, cb) ->
                temperatureFrom == null
                        ? null
                        : cb.greaterThanOrEqualTo(
                        root.get("temperature"),
                        temperatureFrom);
    }

    public static Specification<WeatherHistory> temperatureTo(
            BigDecimal temperatureTo) {

        return (root, query, cb) ->
                temperatureTo == null
                        ? null
                        : cb.lessThanOrEqualTo(
                        root.get("temperature"),
                        temperatureTo);
    }

    public static Specification<WeatherHistory> observationFrom(
            LocalDateTime observationFrom) {

        return (root, query, cb) ->
                observationFrom == null
                        ? null
                        : cb.greaterThanOrEqualTo(
                        root.get("observationTime"),
                        observationFrom);
    }

    public static Specification<WeatherHistory> observationTo(
            LocalDateTime observationTo) {

        return (root, query, cb) ->
                observationTo == null
                        ? null
                        : cb.lessThanOrEqualTo(
                        root.get("observationTime"),
                        observationTo);
    }
}