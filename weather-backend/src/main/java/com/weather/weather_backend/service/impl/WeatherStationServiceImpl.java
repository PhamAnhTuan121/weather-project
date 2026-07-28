package com.weather.weather_backend.service.impl;


import com.weather.weather_backend.dto.weather_station.WeatherStationRequest;
import com.weather.weather_backend.dto.weather_station.WeatherStationResponse;
import com.weather.weather_backend.entity.City;
import com.weather.weather_backend.entity.StationStatus;
import com.weather.weather_backend.entity.WeatherStation;
import com.weather.weather_backend.mapper.WeatherStationMapper;
import com.weather.weather_backend.repository.CityRepository;
import com.weather.weather_backend.repository.WeatherStationRepository;
import com.weather.weather_backend.service.WeatherStationService;
import com.weather.weather_backend.specification.WeatherStationSpecification;
import com.weather.weather_backend.exception.DuplicateResourceException;
import com.weather.weather_backend.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WeatherStationServiceImpl implements WeatherStationService {

    private final WeatherStationRepository weatherStationRepository;
    private final CityRepository cityRepository;
    private final WeatherStationMapper weatherStationMapper;

    @Override
    @Transactional
    public WeatherStationResponse create(WeatherStationRequest request) {

        log.info("Creating weather station {}", request.getStationCode());

        if (weatherStationRepository.existsByStationCode(request.getStationCode())) {
            throw new DuplicateResourceException("Station code already exists.");
        }

        City city = cityRepository.findById(request.getCityId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "City not found with id: " + request.getCityId()));

        WeatherStation station = weatherStationMapper.toEntity(request);
        station.setCity(city);

        WeatherStation saved = weatherStationRepository.save(station);

        log.info("Weather station created successfully: {}", saved.getId());

        return weatherStationMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public WeatherStationResponse update(Long id,
                                         WeatherStationRequest request) {

        log.info("Updating weather station {}", id);

        WeatherStation station = weatherStationRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Weather station not found with id: " + id));

        if (weatherStationRepository.existsByStationCodeAndIdNot(
                request.getStationCode(), id)) {

            throw new DuplicateResourceException(
                    "Station code already exists.");
        }

        City city = cityRepository.findById(request.getCityId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "City not found with id: " + request.getCityId()));

        weatherStationMapper.updateEntity(request, station);
        station.setCity(city);

        WeatherStation updated = weatherStationRepository.save(station);

        log.info("Weather station updated {}", updated.getId());

        return weatherStationMapper.toResponse(updated);
    }

    @Override
    @Transactional
    public void delete(Long id) {

        log.info("Deleting weather station {}", id);

        WeatherStation station = weatherStationRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Weather station not found with id: " + id));

        weatherStationRepository.delete(station);

        log.info("Weather station deleted {}", id);
    }

    @Override
    public WeatherStationResponse findById(Long id) {

        WeatherStation station = weatherStationRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Weather station not found with id: " + id));

        return weatherStationMapper.toResponse(station);
    }

    @Override
    public Page<WeatherStationResponse> findAll(Pageable pageable) {

        return weatherStationRepository.findAll(pageable)
                .map(weatherStationMapper::toResponse);
    }

    @Override
    public Page<WeatherStationResponse> search(
            String keyword,
            Long cityId,
            StationStatus status,
            Pageable pageable) {

        Specification<WeatherStation> specification =
                WeatherStationSpecification.keyword(keyword)
                        .and(WeatherStationSpecification.cityId(cityId))
                        .and(WeatherStationSpecification.status(status));

        return weatherStationRepository.findAll(specification, pageable)
                .map(weatherStationMapper::toResponse);
    }
}