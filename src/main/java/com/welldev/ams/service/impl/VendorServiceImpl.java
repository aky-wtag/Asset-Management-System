package com.welldev.ams.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.welldev.ams.model.db.Location;
import com.welldev.ams.model.db.Role;
import com.welldev.ams.model.db.Vendor;
import com.welldev.ams.model.mapper.VendorMapper;
import com.welldev.ams.model.request.VendorDTO;
import com.welldev.ams.model.response.BaseResponse;
import com.welldev.ams.repositories.VendorRepository;
import com.welldev.ams.service.VendorService;
import com.welldev.ams.utils.Utils;

@Slf4j
@Service
public class VendorServiceImpl implements VendorService {
  private final VendorRepository vendorRepository;
  private final Utils utils;
  private final VendorMapper vendorMapper;

  public VendorServiceImpl(VendorRepository vendorRepository, Utils utils, VendorMapper vendorMapper) {
    this.vendorRepository = vendorRepository;
    this.utils = utils;
    this.vendorMapper = vendorMapper;
  }

  @Override
  public ResponseEntity<BaseResponse> createVendor(VendorDTO vendorDTO) {
    try {
      Vendor saveEntity = vendorRepository.save(vendorMapper.vendorToEntity(vendorDTO));
      return ResponseEntity.ok().body(utils.generateResponse(saveEntity, true, HttpStatus.OK.value(), "Vendor Created Successfully."));
    }
    catch (Exception e) {
      log.error("Create Vendor Error: {}", e);
      return ResponseEntity.internalServerError().body(utils.generateResponse(null, false, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
    }
  }

  @Override
  public ResponseEntity<BaseResponse> updateVendor(VendorDTO vendorDTO, String vendorId) {
    try {
      Optional<Vendor> entity = vendorRepository.findByIdAndActiveAndDeleted(UUID.fromString(vendorId), true, false);
      if(entity.isPresent()) {
        vendorMapper.updateVendor(vendorDTO, entity.get());
        Vendor saveEntity = vendorRepository.save(entity.get());
        return ResponseEntity.ok().body(utils.generateResponse(saveEntity, true, HttpStatus.OK.value(), ""));
      }
      return ResponseEntity.notFound().build();
    }
    catch (Exception e) {
      log.error("Update Vendor Error: {}", e);
      return ResponseEntity.internalServerError().body(utils.generateResponse(null, false, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
    }
  }

  @Override
  public ResponseEntity<BaseResponse> getVendors(String name, String contactPerson, String email, String phone, int page, int size, String sortBy, String order) {
    try {
      Specification<Vendor> spec = (root, query, cb) -> {
        List<Predicate> predicates = new ArrayList<>();

        if (name != null && !name.isEmpty()) {
          predicates.add(cb.like(root.get("name"), "%"+name+"%"));
        }
        if (contactPerson != null && !contactPerson.isEmpty()) {
          predicates.add(cb.equal(root.get("contactPerson"), contactPerson));
        }
        if (email != null && !email.isEmpty()) {
          predicates.add(cb.equal(root.get("email"), email));
        }
        if (phone != null && !phone.isEmpty()) {
          predicates.add(cb.equal(root.get("phone"), phone));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
      };
      Page<Vendor> vendorList = vendorRepository.findAll(spec, PageRequest.of(page, size));
      return ResponseEntity.ok().body(utils.generateResponse(vendorList, true, HttpStatus.OK.value(), ""));
    }
    catch (Exception e) {
      log.error("Get Users Error: {}", e);
      return ResponseEntity.internalServerError().body(utils.generateResponse(null, false, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
    }
  }

  @Override
  public ResponseEntity<BaseResponse> getVendor(String vendorId) {
    try {
      Optional<Vendor> vendor = vendorRepository.findById(UUID.fromString(vendorId));
      return vendor.map(value -> ResponseEntity.ok()
              .body(utils.generateResponse(value, true, HttpStatus.OK.value(), "")))
          .orElseGet(() -> ResponseEntity.notFound()
              .build());
    }
    catch (Exception e) {
      log.error("Get Vendor Error: {}", vendorId, e);
      return ResponseEntity.internalServerError().body(utils.generateResponse(null, false, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
    }
  }

  @Override
  public ResponseEntity<BaseResponse> deleteVendor(String vendorId) {
    try {
      Optional<Vendor> vendor = vendorRepository.findByIdAndActiveAndDeleted(UUID.fromString(vendorId), true, false);
      if(vendor.isPresent()) {
        vendor.get().setDeleted(true);
        vendorRepository.save(vendor.get());
        return ResponseEntity.noContent().build();
      }
      else{
        return ResponseEntity.notFound().build();
      }
    }
    catch (Exception e) {
      log.error("Delete Vendor Error: {}", vendorId, e);
      return ResponseEntity.internalServerError().body(utils.generateResponse(null, false, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
    }
  }
}
