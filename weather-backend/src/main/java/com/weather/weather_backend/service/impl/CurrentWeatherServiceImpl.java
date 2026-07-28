package com.weather.weather_backend.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.weather.weather_backend.client.WeatherClient;
import com.weather.weather_backend.client.dto.OpenWeatherResponse;
import com.weather.weather_backend.client.dto.WeatherResponse;
import com.weather.weather_backend.dto.current_weather.CurrentWeatherRequest;
import com.weather.weather_backend.dto.current_weather.CurrentWeatherResponse;
import com.weather.weather_backend.entity.City;
import com.weather.weather_backend.entity.CurrentWeather;
import com.weather.weather_backend.entity.WeatherStation;
import com.weather.weather_backend.exception.ResourceNotFoundException;
import com.weather.weather_backend.mapper.CurrentWeatherMapper;
import com.weather.weather_backend.redis.constant.RedisKey;
import com.weather.weather_backend.repository.CityRepository;
import com.weather.weather_backend.repository.CurrentWeatherRepository;
import com.weather.weather_backend.repository.WeatherStationRepository;
import com.weather.weather_backend.service.CurrentWeatherService;
import com.weather.weather_backend.service.RedisService;
import com.weather.weather_backend.specification.CurrentWeatherSpecification;
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
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CurrentWeatherServiceImpl implements CurrentWeatherService {

    private static final Duration CACHE_TTL = Duration.ofMinutes(5);

    private final CurrentWeatherRepository currentWeatherRepository;
    private final CityRepository cityRepository;
    private final WeatherStationRepository weatherStationRepository;
    private final CurrentWeatherMapper currentWeatherMapper;
    private final WeatherClient weatherClient;
    private final RedisService redisService;


    @Override
    @Transactional
    public CurrentWeatherResponse create(CurrentWeatherRequest request) {

        log.info("Creating current weather for cityId={}, stationId={}",
                request.getCityId(),
                request.getStationId());

        City city = getCity(request.getCityId());

        WeatherStation station = getStation(request.getStationId());

        CurrentWeather entity = currentWeatherRepository
                .findByStationId(station.getId())
                .orElse(new CurrentWeather());

        currentWeatherMapper.updateEntity(request, entity);

        entity.setCity(city);
        entity.setStation(station);

        CurrentWeather saved = currentWeatherRepository.save(entity);

        CurrentWeatherResponse response =
                currentWeatherMapper.toResponse(saved);

        saveCache(response);

        log.info("Current weather created successfully, id={}",
                saved.getId());

        return response;
    }

    @Override
    @Transactional
    public CurrentWeatherResponse update(Long id,
                                         CurrentWeatherRequest request) {

        log.info("Updating current weather id={}", id);

        CurrentWeather entity = getCurrentWeather(id);

        City city = getCity(request.getCityId());

        WeatherStation station = getStation(request.getStationId());

        currentWeatherMapper.updateEntity(request, entity);

        entity.setCity(city);
        entity.setStation(station);

        CurrentWeather updated =
                currentWeatherRepository.save(entity);

        CurrentWeatherResponse response =
                currentWeatherMapper.toResponse(updated);

        saveCache(response);

        log.info("Current weather updated successfully id={}",
                updated.getId());

        return response;
    }

    @Override
    @Transactional
    public void delete(Long id) {

        log.info("Deleting current weather id={}", id);

        CurrentWeather entity = getCurrentWeather(id);

        Long stationId = entity.getStation().getId();

        currentWeatherRepository.delete(entity);

        deleteCache(stationId);

        log.info("Current weather deleted successfully id={}", id);
    }

    @Override
    public CurrentWeatherResponse findById(Long id) {

        CurrentWeather entity = getCurrentWeather(id);

        return currentWeatherMapper.toResponse(entity);
    }

    @Override
    public Page<CurrentWeatherResponse> findAll(Pageable pageable) {

        return currentWeatherRepository.findAll(pageable)
                .map(currentWeatherMapper::toResponse);
    }

    @Override
    public Page<CurrentWeatherResponse> search(
            Long cityId,
            Long stationId,
            BigDecimal temperatureFrom,
            BigDecimal temperatureTo,
            LocalDateTime observationFrom,
            LocalDateTime observationTo,
            Pageable pageable) {

        Specification<CurrentWeather> specification =
                CurrentWeatherSpecification.cityId(cityId)
                        .and(CurrentWeatherSpecification.stationId(stationId))
                        .and(CurrentWeatherSpecification.temperatureFrom(temperatureFrom))
                        .and(CurrentWeatherSpecification.temperatureTo(temperatureTo))
                        .and(CurrentWeatherSpecification.observationFrom(observationFrom))
                        .and(CurrentWeatherSpecification.observationTo(observationTo));

        return currentWeatherRepository.findAll(specification, pageable)
                .map(currentWeatherMapper::toResponse);
    }

    @Override
    public CurrentWeatherResponse getLatestByCity(Long cityId) {

        if (!cityRepository.existsById(cityId)) {
            throw new ResourceNotFoundException(
                    "City not found with id: " + cityId);
        }

        CurrentWeather entity = currentWeatherRepository
                .findTopByCityIdOrderByObservationTimeDesc(cityId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "No weather data found for city id: " + cityId));

        return currentWeatherMapper.toResponse(entity);
    }

    @Override
    public CurrentWeatherResponse getLatestByStation(Long stationId) {

        weatherStationRepository.findById(stationId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Weather station not found with id: " + stationId));

        CurrentWeatherResponse cache = getCache(stationId);

        if (cache != null) {

            log.info("Cache hit for station {}", stationId);

            return cache;
        }

        log.info("Cache miss for station {}", stationId);

        CurrentWeather entity = currentWeatherRepository
                .findTopByStationIdOrderByObservationTimeDesc(stationId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "No weather data found for station id: "
                                        + stationId));

        CurrentWeatherResponse response =
                currentWeatherMapper.toResponse(entity);

        saveCache(response);

        return response;
    }

    @Override
    @Transactional
    public void syncAllStations() {

        log.info("Starting weather synchronization...");

        List<WeatherStation> stations = weatherStationRepository.findAll();

        int success = 0;
        int failed = 0;

        for (WeatherStation station : stations) {

            try {

                syncByStation(station.getId());

                success++;

            } catch (Exception ex) {

                failed++;

                log.error(
                        "Failed to synchronize station {}",
                        station.getStationCode(),
                        ex);

            }

        }

        log.info(
                "Weather synchronization completed. Success={}, Failed={}",
                success,
                failed);
    }

    @Override
    @Transactional
    public CurrentWeatherResponse syncByStation(Long stationId) {

        log.info("Synchronizing weather for station {}", stationId);

        WeatherStation station = getStation(stationId);

        OpenWeatherResponse apiResponse =
                weatherClient.getCurrentWeather(
                        station.getLatitude(),
                        station.getLongitude());

        CurrentWeather entity =
                currentWeatherRepository
                        .findByStationId(stationId)
                        .orElse(new CurrentWeather());

        entity.setStation(station);
        entity.setCity(station.getCity());

        updateWeather(entity, apiResponse);

        CurrentWeather saved =
                currentWeatherRepository.save(entity);

        CurrentWeatherResponse response =
                currentWeatherMapper.toResponse(saved);

        saveCache(response);

        log.info(
                "Weather synchronized successfully for station {}",
                stationId);

        return response;
    }

    private City getCity(Long cityId) {

        return cityRepository.findById(cityId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "City not found with id: " + cityId));
    }

    private WeatherStation getStation(Long stationId) {

        return weatherStationRepository.findById(stationId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Weather station not found with id: " + stationId));
    }

    private CurrentWeather getCurrentWeather(Long id) {

        return currentWeatherRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Current weather not found with id: " + id));
    }

    private CurrentWeatherResponse getCache(Long stationId) {

        return redisService.get(
                weatherKey(stationId),
                new TypeReference<CurrentWeatherResponse>() {}
        );
    }

    private void updateWeather(CurrentWeather entity,
                               OpenWeatherResponse response) {

        if (response.getMain() != null) {

            entity.setTemperature(
                    BigDecimal.valueOf(response.getMain().getTemp()));

            entity.setHumidity(
                    response.getMain().getHumidity());

            entity.setPressure(
                    response.getMain().getPressure());
        }

        if (response.getWind() != null) {

            entity.setWindSpeed(
                    BigDecimal.valueOf(response.getWind().getSpeed()));

            entity.setWindDirection(
                    response.getWind().getDeg() == null
                            ? null
                            : response.getWind().getDeg().toString());
        }

        entity.setObservationTime(LocalDateTime.now());

        if (response.getClouds() != null) {
            entity.setCloud(response.getClouds().getAll());
        }

        entity.setVisibility(response.getVisibility());

        if (response.getWeather() != null &&
                !response.getWeather().isEmpty()) {

            WeatherResponse weather =
                    response.getWeather().get(0);

            entity.setDescription(weather.getDescription());

            entity.setIcon(weather.getIcon());
        }

    }

    private String weatherKey(Long stationId) {

        return RedisKey.CURRENT_WEATHER + stationId;
    }

    private void saveCache(CurrentWeatherResponse response) {

        redisService.save(
                weatherKey(response.getStationId()),
                response,
                CACHE_TTL);
    }

    private void deleteCache(Long stationId) {

        redisService.delete(weatherKey(stationId));
    }

}