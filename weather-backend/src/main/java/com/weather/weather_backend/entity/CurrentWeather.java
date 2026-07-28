package com.weather.weather_backend.entity;

import com.weather.weather_backend.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "current_weather")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CurrentWeather extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal temperature;

    @Column(nullable = false)
    private Integer humidity;

    @Column(nullable = false)
    private Integer pressure;

    @Column(name = "wind_speed", precision = 5, scale = 2)
    private BigDecimal windSpeed;

    @Column(name = "wind_direction")
    private String windDirection;

    private Integer visibility;

    private Integer cloud;

    @Column(name = "uv_index", precision = 4, scale = 2)
    private BigDecimal uvIndex;

    private String description;

    private String icon;

    @Column(name = "observation_time")
    private LocalDateTime observationTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_id", nullable = false)
    private WeatherStation station;

    @Column(name = "feels_like", precision = 5, scale = 2)
    private BigDecimal feelsLike;
}