package com.welldev.ams.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.welldev.ams.model.db.AssetRequest;

public interface AssetRequestRepository extends JpaRepository<AssetRequest, UUID>, JpaSpecificationExecutor<AssetRequest> {
  Page<AssetRequest> findByActiveAndDeleted(Boolean active, Boolean deleted, Pageable pageable);
  Optional<AssetRequest> findByIdAndActiveAndDeleted(UUID id, Boolean active, Boolean deleted);
}
