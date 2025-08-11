package com.welldev.ams.service;

import java.time.ZonedDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import com.welldev.ams.model.request.AssetDTO;
import com.welldev.ams.model.response.BaseResponse;

public interface AssetService {
  AssetDTO createAsset(AssetDTO assetDTO);

  Optional<AssetDTO> updateAsset(AssetDTO assetDTO, String assetId);

  Page<AssetDTO> getAssets(String serialNumber, String category, String vendor, String location, ZonedDateTime purchaseDateFrom, ZonedDateTime purchaseDateTo, String status, int page, int size, String sortBy, String order);

  Optional<AssetDTO>  getAsset(String assetId);

  boolean deleteAsset(String assetId);
}
