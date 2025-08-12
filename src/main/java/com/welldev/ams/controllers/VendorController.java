package com.welldev.ams.controllers;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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
import com.welldev.ams.utils.Utils;

@RestController
@RequestMapping("/vendor")
public class VendorController {
  private final VendorService vendorService;
  private final Utils utils;

  public VendorController(VendorService vendorService, Utils utils) {
    this.vendorService = vendorService;
    this.utils = utils;
  }

  @PostMapping
  @PreAuthorize("hasAnyRole('ADMIN')")
  ResponseEntity<BaseResponse> createVendor(@Valid @RequestBody VendorDTO vendorDTO) {
    VendorDTO vendor = vendorService.createVendor(vendorDTO);
    return ResponseEntity.status(HttpStatus.OK.value()).body(utils.generateResponse(vendor,true, HttpStatus.OK.value(), "Vendor Created Successfully"));
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
    Page<VendorDTO> vendorList = vendorService.getVendors(name, contactPerson, email, phone, page, pageSize, sortBy, order);
    return ResponseEntity.status(HttpStatus.OK.value()).body(utils.generateResponse(vendorList,true, HttpStatus.OK.value(), ""));
  }

  @GetMapping("/{vendorId}")
  @PreAuthorize("hasAnyRole('ADMIN')")
  ResponseEntity<BaseResponse> getVendor(@PathVariable String vendorId) {
    VendorDTO vendor = vendorService.getVendor(vendorId);
    return ResponseEntity.status(HttpStatus.OK.value()).body(utils.generateResponse(vendor,true, HttpStatus.OK.value(), ""));
  }

  @PutMapping("/{vendorId}")
  @PreAuthorize("hasAnyRole('ADMIN')")
  ResponseEntity<BaseResponse> updateVendor(@Valid @RequestBody VendorDTO vendorDTO, @PathVariable String vendorId) {
    VendorDTO vendor = vendorService.updateVendor(vendorDTO,vendorId);
    return ResponseEntity.status(HttpStatus.OK.value()).body(utils.generateResponse(vendor,true, HttpStatus.OK.value(), "Vendor Updated Successfully"));
  }

  @DeleteMapping("/{vendorId}")
  @PreAuthorize("hasAnyRole('ADMIN')")
  ResponseEntity<BaseResponse> deleteVendor(@PathVariable String vendorId) {
    vendorService.deleteVendor(vendorId);
    return ResponseEntity.noContent().build();
  }
}
