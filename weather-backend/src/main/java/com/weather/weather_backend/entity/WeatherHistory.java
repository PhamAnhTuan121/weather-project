package com.weather.weather_backend.entity;

import com.weather.weather_backend.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "weather_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeatherHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_id")
    private WeatherStation station;

    private LocalDateTime observationTime;

    @Column(precision = 5, scale = 2)
    private BigDecimal temperature;

    @Column(precision = 5, scale = 2)
    private BigDecimal feelsLike;

    private Integer humidity;

    private Integer pressure;

    @Column(precision = 5, scale = 2)
    private BigDecimal windSpeed;

    private String windDirection;

    private Integer visibility;

    private Integer cloud;

    @Column(precision = 5, scale = 2)
    private BigDecimal uvIndex;

    private String description;

    private String icon;

}