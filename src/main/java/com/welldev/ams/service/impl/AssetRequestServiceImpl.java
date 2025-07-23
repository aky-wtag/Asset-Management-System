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

import com.welldev.ams.model.db.AssetRequest;
import com.welldev.ams.model.mapper.AssetRequestMapper;
import com.welldev.ams.model.request.AssetRequestDTO;
import com.welldev.ams.model.response.BaseResponse;
import com.welldev.ams.repositories.AssetRequestRepository;
import com.welldev.ams.service.AssetRequestService;
import com.welldev.ams.utils.Utils;

@Slf4j
@Service
public class AssetRequestServiceImpl implements AssetRequestService {
  private final AssetRequestRepository assetRequestRepository;
  private final Utils utils;
  private final AssetRequestMapper assetRequestMapper;

  public AssetRequestServiceImpl(AssetRequestRepository assetRequestRepository, Utils utils, AssetRequestMapper assetRequestMapper)
  {
    this.assetRequestRepository = assetRequestRepository;
    this.utils = utils;
    this.assetRequestMapper = assetRequestMapper;
  }

  @Override
  public ResponseEntity<BaseResponse> createAssetRequest(AssetRequestDTO assetRequestDTO) {
    try {
      AssetRequest saveEntity = assetRequestRepository.save(assetRequestMapper.toEntity(assetRequestDTO));
      return ResponseEntity.ok().body(utils.generateResponse(saveEntity, true, HttpStatus.OK.value(), "Asset Request Created Successfully."));
    }
    catch (Exception e) {
      log.error("Create AssetRequest Error: {}", e);
      return ResponseEntity.internalServerError().body(utils.generateResponse(null, false, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
    }
  }

  @Override
  public ResponseEntity<BaseResponse> updateAssetRequest(AssetRequestDTO assetRequestDTO, String assetRequestId) {
    try {
      Optional<AssetRequest> locationEntity = assetRequestRepository.findByIdAndActiveAndDeleted(UUID.fromString(assetRequestId), true, false);
      if(locationEntity.isPresent()) {
        assetRequestMapper.updateEntity(assetRequestDTO, locationEntity.get());
        AssetRequest saveEntity = assetRequestRepository.save(locationEntity.get());
        return ResponseEntity.ok().body(utils.generateResponse(saveEntity, true, HttpStatus.OK.value(), ""));
      }
      return ResponseEntity.notFound().build();
    }
    catch (Exception e) {
      log.error("Update Asset Request Error: {}", e);
      return ResponseEntity.internalServerError().body(utils.generateResponse(null, false, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
    }
  }

  @Override
  public ResponseEntity<BaseResponse> getAssetRequests(String requestedBy, String assetName, String status,
      ZonedDateTime requestDateFrom, ZonedDateTime requestDateTo, int page, int size, String sortBy, String order)
  {
    try {
      Specification<AssetRequest> spec = (root, query, cb) -> {
        List<Predicate> predicates = new ArrayList<>();

        if (requestedBy != null && !requestedBy.isEmpty()) {
          predicates.add(cb.equal(root.get("requestedBy"), requestedBy));
        }
        if (assetName != null && !assetName.isEmpty()) {
          predicates.add(cb.equal(root.get("assetName"), assetName));
        }
        if (requestDateFrom != null && requestDateTo != null) {
          predicates.add(cb.between(root.get("purchaseDate"), requestDateFrom, requestDateTo));
        }
        if (status != null && !status.isEmpty()) {
          predicates.add(cb.equal(root.get("status"), status));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
      };
      Page<AssetRequest> assetList = assetRequestRepository.findAll(spec, PageRequest.of(page, size));
      return ResponseEntity.ok().body(utils.generateResponse(assetList, true, HttpStatus.OK.value(), ""));
    }
    catch (Exception e) {
      log.error("Get AssetRequests Error: {}", e);
      return ResponseEntity.internalServerError().body(utils.generateResponse(null, false, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
    }
  }

  @Override
  public ResponseEntity<BaseResponse> getAssetRequest(String assetRequestId) {
    try {
      Optional<AssetRequest> asset = assetRequestRepository.findById(UUID.fromString(assetRequestId));
      return asset.map(value -> ResponseEntity.ok()
              .body(utils.generateResponse(value, true, HttpStatus.OK.value(), "")))
          .orElseGet(() -> ResponseEntity.notFound()
              .build());
    }
    catch (Exception e) {
      log.error("Get AssetRequest Error: {}", assetRequestId, e);
      return ResponseEntity.internalServerError().body(utils.generateResponse(null, false, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
    }
  }

  @Override
  public ResponseEntity<BaseResponse> deleteAssetRequest(String assetRequestId) {
    try {
      Optional<AssetRequest> asset = assetRequestRepository.findByIdAndActiveAndDeleted(UUID.fromString(assetRequestId), true, false);
      if(asset.isPresent()) {
        asset.get().setDeleted(true);
        assetRequestRepository.save(asset.get());
        return ResponseEntity.noContent().build();
      }
      else{
        return ResponseEntity.notFound().build();
      }
    }
    catch (Exception e) {
      log.error("Delete AssetRequest Error: {}", assetRequestId, e);
      return ResponseEntity.internalServerError().body(utils.generateResponse(null, false, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
    }
  }
}
