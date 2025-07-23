package com.welldev.ams.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.welldev.ams.model.db.Vendor;

public interface VendorRepository extends JpaRepository<Vendor, UUID>, JpaSpecificationExecutor<Vendor> {
  Page<Vendor> findByActiveAndDeleted(Boolean active, Boolean deleted, Pageable pageable);
  Optional<Vendor> findByIdAndActiveAndDeleted(UUID id, Boolean active, Boolean deleted);
}
