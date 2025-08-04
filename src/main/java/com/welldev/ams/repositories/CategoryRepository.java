package com.welldev.ams.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.welldev.ams.model.db.Category;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
  Page<Category> findByNameContainingIgnoreCaseAndDeletedAndActive(String name, Boolean deleted, Boolean active, Pageable pageable);
  Page<Category> findByActiveAndDeleted(Boolean active, Boolean deleted, Pageable pageable);
  Optional<Category> findByIdAndActiveAndDeleted(UUID id, Boolean active, Boolean deleted);
  Optional<Category> findByIdAndDeleted(UUID id, Boolean deleted);
}
