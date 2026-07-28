package com.weather.weather_backend.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ForecastItemResponse {

    private Long dt;

    @JsonProperty("dt_txt")
    private String dateTime;

    private MainResponse main;

    private WindResponse wind;

    private CloudsResponse clouds;

    private Integer visibility;

    private List<WeatherResponse> weather;

}