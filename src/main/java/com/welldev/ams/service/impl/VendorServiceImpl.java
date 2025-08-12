package com.welldev.ams.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.welldev.ams.model.db.Vendor;
import com.welldev.ams.model.mapper.VendorMapper;
import com.welldev.ams.model.request.VendorDTO;
import com.welldev.ams.repositories.VendorRepository;
import com.welldev.ams.service.VendorService;

@Slf4j
@Service
@Transactional
public class VendorServiceImpl implements VendorService {
  private final VendorRepository vendorRepository;
  private final VendorMapper vendorMapper;

  public VendorServiceImpl(VendorRepository vendorRepository, VendorMapper vendorMapper) {
    this.vendorRepository = vendorRepository;
    this.vendorMapper = vendorMapper;
  }

  @Override
  public VendorDTO createVendor(VendorDTO vendorDTO) {
    Vendor saveEntity = vendorRepository.save(vendorMapper.vendorToEntity(vendorDTO));
    return vendorMapper.toDto(saveEntity);
  }

  @Override
  public VendorDTO updateVendor(VendorDTO vendorDTO, String vendorId) {
    Optional<Vendor> entity = vendorRepository.findByIdAndActiveAndDeleted(UUID.fromString(vendorId), true, false);
    if (entity.isPresent()) {
      vendorMapper.updateVendor(vendorDTO, entity.get());
      Vendor saveEntity = vendorRepository.save(entity.get());
      return vendorMapper.toDto(saveEntity);
    }
    else {
      throw new RuntimeException("Vendor not found");
    }
  }

  @Override
  public Page<VendorDTO> getVendors(String name, String contactPerson, String email, String phone, int page, int size,
      String sortBy, String order)
  {
    Specification<Vendor> spec = (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<>();

      if (name != null && !name.isEmpty()) {
        predicates.add(cb.like(root.get("name"), "%" + name + "%"));
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

      predicates.add(cb.equal(root.get("active"), true));
      predicates.add(cb.equal(root.get("deleted"), false));

      return cb.and(predicates.toArray(new Predicate[0]));
    };
    Page<Vendor> vendorList = vendorRepository.findAll(spec, PageRequest.of(page, size));
    return vendorList.map(vendorMapper::toDto);
  }

  @Override
  public VendorDTO getVendor(String vendorId) {
    Optional<Vendor> vendor = vendorRepository.findById(UUID.fromString(vendorId));
    if (vendor.isPresent()) {
      return vendorMapper.toDto(vendor.get());
    }
    else {
      throw new RuntimeException("Vendor not found");
    }
  }

  @Override
  public void deleteVendor(String vendorId) {
    Optional<Vendor> vendor = vendorRepository.findByIdAndActiveAndDeleted(UUID.fromString(vendorId), true, false);
    if (vendor.isPresent()) {
      vendor.get()
          .setDeleted(true);
      vendorRepository.save(vendor.get());
    }
    else {
      throw new RuntimeException("Vendor not found");
    }
  }
}
