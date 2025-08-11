package com.welldev.ams.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.welldev.ams.model.db.AssetRequest;
import com.welldev.ams.model.request.AssetRequestDTO;

@Mapper(componentModel = "spring", uses = {UserMapperHelper.class})
public interface AssetRequestMapper {
  @Mapping(target = "requestedBy", source = "requestedBy", qualifiedByName = "mapRequestedBy")
  @Mapping(target = "assetName", source = "assetId")
  AssetRequest toEntity(AssetRequestDTO assetRequestDTO);

  @Mapping(target = "requestedBy", source = "requestedBy", qualifiedByName = "mapRequestedBy")
  @Mapping(target = "assetName", source = "assetId")
  void updateEntity(AssetRequestDTO assetRequestDTO, @MappingTarget AssetRequest assetRequest);

  @Mapping(target = "requestedBy", source = "requestedBy.id")
  @Mapping(target = "assetId", source = "assetName")
  AssetRequestDTO toDto(AssetRequest assetRequest);
}
