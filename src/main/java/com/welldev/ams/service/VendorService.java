package com.welldev.ams.service;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import com.welldev.ams.model.request.VendorDTO;
import com.welldev.ams.model.response.BaseResponse;

public interface VendorService {
  VendorDTO createVendor(VendorDTO vendorDTO);

  VendorDTO updateVendor(VendorDTO vendorDTO, String vendorId);

  Page<VendorDTO> getVendors(String name, String contactPerson, String email, String phone, int page, int size, String sortBy, String order);

  VendorDTO getVendor(String vendorId);

  void deleteVendor(String vendorId);
}
