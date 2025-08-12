package com.welldev.ams.service.impl;

import java.util.Optional;
import java.util.UUID;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.welldev.ams.model.db.Location;
import com.welldev.ams.model.mapper.LocationMapper;
import com.welldev.ams.model.request.LocationDTO;
import com.welldev.ams.repositories.LocationRepository;
import com.welldev.ams.service.LocationService;

@Slf4j
@Service
@Transactional
public class LocationServiceImpl implements LocationService {
  private final LocationRepository locationRepository;
  private final LocationMapper locationMapper;

  public LocationServiceImpl(LocationRepository locationRepository, LocationMapper locationMapper) {
    this.locationRepository = locationRepository;
    this.locationMapper = locationMapper;
  }

  @Override
  public LocationDTO createLocation(LocationDTO locationDTO) {
    Location entity = locationMapper.locationDTOToLocationEntity(locationDTO);
    Location saved = locationRepository.save(entity);
    return locationMapper.toDto(saved);
  }

  @Override
  public LocationDTO updateLocation(LocationDTO locationDTO, String locationId) {
    Optional<Location> optional = locationRepository.findById(UUID.fromString(locationId));
    if (optional.isPresent()) {
      Location entity = optional.get();
      locationMapper.updateLocationEntity(locationDTO, entity);
      Location saved = locationRepository.save(entity);
      return locationMapper.toDto(saved);
    }
    throw new RuntimeException("Location not found");
  }

  @Override
  public Page<LocationDTO> getLocations(int page, int size, String sortBy, String order) {
    Sort sort = order.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
    Page<Location> locationPage = locationRepository.findAll(PageRequest.of(page, size, sort));
    return locationPage.map(locationMapper::toDto);
  }

  @Override
  public LocationDTO getLocation(String locationId) {
    Optional<Location> location = locationRepository.findById(UUID.fromString(locationId));
    if (location.isPresent()) {
      return locationMapper.toDto(location.get());
    }
    throw new RuntimeException("Location not found");
  }

  @Override
  public void deleteLocation(String locationId) {
    Optional<Location> location = locationRepository.findById(UUID.fromString(locationId));
    if (location.isPresent()) {
      location.get().setDeleted(true);
      locationRepository.save(location.get());
    } else {
      throw new RuntimeException("Location not found");
    }
  }
}
