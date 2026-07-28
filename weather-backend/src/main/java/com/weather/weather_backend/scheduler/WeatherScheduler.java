package com.weather.weather_backend.scheduler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import com.weather.weather_backend.service.CurrentWeatherService;
import com.weather.weather_backend.service.WeatherForecastService;
import com.weather.weather_backend.service.WeatherHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(
        prefix = "scheduler",
        name = "enabled",
        havingValue = "true",
        matchIfMissing = true
)
public class WeatherScheduler {

    private final CurrentWeatherService currentWeatherService;
    private final WeatherForecastService weatherForecastService;
    private final WeatherHistoryService weatherHistoryService;

    @Scheduled(cron = "${scheduler.current-weather.cron}")
    public void syncCurrentWeather() {

        log.info("Running current weather scheduler");

        currentWeatherService.syncAllStations();
    }

    @Scheduled(cron = "${scheduler.forecast.cron}")
    public void syncForecast() {

        log.info("Running forecast scheduler");

        weatherForecastService.syncAllStations();
    }

    @Scheduled(cron = "${scheduler.history.cron}")
    public void archiveHistory() {

        log.info("Running history scheduler");

        weatherHistoryService.archiveCurrentWeather();
    }

}