package com.welldev.ams.controllers;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.welldev.ams.model.request.LocationDTO;
import com.welldev.ams.service.LocationService;

@RestController
@RequestMapping("/locations")
public class LocationController {
  private final LocationService locationService;

  public LocationController(LocationService locationService) {
    this.locationService = locationService;
  }

  @PostMapping
  @PreAuthorize("hasAnyRole('ADMIN')")
  public ResponseEntity<LocationDTO> createLocation(@Valid @RequestBody LocationDTO locationDTO) {
    LocationDTO created = locationService.createLocation(locationDTO);
    return ResponseEntity.status(201).body(created);
  }

  @GetMapping
  @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
  public ResponseEntity<Page<LocationDTO>> getLocations(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int pageSize,
      @RequestParam(defaultValue = "name") String sortBy,
      @RequestParam(defaultValue = "asc") String order) {
    Page<LocationDTO> locations = locationService.getLocations(page, pageSize, sortBy, order);
    return ResponseEntity.ok(locations);
  }

  @GetMapping("/{locationId}")
  @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
  public ResponseEntity<LocationDTO> getLocation(@PathVariable String locationId) {
    LocationDTO dto = locationService.getLocation(locationId);
    return ResponseEntity.ok(dto);
  }

  @PutMapping("/{locationId}")
  @PreAuthorize("hasAnyRole('ADMIN')")
  public ResponseEntity<LocationDTO> updateLocation(@Valid @RequestBody LocationDTO locationDTO, @PathVariable String locationId) {
    LocationDTO updated = locationService.updateLocation(locationDTO, locationId);
    return ResponseEntity.ok(updated);
  }

  @DeleteMapping("/{locationId}")
  @PreAuthorize("hasAnyRole('ADMIN')")
  public ResponseEntity<Void> deleteLocation(@PathVariable String locationId) {
    locationService.deleteLocation(locationId);
    return ResponseEntity.noContent().build();
  }
}
