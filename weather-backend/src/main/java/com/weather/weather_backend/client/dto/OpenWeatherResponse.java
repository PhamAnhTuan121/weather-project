package com.weather.weather_backend.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherResponse {

    private CoordResponse coord;

    private MainResponse main;

    private WindResponse wind;

    private List<WeatherResponse> weather;

    private CloudsResponse clouds;

    private Integer visibility;

    private Long dt;

    private String name;
}