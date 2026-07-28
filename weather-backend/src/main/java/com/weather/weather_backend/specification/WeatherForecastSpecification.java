package com.weather.weather_backend.specification;

import com.weather.weather_backend.entity.WeatherForecast;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class WeatherForecastSpecification {

    public static Specification<WeatherForecast> cityId(Long cityId) {
        return (root, query, cb) ->
                cityId == null ? null :
                        cb.equal(root.get("city").get("id"), cityId);
    }

    public static Specification<WeatherForecast> stationId(Long stationId) {
        return (root, query, cb) ->
                stationId == null ? null :
                        cb.equal(root.get("station").get("id"), stationId);
    }

    public static Specification<WeatherForecast> forecastFrom(LocalDateTime from) {
        return (root, query, cb) ->
                from == null ? null :
                        cb.greaterThanOrEqualTo(root.get("forecastTime"), from);
    }

    public static Specification<WeatherForecast> forecastTo(LocalDateTime to) {
        return (root, query, cb) ->
                to == null ? null :
                        cb.lessThanOrEqualTo(root.get("forecastTime"), to);
    }

    public static Specification<WeatherForecast> temperatureFrom(BigDecimal from) {
        return (root, query, cb) ->
                from == null ? null :
                        cb.greaterThanOrEqualTo(root.get("temperature"), from);
    }

    public static Specification<WeatherForecast> temperatureTo(BigDecimal to) {
        return (root, query, cb) ->
                to == null ? null :
                        cb.lessThanOrEqualTo(root.get("temperature"), to);
    }

}