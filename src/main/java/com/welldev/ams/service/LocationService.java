package com.welldev.ams.service;

import org.springframework.http.ResponseEntity;

import com.welldev.ams.model.request.LocationDTO;
import com.welldev.ams.model.response.BaseResponse;

public interface LocationService {
  ResponseEntity<BaseResponse> createLocation(LocationDTO locationDTO);

  ResponseEntity<BaseResponse> updateLocation(LocationDTO locationDTO, String locationId);

  ResponseEntity<BaseResponse> getLocations(String building, String floor, String roomNumber, String city, int page, int size, String sortBy, String order);

  ResponseEntity<BaseResponse> getLocation(String locationId);

  ResponseEntity<BaseResponse> deleteLocation(String locationId);
}
