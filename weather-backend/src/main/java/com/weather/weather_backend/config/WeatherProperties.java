package com.weather.weather_backend.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "weather.api")
public class WeatherProperties {

    /**
     * https://api.openweathermap.org/data/2.5
     */
    private String baseUrl;

    /**
     * OpenWeather API Key
     */
    private String apiKey;

}