package com.weather.weather_backend.service.impl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.weather.weather_backend.client.WeatherClient;
import com.weather.weather_backend.client.dto.ForecastItemResponse;
import com.weather.weather_backend.client.dto.OpenWeatherForecastResponse;
import com.weather.weather_backend.client.dto.WeatherResponse;
import com.weather.weather_backend.dto.forecast.ForecastRequest;
import com.weather.weather_backend.dto.forecast.ForecastResponse;
import com.weather.weather_backend.entity.City;
import com.weather.weather_backend.entity.WeatherForecast;
import com.weather.weather_backend.entity.WeatherStation;
import com.weather.weather_backend.exception.ResourceNotFoundException;
import com.weather.weather_backend.mapper.WeatherForecastMapper;
import com.weather.weather_backend.redis.constant.RedisKey;
import com.weather.weather_backend.repository.CityRepository;
import com.weather.weather_backend.repository.WeatherForecastRepository;
import com.weather.weather_backend.repository.WeatherStationRepository;
import com.weather.weather_backend.service.RedisService;
import com.weather.weather_backend.service.WeatherForecastService;
import com.weather.weather_backend.specification.WeatherForecastSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WeatherForecastServiceImpl
        implements WeatherForecastService {

    private static final Duration CACHE_TTL =
            Duration.ofMinutes(30);

    private final WeatherForecastRepository weatherForecastRepository;
    private final CityRepository cityRepository;
    private final WeatherStationRepository weatherStationRepository;
    private final WeatherForecastMapper weatherForecastMapper;
    private final WeatherClient weatherClient;
    private final RedisService redisService;

    @Override
    @Transactional
    public ForecastResponse create(ForecastRequest request) {

        log.info("Creating forecast for station={}",
                request.getStationId());

        City city = getCity(request.getCityId());

        WeatherStation station =
                getStation(request.getStationId());

        WeatherForecast entity =
                weatherForecastMapper.toEntity(request);

        entity.setCity(city);
        entity.setStation(station);

        WeatherForecast saved =
                weatherForecastRepository.save(entity);

        ForecastResponse response =
                weatherForecastMapper.toResponse(saved);

        deleteCache(station.getId());

        log.info("Forecast created successfully id={}",
                saved.getId());

        return response;
    }

    @Override
    @Transactional
    public ForecastResponse update(Long id,
                                   ForecastRequest request) {

        log.info("Updating forecast id={}", id);

        WeatherForecast entity =
                getForecast(id);

        City city =
                getCity(request.getCityId());

        WeatherStation station =
                getStation(request.getStationId());

        weatherForecastMapper.updateEntity(request, entity);

        entity.setCity(city);
        entity.setStation(station);

        WeatherForecast updated =
                weatherForecastRepository.save(entity);

        deleteCache(station.getId());

        log.info("Forecast updated id={}",
                updated.getId());

        return weatherForecastMapper.toResponse(updated);
    }

    @Override
    @Transactional
    public void delete(Long id) {

        log.info("Deleting forecast {}", id);

        WeatherForecast entity =
                getForecast(id);

        Long stationId =
                entity.getStation().getId();

        weatherForecastRepository.delete(entity);

        deleteCache(stationId);

        log.info("Forecast deleted {}", id);

    }

    private City getCity(Long cityId) {

        return cityRepository.findById(cityId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "City not found with id: "
                                        + cityId));

    }

    private WeatherStation getStation(Long stationId) {

        return weatherStationRepository.findById(stationId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Weather station not found with id: "
                                        + stationId));

    }

    private WeatherForecast getForecast(Long id) {

        return weatherForecastRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Forecast not found with id: "
                                        + id));

    }

    private String forecastKey(Long stationId) {

        return RedisKey.FORECAST + stationId;

    }

    private void deleteCache(Long stationId) {

        redisService.delete(forecastKey(stationId));

    }

    private List<ForecastResponse> getCache(Long stationId) {

        return redisService.get(
                forecastKey(stationId),
                new TypeReference<List<ForecastResponse>>() {}
        );
    }

    @Override
    public ForecastResponse findById(Long id) {

        WeatherForecast entity = getForecast(id);

        return weatherForecastMapper.toResponse(entity);

    }

    @Override
    public Page<ForecastResponse> findAll(Pageable pageable) {

        return weatherForecastRepository
                .findAll(pageable)
                .map(weatherForecastMapper::toResponse);

    }

    @Override
    public Page<ForecastResponse> search(
            Long cityId,
            Long stationId,
            BigDecimal temperatureFrom,
            BigDecimal temperatureTo,
            LocalDateTime forecastFrom,
            LocalDateTime forecastTo,
            Pageable pageable) {

        Specification<WeatherForecast> specification =
                WeatherForecastSpecification.cityId(cityId)
                        .and(WeatherForecastSpecification.stationId(stationId))
                        .and(WeatherForecastSpecification.temperatureFrom(temperatureFrom))
                        .and(WeatherForecastSpecification.temperatureTo(temperatureTo))
                        .and(WeatherForecastSpecification.forecastFrom(forecastFrom))
                        .and(WeatherForecastSpecification.forecastTo(forecastTo));

        return weatherForecastRepository
                .findAll(specification, pageable)
                .map(weatherForecastMapper::toResponse);

    }

    private void saveCache(Long stationId,
                           List<ForecastResponse> forecasts) {

        redisService.save(
                forecastKey(stationId),
                forecasts,
                CACHE_TTL);

    }

    @Override
    public List<ForecastResponse> getForecastByStation(Long stationId) {

        getStation(stationId);

        List<ForecastResponse> cache = getCache(stationId);

        if (cache != null && !cache.isEmpty()) {

            log.info("Forecast cache hit station={}", stationId);

            return cache;
        }

        log.info("Forecast cache miss station={}", stationId);

        List<ForecastResponse> response =
                weatherForecastRepository
                        .findByStationIdOrderByForecastTimeAsc(stationId)
                        .stream()
                        .map(weatherForecastMapper::toResponse)
                        .toList();

        saveCache(stationId, response);

        return response;

    }

    @Override
    public List<ForecastResponse> getForecastByCity(Long cityId) {

        getCity(cityId);

        return weatherForecastRepository
                .findByCityIdOrderByForecastTimeAsc(cityId)
                .stream()
                .map(weatherForecastMapper::toResponse)
                .toList();

    }

    @Override
    @Transactional
    public List<ForecastResponse> syncForecast(Long stationId) {

        log.info("Synchronizing forecast for station={}", stationId);

        WeatherStation station = getStation(stationId);

        OpenWeatherForecastResponse apiResponse =
                weatherClient.getForecast(
                        station.getLatitude(),
                        station.getLongitude());

        weatherForecastRepository.deleteByStationId(stationId);

        List<WeatherForecast> forecasts = new ArrayList<>();

        if (apiResponse.getList() != null) {

            for (ForecastItemResponse item : apiResponse.getList()) {

                WeatherForecast entity = new WeatherForecast();

                entity.setStation(station);
                entity.setCity(station.getCity());

                updateForecast(entity, item);

                forecasts.add(entity);
            }

        }

        List<WeatherForecast> saved =
                weatherForecastRepository.saveAll(forecasts);

        List<ForecastResponse> response =
                saved.stream()
                        .map(weatherForecastMapper::toResponse)
                        .toList();

        saveCache(stationId, response);

        log.info("Forecast synchronized successfully. station={}, records={}",
                stationId,
                response.size());

        return response;
    }

    @Override
    @Transactional
    public void syncAllStations() {

        log.info("Starting forecast synchronization...");

        List<WeatherStation> stations =
                weatherStationRepository.findAll();

        int success = 0;
        int failed = 0;

        for (WeatherStation station : stations) {

            try {

                syncForecast(station.getId());

                success++;

            } catch (Exception ex) {

                failed++;

                log.error(
                        "Failed to synchronize forecast for station {}",
                        station.getStationCode(),
                        ex);

            }

        }

        log.info(
                "Forecast synchronization completed. Success={}, Failed={}",
                success,
                failed);

    }

    private void updateForecast(
            WeatherForecast entity,
            ForecastItemResponse item) {

        entity.setForecastTime(
                LocalDateTime.parse(
                        item.getDateTime(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        entity.setTemperature(
                BigDecimal.valueOf(item.getMain().getTemp()));

        entity.setFeelsLike(
                item.getMain().getFeelsLike() == null
                        ? null
                        : BigDecimal.valueOf(item.getMain().getFeelsLike()));

        entity.setHumidity(
                item.getMain().getHumidity());

        entity.setPressure(
                item.getMain().getPressure());

        entity.setWindSpeed(
                BigDecimal.valueOf(item.getWind().getSpeed()));

        entity.setWindDirection(
                item.getWind().getDeg() == null
                        ? null
                        : item.getWind().getDeg().toString());

        entity.setVisibility(
                item.getVisibility());

        if (item.getClouds() != null) {

            entity.setCloud(
                    item.getClouds().getAll());

        }

        if (item.getWeather() != null &&
                !item.getWeather().isEmpty()) {

            WeatherResponse weather =
                    item.getWeather().get(0);

            entity.setDescription(
                    weather.getDescription());

            entity.setIcon(
                    weather.getIcon());

        }

    }
}

