package com.welldev.ams.service;

import org.springframework.data.domain.Page;
import com.welldev.ams.model.request.LocationDTO;

public interface LocationService {
  LocationDTO createLocation(LocationDTO locationDTO);

  LocationDTO updateLocation(LocationDTO locationDTO, String locationId);

  Page<LocationDTO> getLocations(int page, int size, String sortBy, String order);

  LocationDTO getLocation(String locationId);

  void deleteLocation(String locationId);
}
