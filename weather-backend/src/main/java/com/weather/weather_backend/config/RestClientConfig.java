package com.weather.weather_backend.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@RequiredArgsConstructor
public class RestClientConfig {

    private final WeatherProperties weatherProperties;

    @Bean
    public RestClient weatherRestClient(RestClient.Builder builder) {

        return builder
                .baseUrl(weatherProperties.getBaseUrl())
                .build();
    }

}