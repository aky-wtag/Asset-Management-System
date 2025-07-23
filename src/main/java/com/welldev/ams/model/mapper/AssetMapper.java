package com.welldev.ams.model.mapper;

import java.util.UUID;

import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.welldev.ams.model.db.Asset;
import com.welldev.ams.model.db.Category;
import com.welldev.ams.model.db.Location;
import com.welldev.ams.model.db.Vendor;
import com.welldev.ams.model.request.AssetDTO;
import com.welldev.ams.repositories.CategoryRepository;
import com.welldev.ams.repositories.LocationRepository;
import com.welldev.ams.repositories.VendorRepository;

@Mapper(componentModel = "spring")
public interface AssetMapper {

  @Mapping(target = "category", ignore = true)
  @Mapping(target = "vendor", ignore = true)
  @Mapping(target = "location", ignore = true)
  Asset categoryDTOToCategoryEntity(
      AssetDTO assetDTO,
      @Context CategoryRepository categoryRepository,
      @Context VendorRepository vendorRepository,
      @Context LocationRepository locationRepository
  );
  @Mapping(target = "category", ignore = true)
  @Mapping(target = "vendor", ignore = true)
  @Mapping(target = "location", ignore = true)
  void updateCategoryEntity(
      AssetDTO assetDTO,
      @MappingTarget Asset asset,
      @Context CategoryRepository categoryRepository,
      @Context VendorRepository vendorRepository,
      @Context LocationRepository locationRepository
  );

  @BeforeMapping
  default void updateEntity(AssetDTO assetDTO, @MappingTarget Asset asset,
      @Context CategoryRepository categoryRepository,
      @Context VendorRepository vendorRepository,
      @Context LocationRepository locationRepository) {

    // Fetch and set Category
    Category category = categoryRepository.findByIdAndActiveAndDeleted(UUID.fromString(assetDTO.getCategory()), true, false)
        .orElseThrow(() -> new IllegalArgumentException("Category not found: " + assetDTO.getCategory()));
    asset.setCategory(category);

    // Fetch and set Vendor
    Vendor vendor = vendorRepository.findByIdAndActiveAndDeleted(UUID.fromString(assetDTO.getVendor()), true, false)
        .orElseThrow(() -> new IllegalArgumentException("Vendor not found: " + assetDTO.getVendor()));
    asset.setVendor(vendor);

    // Fetch and set Location
    Location location = locationRepository.findByIdAndActiveAndDeleted(UUID.fromString(assetDTO.getLocation()), true, false)
        .orElseThrow(() -> new IllegalArgumentException("Location not found: " + assetDTO.getLocation()));
    asset.setLocation(location);
  }
}
