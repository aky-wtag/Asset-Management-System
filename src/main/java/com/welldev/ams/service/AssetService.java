package com.welldev.ams.service;

import java.time.ZonedDateTime;

import org.springframework.http.ResponseEntity;

import com.welldev.ams.model.request.AssetDTO;
import com.welldev.ams.model.response.BaseResponse;

public interface AssetService {
  ResponseEntity<BaseResponse> createAsset(AssetDTO assetDTO);

  ResponseEntity<BaseResponse> updateAsset(AssetDTO assetDTO, String assetId);

  ResponseEntity<BaseResponse> getAssets(String serialNumber, String category, String vendor, String location, ZonedDateTime purchaseDateFrom, ZonedDateTime purchaseDateTo, String status, int page, int size, String sortBy, String order);

  ResponseEntity<BaseResponse> getAsset(String assetId);

  ResponseEntity<BaseResponse> deleteAsset(String assetId);
}
