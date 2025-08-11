package com.welldev.ams.controllers;

import jakarta.validation.Valid;

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

import com.welldev.ams.model.request.VendorDTO;
import com.welldev.ams.model.response.BaseResponse;
import com.welldev.ams.service.VendorService;

@RestController
@RequestMapping("/vendor")
public class VendorController {
  private final VendorService vendorService;

  public VendorController(VendorService vendorService) {
    this.vendorService = vendorService;
  }

  @PostMapping
  @PreAuthorize("hasAnyRole('ADMIN')")
  ResponseEntity<BaseResponse> createVendor(@Valid @RequestBody VendorDTO vendorDTO) {
    return vendorService.createVendor(vendorDTO);
  }

  @GetMapping
  @PreAuthorize("hasAnyRole('ADMIN')")
  ResponseEntity<BaseResponse> getRoles(
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String contactPerson,
      @RequestParam(required = false) String email,
      @RequestParam(required = false) String phone,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int pageSize,
      @RequestParam(defaultValue = "createdAt") String sortBy,
      @RequestParam(defaultValue = "desc") String order) {
    return vendorService.getVendors(name, contactPerson, email, phone, page, pageSize, sortBy, order);
  }

  @GetMapping("/{vendorId}")
  @PreAuthorize("hasAnyRole('ADMIN')")
  ResponseEntity<BaseResponse> getVendor(@PathVariable String vendorId) {
    return vendorService.getVendor(vendorId);
  }

  @PutMapping("/{vendorId}")
  @PreAuthorize("hasAnyRole('ADMIN')")
  ResponseEntity<BaseResponse> updateVendor(@Valid @RequestBody VendorDTO vendorDTO, @PathVariable String vendorId) {
    return vendorService.updateVendor(vendorDTO,vendorId);
  }

  @DeleteMapping("/{vendorId}")
  @PreAuthorize("hasAnyRole('ADMIN')")
  ResponseEntity<BaseResponse> deleteVendor(@PathVariable String vendorId) {
    return vendorService.deleteVendor(vendorId);
  }
}
