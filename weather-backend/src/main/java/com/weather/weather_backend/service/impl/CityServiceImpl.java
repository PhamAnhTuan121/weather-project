package com.weather.weather_backend.service.impl;

import com.weather.weather_backend.dto.city.CityRequest;
import com.weather.weather_backend.dto.city.CityResponse;
import com.weather.weather_backend.entity.City;
import com.weather.weather_backend.entity.Region;
import com.weather.weather_backend.exception.DuplicateResourceException;
import com.weather.weather_backend.exception.ResourceNotFoundException;
import com.weather.weather_backend.mapper.CityMapper;
import com.weather.weather_backend.repository.CityRepository;
import com.weather.weather_backend.repository.RegionRepository;
import com.weather.weather_backend.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;
    private final RegionRepository regionRepository;
    private final CityMapper cityMapper;

    @Override
    @Transactional
    public CityResponse create(CityRequest request) {

        if (cityRepository.existsByNameAndRegionId(
                request.getName(),
                request.getRegionId())) {
            throw new DuplicateResourceException("City already exists in this region.");
        }

        Region region = regionRepository.findById(request.getRegionId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Region not found with id: "
                                + request.getRegionId()));

        City city = City.builder()
                .name(request.getName())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .region(region)
                .build();

        return cityMapper.toResponse(cityRepository.save(city));
    }

    @Override
    @Transactional
    public CityResponse update(Long id, CityRequest request) {

        City city = cityRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("City not found with id: " + id));

        Region region = regionRepository.findById(request.getRegionId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Region not found with id: "
                                + request.getRegionId()));

        if (!city.getName().equalsIgnoreCase(request.getName())
                || !city.getRegion().getId().equals(request.getRegionId())) {

            if (cityRepository.existsByNameAndRegionId(
                    request.getName(),
                    request.getRegionId())) {
                throw new DuplicateResourceException(
                        "City already exists in this region.");
            }
        }

        city.setName(request.getName());
        city.setLatitude(request.getLatitude());
        city.setLongitude(request.getLongitude());
        city.setRegion(region);

        return cityMapper.toResponse(cityRepository.save(city));
    }

    @Override
    @Transactional
    public void delete(Long id) {

        City city = cityRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("City not found with id: " + id));

        cityRepository.delete(city);
    }

    @Override
    public CityResponse findById(Long id) {

        City city = cityRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("City not found with id: " + id));

        return cityMapper.toResponse(city);
    }

    @Override
    public Page<CityResponse> findAll(Pageable pageable) {
        return cityRepository.findAll(pageable)
                .map(cityMapper::toResponse);
    }

    @Override
    public Page<CityResponse> search(String keyword, Pageable pageable) {
        return cityRepository
                .findByNameContainingIgnoreCase(keyword, pageable)
                .map(cityMapper::toResponse);
    }

    @Override
    public Page<CityResponse> findByRegion(Long regionId,
                                           Pageable pageable) {

        if (!regionRepository.existsById(regionId)) {
            throw new ResourceNotFoundException(
                    "Region not found with id: " + regionId);
        }

        return cityRepository.findByRegionId(regionId, pageable)
                .map(cityMapper::toResponse);
    }
}