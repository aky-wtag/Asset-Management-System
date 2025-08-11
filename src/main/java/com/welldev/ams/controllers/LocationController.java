package com.welldev.ams.controllers;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.welldev.ams.model.request.LocationDTO;
import com.welldev.ams.model.response.BaseResponse;
import com.welldev.ams.service.LocationService;

@RestController
@RequestMapping("/location")
public class LocationController {
  private final LocationService locationService;

  public LocationController(LocationService locationService) {
    this.locationService = locationService;
  }

  @PostMapping
  @PreAuthorize("hasAnyRole('ADMIN')")
  ResponseEntity<BaseResponse> createLocation(@Valid @RequestBody LocationDTO locationDTO) {
    return locationService.createLocation(locationDTO);
  }

  @GetMapping
  @PreAuthorize("hasAnyRole('ADMIN')")
  ResponseEntity<BaseResponse> getCategories(
      @RequestParam(required = false) String building,
      @RequestParam(required = false) String floor,
      @RequestParam(required = false) String roomNumber,
      @RequestParam(required = false) String city,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int pageSize,
      @RequestParam(defaultValue = "createdAt") String sortBy,
      @RequestParam(defaultValue = "desc") String order) {
    return locationService.getLocations(building, floor, roomNumber, city, page, pageSize, sortBy, order);
  }

  @GetMapping("/{locationId}")
  @PreAuthorize("hasAnyRole('ADMIN')")
  ResponseEntity<BaseResponse> getCategory(@PathVariable String locationId) {
    return locationService.getLocation(locationId);
  }

  @PutMapping("/{locationId}")
  @PreAuthorize("hasAnyRole('ADMIN')")
  ResponseEntity<BaseResponse> updateCategory(@Valid @RequestBody LocationDTO locationDTO, @PathVariable String locationId) {
    return locationService.updateLocation(locationDTO,locationId);
  }

  @DeleteMapping("/{locationId}")
  @PreAuthorize("hasAnyRole('ADMIN')")
  ResponseEntity<BaseResponse> deleteCategory(@PathVariable String locationId) {
    return locationService.deleteLocation(locationId);
  }
}
