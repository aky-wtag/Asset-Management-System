package com.welldev.ams.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.welldev.ams.model.db.Location;
import com.welldev.ams.model.request.LocationDTO;

@Mapper(componentModel = "spring")
public interface LocationMapper {
  Location locationDTOToLocationEntity(LocationDTO locationDTO);

  void updateLocationEntity(LocationDTO locationDTO, @MappingTarget Location location);

  LocationDTO toDto(Location location);
}
