package com.welldev.ams.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.welldev.ams.model.db.Assignment;

public interface AssignmentRepository extends JpaRepository<Assignment, UUID>,
    JpaSpecificationExecutor<Assignment>
{
  Page<Assignment> findByActiveAndDeleted(Boolean active, Boolean deleted, Pageable pageable);
  Optional<Assignment> findByIdAndActiveAndDeleted(UUID id, Boolean active, Boolean deleted);
}
