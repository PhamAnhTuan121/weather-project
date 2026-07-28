package com.weather.weather_backend.entity;

import com.weather.weather_backend.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "weather_stations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeatherStation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "station_code", nullable = false, unique = true, length = 50)
    private String stationCode;

    @Column(name = "station_name", nullable = false, length = 150)
    private String stationName;

    @Column(nullable = false, length = 50)
    private String provider;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StationStatus status;

    @Column(precision = 10, scale = 7, nullable = false)
    private BigDecimal latitude;

    @Column(precision = 10, scale = 7, nullable = false)
    private BigDecimal longitude;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", nullable = false)
    private City city;
}