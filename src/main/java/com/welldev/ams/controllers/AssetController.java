package com.welldev.ams.controllers;

import java.time.ZonedDateTime;
import java.util.Optional;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
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

import com.welldev.ams.model.request.AssetDTO;
import com.welldev.ams.model.response.BaseResponse;
import com.welldev.ams.service.AssetService;
import com.welldev.ams.utils.Utils;

@Slf4j
@RestController
@RequestMapping("/assets")
public class AssetController {
  private final AssetService assetService;
  private final Utils utils;

  public AssetController(AssetService assetService, Utils utils) {
    this.assetService = assetService;
    this.utils = utils;
  }

  @PostMapping
  @PreAuthorize("hasAnyRole('ADMIN')")
  ResponseEntity<BaseResponse> createAsset(@Valid @RequestBody AssetDTO assetDTO) {
    try {
      AssetDTO savedAsset = assetService.createAsset(assetDTO);
      return ResponseEntity.ok(
          utils.generateResponse(savedAsset, true, HttpStatus.OK.value(), "Asset Created Successfully.")
      );
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body(
          utils.generateResponse(null, false, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage())
      );
    }
  }

  @GetMapping
  @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
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
    try {
      Page<AssetDTO> result = assetService.getAssets(
          serialNumber, category, vendor, location,
          purchaseDateFrom, purchaseDateTo, status,
          page, pageSize, sortBy, order
      );
      return ResponseEntity.ok(utils.generateResponse(result, true, HttpStatus.OK.value(), ""));
    } catch (Exception e) {
      return ResponseEntity.internalServerError()
          .body(utils.generateResponse(null, false, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
    }
  }

  @GetMapping("/{assetId}")
  @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
  ResponseEntity<BaseResponse> getAsset(@PathVariable String assetId) {
    try {
      Optional<AssetDTO> assetDTO = assetService.getAsset(assetId);
      return assetDTO.map(dto -> ResponseEntity.ok(
              utils.generateResponse(dto, true, HttpStatus.OK.value(), "")
          ))
          .orElseGet(() -> ResponseEntity.notFound().build());
    } catch (Exception e) {
      log.error("Get Asset Error: {}", assetId, e);
      return ResponseEntity.internalServerError().body(
          utils.generateResponse(null, false, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage())
      );
    }
  }

  @PutMapping("/{assetId}")
  @PreAuthorize("hasAnyRole('ADMIN')")
  ResponseEntity<BaseResponse> updateAssetRequest(@Valid @RequestBody AssetDTO assetDTO, @PathVariable String assetId) {
    try {
      return assetService.updateAsset(assetDTO, assetId)
          .map(updated -> ResponseEntity.ok(
              utils.generateResponse(updated, true, HttpStatus.OK.value(), "Asset updated successfully.")
          ))
          .orElse(ResponseEntity.notFound().build());
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body(
          utils.generateResponse(null, false, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage())
      );
    }
  }

  @DeleteMapping("/{assetId}")
  @PreAuthorize("hasAnyRole('ADMIN')")
  ResponseEntity<BaseResponse> deleteAsset(@PathVariable String assetId) {
    try {
      boolean deleted = assetService.deleteAsset(assetId);
      if (deleted) {
        return ResponseEntity.noContent().build();
      } else {
        return ResponseEntity.notFound().build();
      }
    } catch (Exception e) {
      log.error("Delete Asset Error: {}", assetId, e);
      return ResponseEntity.internalServerError().body(
          utils.generateResponse(null, false, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage())
      );
    }
  }

}
