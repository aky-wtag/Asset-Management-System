package com.welldev.ams.controllers;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

  @PostMapping("/create-location")
  ResponseEntity<BaseResponse> createLocation(@Valid @RequestBody LocationDTO locationDTO) {
    return locationService.createLocation(locationDTO);
  }

  @GetMapping("/get-locations")
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

  @GetMapping("/get-location")
  ResponseEntity<BaseResponse> getCategory(@RequestParam String locationId) {
    return locationService.getLocation(locationId);
  }

  @PutMapping("/update-location")
  ResponseEntity<BaseResponse> updateCategory(@Valid @RequestBody LocationDTO locationDTO, @RequestParam String locationId) {
    return locationService.updateLocation(locationDTO,locationId);
  }

  @DeleteMapping("/delete-location")
  ResponseEntity<BaseResponse> deleteCategory(@RequestParam String locationId) {
    return locationService.deleteLocation(locationId);
  }
}
