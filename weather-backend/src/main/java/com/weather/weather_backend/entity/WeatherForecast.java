package com.weather.weather_backend.entity;

import com.weather.weather_backend.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "weather_forecasts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeatherForecast extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_id", nullable = false)
    private WeatherStation station;

    @Column(nullable = false)
    private LocalDateTime forecastTime;

    @Column(precision = 5, scale = 2, nullable = false)
    private BigDecimal temperature;

    @Column(precision = 5, scale = 2)
    private BigDecimal feelsLike;

    private Integer humidity;

    private Integer pressure;

    @Column(precision = 5, scale = 2)
    private BigDecimal windSpeed;

    @Column(length = 30)
    private String windDirection;

    private Integer cloud;

    @Column(length = 255)
    private String description;

    @Column(length = 30)
    private String icon;

    private Integer visibility;

}