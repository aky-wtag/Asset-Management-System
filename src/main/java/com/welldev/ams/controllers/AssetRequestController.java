package com.welldev.ams.controllers;

import java.time.ZonedDateTime;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.welldev.ams.model.request.AssetRequestDTO;
import com.welldev.ams.model.response.BaseResponse;
import com.welldev.ams.service.AssetRequestService;

@RestController
@RequestMapping("/asset-request")
public class AssetRequestController {
  private final AssetRequestService assetRequestService;

  public AssetRequestController(AssetRequestService assetRequestService) {
    this.assetRequestService = assetRequestService;
  }

  @PostMapping("/create-request")
  ResponseEntity<BaseResponse> createAssetRequest(@Valid @RequestBody AssetRequestDTO assetRequestDTO) {
    return assetRequestService.createAssetRequest(assetRequestDTO);
  }

  @GetMapping("/get-requests")
  ResponseEntity<BaseResponse> getAssetRequests(
      @RequestParam(required = false) String requestedBy,
      @RequestParam(required = false) String assetName,
      @RequestParam(required = false) String status,
      @RequestParam(required = false) ZonedDateTime requestDateFrom,
      @RequestParam(required = false) ZonedDateTime requestDateTo,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int pageSize,
      @RequestParam(defaultValue = "createdAt") String sortBy,
      @RequestParam(defaultValue = "desc") String order) {
    return assetRequestService.getAssetRequests(requestedBy, assetName, status, requestDateFrom, requestDateTo, page, pageSize, sortBy, order);
  }

  @GetMapping("/get-request")
  ResponseEntity<BaseResponse> getAssetRequest(@RequestParam String assetRequestId) {
    return assetRequestService.getAssetRequest(assetRequestId);
  }

  @PutMapping("/update-request")
  ResponseEntity<BaseResponse> updateAssetRequest(@Valid @RequestBody AssetRequestDTO assetRequestDTO, @RequestParam String assetRequestId) {
    return assetRequestService.updateAssetRequest(assetRequestDTO,assetRequestId);
  }

  @DeleteMapping("/delete-request")
  ResponseEntity<BaseResponse> deleteAssetRequest(@RequestParam String assetRequestId) {
    return assetRequestService.deleteAssetRequest(assetRequestId);
  }
}
