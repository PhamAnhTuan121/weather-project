package com.weather.weather_backend.service.impl;

import com.weather.weather_backend.dto.region.RegionRequest;
import com.weather.weather_backend.dto.region.RegionResponse;
import com.weather.weather_backend.entity.Region;
import com.weather.weather_backend.exception.DuplicateResourceException;
import com.weather.weather_backend.exception.ResourceNotFoundException;
import com.weather.weather_backend.mapper.RegionMapper;
import com.weather.weather_backend.repository.RegionRepository;
import com.weather.weather_backend.service.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RegionServiceImpl implements RegionService {

    private final RegionRepository repository;
    private final RegionMapper mapper;

    @Override
    @Transactional
    public RegionResponse create(RegionRequest request) {

        if (repository.existsByName(request.getName())) {
            throw new DuplicateResourceException("Region already exists");
        }

        Region entity = mapper.toEntity(request);

        Region saved = repository.save(entity);

        return mapper.toResponse(saved);
    }

    @Override
    @Transactional
    public RegionResponse update(Long id, RegionRequest request) {

        Region region = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Region not found"));

        if (!region.getName().equals(request.getName())
                && repository.existsByName(request.getName())) {

            throw new DuplicateResourceException("Region already exists");
        }

        mapper.updateEntity(request, region);

        Region updated = repository.save(region);

        return mapper.toResponse(updated);
    }

    @Override
    @Transactional
    public void delete(Long id) {

        Region region = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Region not found"));

        repository.delete(region);
    }

    @Override
    public RegionResponse findById(Long id) {

        Region region = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Region not found"));

        return mapper.toResponse(region);
    }

    @Override
    public Page<RegionResponse> findAll(Pageable pageable) {

        return repository.findAll(pageable)
                .map(mapper::toResponse);
    }

    @Override
    public Page<RegionResponse> search(String keyword, Pageable pageable) {

        return repository.findByNameContainingIgnoreCase(keyword, pageable)
                .map(mapper::toResponse);
    }
}