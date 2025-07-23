package com.welldev.ams.service;

import org.springframework.http.ResponseEntity;

import com.welldev.ams.model.request.VendorDTO;
import com.welldev.ams.model.response.BaseResponse;

public interface VendorService {
  ResponseEntity<BaseResponse> createVendor(VendorDTO vendorDTO);

  ResponseEntity<BaseResponse> updateVendor(VendorDTO vendorDTO, String vendorId);

  ResponseEntity<BaseResponse> getVendors(String name, String contactPerson, String email, String phone, int page, int size, String sortBy, String order);

  ResponseEntity<BaseResponse> getVendor(String vendorId);

  ResponseEntity<BaseResponse> deleteVendor(String vendorId);
}
