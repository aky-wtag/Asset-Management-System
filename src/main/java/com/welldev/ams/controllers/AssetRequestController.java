package com.welldev.ams.controllers;

import java.time.ZonedDateTime;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.welldev.ams.model.request.AssetRequestDTO;
import com.welldev.ams.service.AssetRequestService;

@RestController
@RequestMapping("/asset-requests")
public class AssetRequestController {
  private final AssetRequestService assetRequestService;

  public AssetRequestController(AssetRequestService assetRequestService) {
    this.assetRequestService = assetRequestService;
  }

  @PostMapping
  @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
  public ResponseEntity<AssetRequestDTO> createAssetRequest(@Valid @RequestBody AssetRequestDTO assetRequestDTO) {
    AssetRequestDTO created = assetRequestService.createAssetRequest(assetRequestDTO);
    return ResponseEntity.status(201).body(created);
  }

  @GetMapping
  @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
  public ResponseEntity<Page<AssetRequestDTO>> getAssetRequests(
      @RequestParam(required = false) String requestedBy,
      @RequestParam(required = false) String assetName,
      @RequestParam(required = false) String status,
      @RequestParam(required = false) ZonedDateTime requestDateFrom,
      @RequestParam(required = false) ZonedDateTime requestDateTo,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int pageSize,
      @RequestParam(defaultValue = "createdAt") String sortBy,
      @RequestParam(defaultValue = "desc") String order) {
    Page<AssetRequestDTO> requests = assetRequestService.getAssetRequests(requestedBy, assetName, status, requestDateFrom, requestDateTo, page, pageSize, sortBy, order);
    return ResponseEntity.ok(requests);
  }

  @GetMapping("/{assetRequestId}")
  @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
  public ResponseEntity<AssetRequestDTO> getAssetRequest(@PathVariable String assetRequestId) {
    AssetRequestDTO dto = assetRequestService.getAssetRequest(assetRequestId);
    return ResponseEntity.ok(dto);
  }

  @PutMapping("/{assetRequestId}")
  @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
  public ResponseEntity<AssetRequestDTO> updateAssetRequest(@Valid @RequestBody AssetRequestDTO assetRequestDTO, @PathVariable String assetRequestId) {
    AssetRequestDTO updated = assetRequestService.updateAssetRequest(assetRequestDTO, assetRequestId);
    return ResponseEntity.ok(updated);
  }

  @DeleteMapping("/{assetRequestId}")
  @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
  public ResponseEntity<Void> deleteAssetRequest(@PathVariable String assetRequestId) {
    assetRequestService.deleteAssetRequest(assetRequestId);
    return ResponseEntity.noContent().build();
  }
}
