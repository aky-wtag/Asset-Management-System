package com.welldev.ams.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.welldev.ams.model.db.Role;

public interface RoleRepository extends JpaRepository<Role, UUID> {
  Page<Role> findByNameContainingIgnoreCaseAndDeletedAndActive(String name, Boolean deleted, Boolean active, Pageable pageable);
  Page<Role> findByActiveAndDeleted(Boolean active, Boolean deleted, Pageable pageable);
  Optional<Role> findByIdAndActiveAndDeleted(UUID id, Boolean active, Boolean deleted);
}
