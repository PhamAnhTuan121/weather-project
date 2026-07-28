package com.weather.weather_backend.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MainResponse {

    private Double temp;

    private Integer humidity;

    private Integer pressure;

    private Double feelsLike;

}