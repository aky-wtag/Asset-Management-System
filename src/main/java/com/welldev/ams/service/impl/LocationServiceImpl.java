package com.welldev.ams.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.welldev.ams.model.db.Location;
import com.welldev.ams.model.mapper.LocationMapper;
import com.welldev.ams.model.request.LocationDTO;
import com.welldev.ams.model.response.BaseResponse;
import com.welldev.ams.repositories.LocationRepository;
import com.welldev.ams.service.LocationService;
import com.welldev.ams.utils.Utils;

@Slf4j
@Service
public class LocationServiceImpl implements LocationService {
  private final LocationRepository locationRepository;
  private final LocationMapper locationMapper;
  private final Utils utils;

  public LocationServiceImpl(LocationRepository locationRepository, LocationMapper locationMapper, Utils utils) {
    this.locationRepository = locationRepository;
    this.locationMapper = locationMapper;
    this.utils = utils;
  }

  @Override
  public ResponseEntity<BaseResponse> createLocation(LocationDTO locationDTO) {
    try {
      Location saveEntity = locationRepository.save(locationMapper.locationDTOToLocationEntity(locationDTO));
      return ResponseEntity.ok().body(utils.generateResponse(saveEntity, true, HttpStatus.OK.value(), "Location Created Successfully."));
    }
    catch (Exception e) {
      log.error("Create Location Error: {}", e);
      return ResponseEntity.internalServerError().body(utils.generateResponse(null, false, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
    }
  }

  @Override
  public ResponseEntity<BaseResponse> updateLocation(LocationDTO locationDTO, String locationId) {
    try {
      Optional<Location> locationEntity = locationRepository.findByIdAndActiveAndDeleted(UUID.fromString(locationId), true, false);
      if(locationEntity.isPresent()) {
        locationMapper.updateLocationEntity(locationDTO, locationEntity.get());
        Location saveEntity = locationRepository.save(locationEntity.get());
        return ResponseEntity.ok().body(utils.generateResponse(saveEntity, true, HttpStatus.OK.value(), ""));
      }
      return ResponseEntity.notFound().build();
    }
    catch (Exception e) {
      log.error("Update Location Error: {}", e);
      return ResponseEntity.internalServerError().body(utils.generateResponse(null, false, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
    }
  }

  @Override
  public ResponseEntity<BaseResponse> getLocations(String building, String floor, String roomNumber, String city, int page, int size, String sortBy, String order) {
    try {
       Specification<Location> spec = (root, query, cb) -> {
          List<Predicate> predicates = new ArrayList<>();

          if (building != null && !building.isEmpty()) {
            predicates.add(cb.equal(root.get("building"), building));
          }
          if (floor != null && !floor.isEmpty()) {
            predicates.add(cb.equal(root.get("floor"), floor));
          }
          if (roomNumber != null && !roomNumber.isEmpty()) {
            predicates.add(cb.equal(root.get("roomNumber"), roomNumber));
          }
          if (city != null && !city.isEmpty()) {
            predicates.add(cb.equal(root.get("city"), city));
          }

          return cb.and(predicates.toArray(new Predicate[0]));
        };
      Page<Location> locationList = locationRepository.findAll(spec, PageRequest.of(page, size));
      return ResponseEntity.ok().body(utils.generateResponse(locationList, true, HttpStatus.OK.value(), ""));
    }
    catch (Exception e) {
      log.error("Get Categories Error: {}", e);
      return ResponseEntity.internalServerError().body(utils.generateResponse(null, false, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
    }
  }

  @Override
  public ResponseEntity<BaseResponse> getLocation(String locationId) {
    try {
      Optional<Location> location = locationRepository.findById(UUID.fromString(locationId));
      return location.map(value -> ResponseEntity.ok()
              .body(utils.generateResponse(value, true, HttpStatus.OK.value(), "")))
          .orElseGet(() -> ResponseEntity.notFound()
              .build());
    }
    catch (Exception e) {
      log.error("Get Location Error: {}", locationId, e);
      return ResponseEntity.internalServerError().body(utils.generateResponse(null, false, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
    }
  }

  @Override
  public ResponseEntity<BaseResponse> deleteLocation(String locationId) {
    try {
      Optional<Location> location = locationRepository.findByIdAndActiveAndDeleted(UUID.fromString(locationId), true, false);
      if(location.isPresent()) {
        location.get().setDeleted(true);
        locationRepository.save(location.get());
        return ResponseEntity.noContent().build();
      }
      else{
        return ResponseEntity.notFound().build();
      }
    }
    catch (Exception e) {
      log.error("Delete Location Error: {}", locationId, e);
      return ResponseEntity.internalServerError().body(utils.generateResponse(null, false, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
    }
  }
}
