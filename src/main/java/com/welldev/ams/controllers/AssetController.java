package com.welldev.ams.controllers;

import java.time.ZonedDateTime;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.welldev.ams.model.request.AssetDTO;
import com.welldev.ams.model.response.BaseResponse;
import com.welldev.ams.service.AssetService;

@RestController
@RequestMapping("/asset")
public class AssetController {
  private final AssetService assetService;

  public AssetController(AssetService assetService) {
    this.assetService = assetService;
  }

  @PostMapping("/create-asset")
//  @PreAuthorize("hasAnyRole('ADMIN')")
  ResponseEntity<BaseResponse> createAsset(@Valid @RequestBody AssetDTO assetDTO) {
    return assetService.createAsset(assetDTO);
  }

  @GetMapping("/get-assets")
//  @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
  ResponseEntity<BaseResponse> getAssetRequests(
      @RequestParam(required = false) String serialNumber,
      @RequestParam(required = false) String category,
      @RequestParam(required = false) String vendor,
      @RequestParam(required = false) String location,
      @RequestParam(required = false) ZonedDateTime purchaseDateFrom,
      @RequestParam(required = false) ZonedDateTime purchaseDateTo,
      @RequestParam(required = false) String status,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int pageSize,
      @RequestParam(defaultValue = "createdAt") String sortBy,
      @RequestParam(defaultValue = "desc") String order
  ) {
    return assetService.getAssets(serialNumber, category, vendor, location, purchaseDateFrom, purchaseDateTo, status, page, pageSize, sortBy, order);
  }

  @GetMapping("/get-asset")
  ResponseEntity<BaseResponse> getAssetRequest(@RequestParam String assetRequestId) {
    return assetService.getAsset(assetRequestId);
  }

  @PutMapping("/update-asset")
//  @PreAuthorize("hasAnyRole('ADMIN')")
  ResponseEntity<BaseResponse> updateAssetRequest(@Valid @RequestBody AssetDTO assetDTO, @RequestParam String assetRequestId) {
    return assetService.updateAsset(assetDTO,assetRequestId);
  }

  @DeleteMapping("/delete-asset")
//  @PreAuthorize("hasAnyRole('ADMIN')")
  ResponseEntity<BaseResponse> deleteAssetRequest(@RequestParam String assetRequestId) {
    return assetService.deleteAsset(assetRequestId);
  }

}
