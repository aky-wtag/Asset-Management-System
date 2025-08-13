package com.welldev.ams.service.impl;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.welldev.ams.model.db.AssetRequest;
import com.welldev.ams.model.db.Users;
import com.welldev.ams.model.mapper.AssetRequestMapper;
import com.welldev.ams.model.request.AssetRequestDTO;
import com.welldev.ams.repositories.AssetRequestRepository;
import com.welldev.ams.service.AssetRequestService;

@Slf4j
@Service
@Transactional
public class AssetRequestServiceImpl implements AssetRequestService {
  private final AssetRequestRepository assetRequestRepository;
  private final AssetRequestMapper assetRequestMapper;

  public AssetRequestServiceImpl(AssetRequestRepository assetRequestRepository, AssetRequestMapper assetRequestMapper) {
    this.assetRequestRepository = assetRequestRepository;
    this.assetRequestMapper = assetRequestMapper;
  }

  @Override
  public AssetRequestDTO createAssetRequest(AssetRequestDTO assetRequestDTO) {
    AssetRequest entity = assetRequestMapper.toEntity(assetRequestDTO);
    AssetRequest saved = assetRequestRepository.save(entity);
    return assetRequestMapper.toDto(saved);
  }

  @Override
  @PreAuthorize("@permissionCheck.isOwnerOrAdmin(#assetRequestId)")
  public AssetRequestDTO updateAssetRequest(AssetRequestDTO assetRequestDTO, String assetRequestId) {
    Optional<AssetRequest> optional = assetRequestRepository.findByIdAndActiveAndDeleted(UUID.fromString(assetRequestId), true, false);
    if (optional.isPresent()) {
      AssetRequest entity = optional.get();
      assetRequestMapper.updateEntity(assetRequestDTO, entity);
      AssetRequest saved = assetRequestRepository.save(entity);
      return assetRequestMapper.toDto(saved);
    }
    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Asset Request not found");
  }

  @Override
  public Page<AssetRequestDTO> getAssetRequests(String requestedBy, String assetName, String status,
      ZonedDateTime requestDateFrom, ZonedDateTime requestDateTo, int page, int size, String sortBy, String order) {
    Users currentUser = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Specification<AssetRequest> spec = (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<>();

      if (requestedBy != null && !requestedBy.isEmpty()) {
        predicates.add(cb.equal(root.get("requestedBy").get("id"), UUID.fromString(requestedBy)));
      }

      if (currentUser.getRoles().stream().anyMatch(role -> role.getName().equals("ROLE_USER"))) {
        predicates.add(cb.equal(root.get("requestedBy").get("id"), currentUser.getId()));
      }

      if (assetName != null && !assetName.isEmpty()) {
        predicates.add(cb.equal(root.get("assetName"), assetName));
      }

      if (requestDateFrom != null && requestDateTo != null) {
        predicates.add(cb.between(root.get("requestDate"), requestDateFrom, requestDateTo));
      }

      if (status != null && !status.isEmpty()) {
        predicates.add(cb.equal(root.get("status"), status));
      }

      return cb.and(predicates.toArray(new Predicate[0]));
    };
    Page<AssetRequest> assetPage = assetRequestRepository.findAll(spec, PageRequest.of(page, size));
    return assetPage.map(assetRequestMapper::toDto);
  }

  @Override
  public AssetRequestDTO getAssetRequest(String assetRequestId) {
    Optional<AssetRequest> asset = assetRequestRepository.findById(UUID.fromString(assetRequestId));
    if (asset.isPresent()) {
      return assetRequestMapper.toDto(asset.get());
    }
    throw new RuntimeException("Asset Request not found");
  }

  @Override
  public void deleteAssetRequest(String assetRequestId) {
    Optional<AssetRequest> asset = assetRequestRepository.findByIdAndActiveAndDeleted(UUID.fromString(assetRequestId), true, false);
    if (asset.isPresent()) {
      asset.get().setDeleted(true);
      assetRequestRepository.save(asset.get());
    } else {
      throw new RuntimeException("Asset Request not found");
    }
  }
}
