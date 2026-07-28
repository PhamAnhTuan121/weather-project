package com.weather.weather_backend.client;

import com.weather.weather_backend.client.dto.OpenWeatherForecastResponse;
import com.weather.weather_backend.client.dto.OpenWeatherResponse;

import java.math.BigDecimal;

public interface WeatherClient {

    OpenWeatherResponse getCurrentWeather(
            BigDecimal latitude,
            BigDecimal longitude);

    OpenWeatherForecastResponse getForecast(
            BigDecimal latitude,
            BigDecimal longitude);

}