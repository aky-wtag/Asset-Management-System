package com.welldev.ams.service;

import java.time.ZonedDateTime;

import org.springframework.http.ResponseEntity;

import com.welldev.ams.model.request.AssetRequestDTO;
import com.welldev.ams.model.response.BaseResponse;

public interface AssetRequestService {
  ResponseEntity<BaseResponse> createAssetRequest(AssetRequestDTO assetRequestDTO);

  ResponseEntity<BaseResponse> updateAssetRequest(AssetRequestDTO assetRequestDTO, String assetRequestId);

  ResponseEntity<BaseResponse> getAssetRequests(String requestedBy, String assetName, String status, ZonedDateTime requestDateFrom, ZonedDateTime requestDateTo, int page, int size, String sortBy, String order);

  ResponseEntity<BaseResponse> getAssetRequest(String assetRequestId);

  ResponseEntity<BaseResponse> deleteAssetRequest(String assetRequestId);
}
