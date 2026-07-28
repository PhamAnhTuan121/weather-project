package com.weather.weather_backend.service.impl;

import com.weather.weather_backend.dto.history.HistoryRequest;
import com.weather.weather_backend.dto.history.HistoryResponse;
import com.weather.weather_backend.dto.current_weather.CurrentWeatherResponse;
import com.weather.weather_backend.entity.City;
import com.weather.weather_backend.entity.CurrentWeather;
import com.weather.weather_backend.entity.WeatherHistory;
import com.weather.weather_backend.entity.WeatherStation;
import com.weather.weather_backend.exception.ResourceNotFoundException;
import com.weather.weather_backend.mapper.WeatherHistoryMapper;
import com.weather.weather_backend.repository.CityRepository;
import com.weather.weather_backend.repository.CurrentWeatherRepository;
import com.weather.weather_backend.repository.WeatherHistoryRepository;
import com.weather.weather_backend.repository.WeatherStationRepository;
import com.weather.weather_backend.service.WeatherHistoryService;
import com.weather.weather_backend.specification.WeatherHistorySpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WeatherHistoryServiceImpl implements WeatherHistoryService {

    private final WeatherHistoryRepository weatherHistoryRepository;

    private final CurrentWeatherRepository currentWeatherRepository;

    private final CityRepository cityRepository;

    private final WeatherStationRepository weatherStationRepository;

    private final WeatherHistoryMapper weatherHistoryMapper;


    @Override
    @Transactional
    public HistoryResponse create(HistoryRequest request) {

        log.info("Creating weather history");

        City city = getCity(request.getCityId());

        WeatherStation station =
                getStation(request.getStationId());

        WeatherHistory entity =
                weatherHistoryMapper.toEntity(request);

        entity.setCity(city);
        entity.setStation(station);

        WeatherHistory saved =
                weatherHistoryRepository.save(entity);

        log.info("History created id={}", saved.getId());

        return weatherHistoryMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public HistoryResponse update(Long id,
                                  HistoryRequest request) {

        log.info("Updating history id={}", id);

        WeatherHistory entity =
                getHistory(id);

        City city =
                getCity(request.getCityId());

        WeatherStation station =
                getStation(request.getStationId());

        weatherHistoryMapper.updateEntity(
                request,
                entity);

        entity.setCity(city);
        entity.setStation(station);

        WeatherHistory updated =
                weatherHistoryRepository.save(entity);

        log.info("History updated id={}", id);

        return weatherHistoryMapper.toResponse(updated);
    }

    @Override
    @Transactional
    public void delete(Long id) {

        log.info("Deleting history id={}", id);

        WeatherHistory entity =
                getHistory(id);

        weatherHistoryRepository.delete(entity);

        log.info("History deleted id={}", id);
    }

    @Override
    public HistoryResponse findById(Long id) {

        return weatherHistoryMapper.toResponse(
                getHistory(id));
    }

    @Override
    public Page<HistoryResponse> findAll(
            Pageable pageable) {

        return weatherHistoryRepository
                .findAll(pageable)
                .map(weatherHistoryMapper::toResponse);
    }

    @Override
    public Page<HistoryResponse> search(
            Long cityId,
            Long stationId,
            BigDecimal temperatureFrom,
            BigDecimal temperatureTo,
            LocalDateTime observationFrom,
            LocalDateTime observationTo,
            Pageable pageable) {

        Specification<WeatherHistory> specification =
                WeatherHistorySpecification.cityId(cityId)
                        .and(WeatherHistorySpecification.stationId(stationId))
                        .and(WeatherHistorySpecification.temperatureFrom(temperatureFrom))
                        .and(WeatherHistorySpecification.temperatureTo(temperatureTo))
                        .and(WeatherHistorySpecification.observationFrom(observationFrom))
                        .and(WeatherHistorySpecification.observationTo(observationTo));

        return weatherHistoryRepository
                .findAll(specification, pageable)
                .map(weatherHistoryMapper::toResponse);
    }

    @Override
    public List<HistoryResponse> getHistoryByCity(Long cityId) {

        getCity(cityId);

        return weatherHistoryRepository
                .findByCityIdOrderByObservationTimeDesc(cityId)
                .stream()
                .map(weatherHistoryMapper::toResponse)
                .toList();
    }

    @Override
    public List<HistoryResponse> getHistoryByStation(Long stationId) {

        getStation(stationId);

        return weatherHistoryRepository
                .findByStationIdOrderByObservationTimeDesc(stationId)
                .stream()
                .map(weatherHistoryMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public void archiveCurrentWeather() {

        log.info("Starting weather history archive...");

        List<CurrentWeather> currentWeathers =
                currentWeatherRepository.findAll();

        int success = 0;
        int skipped = 0;

        for (CurrentWeather current : currentWeathers) {

            boolean exists =
                    weatherHistoryRepository
                            .existsByStationIdAndObservationTime(
                                    current.getStation().getId(),
                                    current.getObservationTime());

            if (exists) {

                skipped++;
                continue;

            }

            WeatherHistory history =
                    copyFromCurrentWeather(current);

            weatherHistoryRepository.save(history);

            success++;
        }

        log.info(
                "Weather history archive completed. Saved={}, Skipped={}",
                success,
                skipped);
    }

    private WeatherHistory copyFromCurrentWeather(
            CurrentWeather current) {

        return WeatherHistory.builder()
                .city(current.getCity())
                .station(current.getStation())
                .observationTime(current.getObservationTime())
                .temperature(current.getTemperature())
                .feelsLike(current.getFeelsLike())
                .humidity(current.getHumidity())
                .pressure(current.getPressure())
                .windSpeed(current.getWindSpeed())
                .windDirection(current.getWindDirection())
                .visibility(current.getVisibility())
                .cloud(current.getCloud())
                .uvIndex(current.getUvIndex())
                .description(current.getDescription())
                .icon(current.getIcon())
                .build();
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
                                "Weather station not found with id: "
                                        + stationId));
    }

    private WeatherHistory getHistory(Long id) {

        return weatherHistoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "History not found with id: " + id));
    }
}
