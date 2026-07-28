package com.weather.weather_backend.client.impl;

import com.weather.weather_backend.client.WeatherClient;
import com.weather.weather_backend.client.dto.OpenWeatherForecastResponse;
import com.weather.weather_backend.client.dto.OpenWeatherResponse;
import com.weather.weather_backend.config.WeatherProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.math.BigDecimal;

@Slf4j
@Component
@RequiredArgsConstructor
public class WeatherClientImpl implements WeatherClient {

    private final RestClient weatherRestClient;
    private final WeatherProperties weatherProperties;

    @Override
    public OpenWeatherResponse getCurrentWeather(
            BigDecimal latitude,
            BigDecimal longitude) {

        log.info("Calling OpenWeather API: lat={}, lon={}",
                latitude, longitude);

        try {

            OpenWeatherResponse response = weatherRestClient
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/weather")
                            .queryParam("lat", latitude)
                            .queryParam("lon", longitude)
                            .queryParam("appid", weatherProperties.getApiKey())
                            .queryParam("units", "metric")
                            .build())
                    .retrieve()
                    .body(OpenWeatherResponse.class);

            log.info("OpenWeather API called successfully.");

            return response;

        } catch (RestClientException ex) {

            log.error("Cannot call OpenWeather API", ex);

            throw new RuntimeException(
                    "Cannot get weather information from OpenWeather.",
                    ex);

        }

    }

    @Override
    public OpenWeatherForecastResponse getForecast(
            BigDecimal latitude,
            BigDecimal longitude) {

        log.info("Calling OpenWeather Forecast API: lat={}, lon={}",
                latitude, longitude);

        try {

            OpenWeatherForecastResponse response = weatherRestClient
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/forecast")
                            .queryParam("lat", latitude)
                            .queryParam("lon", longitude)
                            .queryParam("appid", weatherProperties.getApiKey())
                            .queryParam("units", "metric")
                            .build())
                    .retrieve()
                    .body(OpenWeatherForecastResponse.class);

            log.info("OpenWeather Forecast API called successfully.");

            return response;

        } catch (RestClientException ex) {

            log.error("Cannot call OpenWeather Forecast API", ex);

            throw new RuntimeException(
                    "Cannot get forecast information from OpenWeather.",
                    ex);

        }
    }

}