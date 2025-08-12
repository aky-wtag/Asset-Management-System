package com.welldev.ams.controllers;

import java.time.ZonedDateTime;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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
import com.welldev.ams.model.response.BaseResponse;
import com.welldev.ams.service.AssetRequestService;
import com.welldev.ams.utils.Utils;

@RestController
@RequestMapping("/asset-requests")
public class AssetRequestController {
  private final AssetRequestService assetRequestService;
  private final Utils utils;

  public AssetRequestController(AssetRequestService assetRequestService, Utils utils) {
    this.assetRequestService = assetRequestService;
    this.utils = utils;
  }

  @PostMapping
  @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
  public ResponseEntity<BaseResponse> createAssetRequest(@Valid @RequestBody AssetRequestDTO assetRequestDTO) {
    AssetRequestDTO created = assetRequestService.createAssetRequest(assetRequestDTO);
    return ResponseEntity.status(HttpStatus.CREATED.value()).body(utils.generateResponse(created,true, HttpStatus.CREATED.value(), "Location Created Successfully"));
  }

  @GetMapping
  @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
  public ResponseEntity<BaseResponse> getAssetRequests(
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
    return ResponseEntity.status(HttpStatus.OK.value()).body(utils.generateResponse(requests,true, HttpStatus.OK.value(), ""));
  }

  @GetMapping("/{assetRequestId}")
  @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
  public ResponseEntity<BaseResponse> getAssetRequest(@PathVariable String assetRequestId) {
    AssetRequestDTO dto = assetRequestService.getAssetRequest(assetRequestId);
    return ResponseEntity.status(HttpStatus.OK.value()).body(utils.generateResponse(dto,true, HttpStatus.OK.value(), ""));
  }

  @PutMapping("/{assetRequestId}")
  @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
  public ResponseEntity<BaseResponse> updateAssetRequest(@Valid @RequestBody AssetRequestDTO assetRequestDTO, @PathVariable String assetRequestId) {
    AssetRequestDTO updated = assetRequestService.updateAssetRequest(assetRequestDTO, assetRequestId);
    return ResponseEntity.status(HttpStatus.OK.value()).body(utils.generateResponse(updated,true, HttpStatus.OK.value(), "Asset Request Updated Successfully"));
  }

  @DeleteMapping("/{assetRequestId}")
  @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
  public ResponseEntity<Void> deleteAssetRequest(@PathVariable String assetRequestId) {
    assetRequestService.deleteAssetRequest(assetRequestId);
    return ResponseEntity.noContent().build();
  }
}
