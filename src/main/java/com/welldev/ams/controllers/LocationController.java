package com.welldev.ams.controllers;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.welldev.ams.model.request.LocationDTO;
import com.welldev.ams.model.response.BaseResponse;
import com.welldev.ams.service.LocationService;
import com.welldev.ams.utils.Utils;

@RestController
@RequestMapping("/locations")
public class LocationController {
  private final LocationService locationService;
  private final Utils utils;

  public LocationController(LocationService locationService,Utils utils) {
    this.locationService = locationService;
    this.utils = utils;
  }

  @PostMapping
  @PreAuthorize("hasAnyRole('ADMIN')")
  public ResponseEntity<BaseResponse> createLocation(@Valid @RequestBody LocationDTO locationDTO) {
    LocationDTO created = locationService.createLocation(locationDTO);
    return ResponseEntity.status(HttpStatus.CREATED.value()).body(utils.generateResponse(created,true, HttpStatus.CREATED.value(), "Location Created Successfully"));
  }

  @GetMapping
  @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
  public ResponseEntity<BaseResponse> getLocations(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int pageSize,
      @RequestParam(defaultValue = "name") String sortBy,
      @RequestParam(defaultValue = "asc") String order) {
    Page<LocationDTO> locations = locationService.getLocations(page, pageSize, sortBy, order);
    return ResponseEntity.ok(utils.generateResponse(locations,true, HttpStatus.OK.value(), ""));
  }

  @GetMapping("/{locationId}")
  @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
  public ResponseEntity<BaseResponse> getLocation(@PathVariable String locationId) {
    LocationDTO dto = locationService.getLocation(locationId);
    return ResponseEntity.ok(utils.generateResponse(dto,true, HttpStatus.OK.value(), ""));
  }

  @PutMapping("/{locationId}")
  @PreAuthorize("hasAnyRole('ADMIN')")
  public ResponseEntity<BaseResponse> updateLocation(@Valid @RequestBody LocationDTO locationDTO, @PathVariable String locationId) {
    LocationDTO updated = locationService.updateLocation(locationDTO, locationId);
    return ResponseEntity.ok(utils.generateResponse(updated,true, HttpStatus.OK.value(), "Location Updated Successfully"));
  }

  @DeleteMapping("/{locationId}")
  @PreAuthorize("hasAnyRole('ADMIN')")
  public ResponseEntity<Void> deleteLocation(@PathVariable String locationId) {
    locationService.deleteLocation(locationId);
    return ResponseEntity.noContent().build();
  }
}
