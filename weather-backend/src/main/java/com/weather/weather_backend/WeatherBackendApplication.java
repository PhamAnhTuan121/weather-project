package com.weather.weather_backend;

import com.weather.weather_backend.config.WeatherProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties(WeatherProperties.class)
@EnableScheduling
public class WeatherBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherBackendApplication.class, args);

	}

}
