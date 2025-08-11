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
  public AssetDTO createAsset(AssetDTO assetDTO) {
    Asset entity = assetMapper.categoryDTOToCategoryEntity(
        assetDTO, categoryRepository, vendorRepository, locationRepository);
    Asset savedEntity = assetRepository.save(entity);
    return assetMapper.entityToDTO(savedEntity);
  }

  @Override
  public Optional<AssetDTO>  updateAsset(AssetDTO assetDTO, String assetId) {
    return assetRepository.findByIdAndDeleted(UUID.fromString(assetId), false)
        .map(asset -> {
          assetMapper.updateEntityFromDTO(assetDTO, asset, categoryRepository, vendorRepository, locationRepository);
          Asset saved = assetRepository.save(asset);
          return assetMapper.entityToDTO(saved);
        });
  }

  @Override
  public Page<AssetDTO>  getAssets(String serialNumber, String category, String vendor, String location, ZonedDateTime purchaseDateFrom, ZonedDateTime purchaseDateTo, String status, int page, int size, String sortBy, String order) {
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
      return assetList.map(assetMapper::entityToDTO);
  }

  @Override
  public Optional<AssetDTO>  getAsset(String assetId) {
    try {
      return assetRepository.findById(UUID.fromString(assetId))
          .map(assetMapper::entityToDTO);
    } catch (Exception e) {
      log.error("Get Asset Error: {}", assetId, e);
      throw e;
    }
  }

  @Override
  public boolean deleteAsset(String assetId) {
    Optional<Asset> asset = assetRepository.findByIdAndActiveAndDeleted(UUID.fromString(assetId), true, false);
    if (asset.isPresent()) {
      Asset toDelete = asset.get();
      toDelete.setDeleted(true);
      assetRepository.save(toDelete);
      return true;
    }
    return false;
  }
}
