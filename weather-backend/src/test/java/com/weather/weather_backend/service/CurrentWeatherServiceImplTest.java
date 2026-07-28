package com.weather.weather_backend.service;

import com.weather.weather_backend.client.WeatherClient;
import com.weather.weather_backend.exception.ResourceNotFoundException;
import com.weather.weather_backend.mapper.CurrentWeatherMapper;
import com.weather.weather_backend.repository.CityRepository;
import com.weather.weather_backend.repository.CurrentWeatherRepository;
import com.weather.weather_backend.service.impl.CurrentWeatherServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class CurrentWeatherServiceImplTest {

    @InjectMocks
    private CurrentWeatherServiceImpl currentWeatherService;

    @Mock
    private CurrentWeatherRepository currentWeatherRepository;

    @Mock
    private CityRepository cityRepository;

    @Mock
    private WeatherClient weatherClient;

    @Mock
    private RedisService redisService;

    @Mock
    private CurrentWeatherMapper currentWeatherMapper;

    @Test
    void shouldThrowExceptionWhenCityNotFound() {

        when(cityRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> currentWeatherService.getLatestByCity(1L)
        );
    }
}
