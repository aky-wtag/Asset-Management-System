package com.welldev.ams.service.impl;

import java.time.ZonedDateTime;
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

import com.welldev.ams.model.db.Asset;
import com.welldev.ams.model.mapper.AssetMapper;
import com.welldev.ams.model.request.AssetDTO;
import com.welldev.ams.model.response.BaseResponse;
import com.welldev.ams.repositories.AssetRepository;
import com.welldev.ams.repositories.CategoryRepository;
import com.welldev.ams.repositories.LocationRepository;
import com.welldev.ams.repositories.VendorRepository;
import com.welldev.ams.service.AssetService;
import com.welldev.ams.utils.Utils;

@Slf4j
@Service
public class AssetServiceImpl implements AssetService {
  private final AssetRepository assetRepository;
  private final CategoryRepository categoryRepository;
  private final VendorRepository vendorRepository;
  private final LocationRepository locationRepository;
  private final Utils utils;
  private final AssetMapper assetMapper;

  public AssetServiceImpl(AssetRepository assetRepository, Utils utils, AssetMapper assetMapper, CategoryRepository categoryRepository, VendorRepository vendorRepository, LocationRepository locationRepository) {
    this.assetRepository = assetRepository;
    this.utils = utils;
    this.assetMapper = assetMapper;
    this.categoryRepository = categoryRepository;
    this.vendorRepository = vendorRepository;
    this.locationRepository = locationRepository;
  }

  @Override
  public ResponseEntity<BaseResponse> createAsset(AssetDTO assetDTO) {
    try {
      Asset saveEntity = assetRepository.save(assetMapper.categoryDTOToCategoryEntity(assetDTO, categoryRepository, vendorRepository, locationRepository));
      return ResponseEntity.ok().body(utils.generateResponse(saveEntity, true, HttpStatus.OK.value(), "Asset Created Successfully."));
    }
    catch (Exception e) {
      log.error("Asset Location Error: {}", e);
      return ResponseEntity.internalServerError().body(utils.generateResponse(null, false, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
    }
  }

  @Override
  public ResponseEntity<BaseResponse> updateAsset(AssetDTO assetDTO, String assetId) {
    try {
      Optional<Asset> locationEntity = assetRepository.findByIdAndActiveAndDeleted(UUID.fromString(assetId), true, false);
      if(locationEntity.isPresent()) {
        assetMapper.updateCategoryEntity(assetDTO, locationEntity.get(), categoryRepository, vendorRepository, locationRepository);
        Asset saveEntity = assetRepository.save(locationEntity.get());
        return ResponseEntity.ok().body(utils.generateResponse(saveEntity, true, HttpStatus.OK.value(), ""));
      }
      return ResponseEntity.notFound().build();
    }
    catch (Exception e) {
      log.error("Asset Location Error: {}", e);
      return ResponseEntity.internalServerError().body(utils.generateResponse(null, false, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
    }
  }

  @Override
  public ResponseEntity<BaseResponse> getAssets(String serialNumber, String category, String vendor, String location, ZonedDateTime purchaseDateFrom, ZonedDateTime purchaseDateTo, String status, int page, int size, String sortBy, String order) {
    try {
      Specification<Asset> spec = (root, query, cb) -> {
        List<Predicate> predicates = new ArrayList<>();

        if (serialNumber != null && !serialNumber.isEmpty()) {
          predicates.add(cb.equal(root.get("serialNumber"), serialNumber));
        }
        if (category != null && !category.isEmpty()) {
          predicates.add(cb.equal(root.get("category"), category));
        }
        if (vendor != null && !vendor.isEmpty()) {
          predicates.add(cb.equal(root.get("vendor"), vendor));
        }
        if (location != null && !location.isEmpty()) {
          predicates.add(cb.equal(root.get("location"), location));
        }
        if (purchaseDateFrom != null && purchaseDateTo != null) {
          predicates.add(cb.between(root.get("purchaseDate"), purchaseDateFrom, purchaseDateTo));
        }
        if (status != null && !status.isEmpty()) {
          predicates.add(cb.equal(root.get("status"), status));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
      };
      Page<Asset> assetList = assetRepository.findAll(spec, PageRequest.of(page, size));
      return ResponseEntity.ok().body(utils.generateResponse(assetList, true, HttpStatus.OK.value(), ""));
    }
    catch (Exception e) {
      log.error("Get Categories Error: {}", e);
      return ResponseEntity.internalServerError().body(utils.generateResponse(null, false, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
    }
  }

  @Override
  public ResponseEntity<BaseResponse> getAsset(String assetId) {
    try {
      Optional<Asset> asset = assetRepository.findById(UUID.fromString(assetId));
      return asset.map(value -> ResponseEntity.ok()
              .body(utils.generateResponse(value, true, HttpStatus.OK.value(), "")))
          .orElseGet(() -> ResponseEntity.notFound()
              .build());
    }
    catch (Exception e) {
      log.error("Get Asset Error: {}", assetId, e);
      return ResponseEntity.internalServerError().body(utils.generateResponse(null, false, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
    }
  }

  @Override
  public ResponseEntity<BaseResponse> deleteAsset(String assetId) {
    try {
      Optional<Asset> asset = assetRepository.findByIdAndActiveAndDeleted(UUID.fromString(assetId), true, false);
      if(asset.isPresent()) {
        asset.get().setDeleted(true);
        assetRepository.save(asset.get());
        return ResponseEntity.noContent().build();
      }
      else{
        return ResponseEntity.notFound().build();
      }
    }
    catch (Exception e) {
      log.error("Delete Asset Error: {}", assetId, e);
      return ResponseEntity.internalServerError().body(utils.generateResponse(null, false, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
    }
  }
}
