package com.welldev.ams.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.welldev.ams.model.db.Users;

public interface UserRepository extends JpaRepository<Users, UUID>, JpaSpecificationExecutor<Users> {
  Page<Users> findByActiveAndDeleted(Boolean active, Boolean deleted, Pageable pageable);
  Optional<Users> findByIdAndActiveAndDeleted(UUID id, Boolean active, Boolean deleted);
  Optional<Users> findByEmail(String userName);
  Users findByEmailAndPassword(String userName, String password);
}
