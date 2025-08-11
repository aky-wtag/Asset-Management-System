package com.welldev.ams.service;

import java.time.ZonedDateTime;

import org.springframework.data.domain.Page;

import com.welldev.ams.model.request.AssetRequestDTO;

public interface AssetRequestService {
  AssetRequestDTO createAssetRequest(AssetRequestDTO assetRequestDTO);

  AssetRequestDTO updateAssetRequest(AssetRequestDTO assetRequestDTO, String assetRequestId);

  Page<AssetRequestDTO> getAssetRequests(String requestedBy, String assetName, String status, ZonedDateTime requestDateFrom, ZonedDateTime requestDateTo, int page, int size, String sortBy, String order);

  AssetRequestDTO getAssetRequest(String assetRequestId);

  void deleteAssetRequest(String assetRequestId);
}
