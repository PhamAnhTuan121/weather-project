package com.weather.weather_backend.dto.history;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HistoryResponse {
    private Long id;

    private Long cityId;
    private String cityName;

    private Long stationId;
    private String stationCode;
    private String stationName;
}
