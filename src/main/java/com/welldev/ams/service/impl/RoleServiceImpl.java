package com.welldev.ams.service.impl;

import java.util.Optional;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.welldev.ams.model.db.Role;
import com.welldev.ams.model.mapper.RoleMapper;
import com.welldev.ams.model.request.RoleDTO;
import com.welldev.ams.model.response.BaseResponse;
import com.welldev.ams.repositories.RoleRepository;
import com.welldev.ams.service.RoleService;
import com.welldev.ams.utils.Utils;

@Slf4j
@Service
public class RoleServiceImpl implements RoleService {
  private final RoleRepository roleRepository;
  private final Utils utils;
  private final RoleMapper roleMapper;

  public RoleServiceImpl(RoleRepository roleRepository, Utils utils, RoleMapper roleMapper) {
    this.roleRepository = roleRepository;
    this.utils = utils;
    this.roleMapper = roleMapper;
  }

  @Override
  public ResponseEntity<BaseResponse> createRole(RoleDTO role) {
    try {
      Role saveEntity = roleRepository.save(roleMapper.roleToEntity(role));
      return ResponseEntity.ok().body(utils.generateResponse(saveEntity, true, HttpStatus.OK.value(), "Role Created Successfully."));
    }
    catch (Exception e) {
      log.error("Create Role Error: {}", e);
      return ResponseEntity.internalServerError().body(utils.generateResponse(null, false, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
    }
  }

  @Override
  public ResponseEntity<BaseResponse> updateRole(RoleDTO role, String roleId) {
    try {
      Optional<Role> roleEntity = roleRepository.findByIdAndActiveAndDeleted(UUID.fromString(roleId), true, false);
      if(roleEntity.isPresent()) {
        roleMapper.updateRole(role, roleEntity.get());
        Role saveEntity = roleRepository.save(roleEntity.get());
        return ResponseEntity.ok().body(utils.generateResponse(saveEntity, true, HttpStatus.OK.value(), ""));
      }
      return ResponseEntity.notFound().build();
    }
    catch (Exception e) {
      log.error("Update Role Error: {}", e);
      return ResponseEntity.internalServerError().body(utils.generateResponse(null, false, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
    }
  }

  @Override
  public ResponseEntity<BaseResponse> getRoles(String roleName, int page, int size, String sortBy, String order) {
    try {
      Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), sortBy));
      if(roleName != null && !roleName.isEmpty()) {
        Page<Role> roleList = roleRepository.findByNameContainingIgnoreCaseAndDeletedAndActive(roleName, false, true, pageable);
        return ResponseEntity.ok().body(utils.generateResponse(roleList, true, HttpStatus.OK.value(), ""));
      }
      else{
        Page<Role> roleList = roleRepository.findByActiveAndDeleted(true, false, pageable);
        return ResponseEntity.ok().body(utils.generateResponse(roleList, true, HttpStatus.OK.value(), ""));
      }
    }
    catch (Exception e) {
      log.error("Get Roles Error: {}", e);
      return ResponseEntity.internalServerError().body(utils.generateResponse(null, false, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
    }
  }

  @Override
  public ResponseEntity<BaseResponse> getRole(String roleId) {
    try {
      Optional<Role> role = roleRepository.findById(UUID.fromString(roleId));
      return role.map(value -> ResponseEntity.ok()
              .body(utils.generateResponse(value, true, HttpStatus.OK.value(), "")))
          .orElseGet(() -> ResponseEntity.notFound()
              .build());
    }
    catch (Exception e) {
      log.error("Get Role Error: {}", roleId, e);
      return ResponseEntity.internalServerError().body(utils.generateResponse(null, false, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
    }
  }

  @Override
  public ResponseEntity<BaseResponse> deleteRole(String roleId) {
    try {
      Optional<Role> role = roleRepository.findByIdAndActiveAndDeleted(UUID.fromString(roleId), true, false);
      if(role.isPresent()) {
        role.get().setDeleted(true);
        roleRepository.save(role.get());
        return ResponseEntity.noContent().build();
      }
      else{
        return ResponseEntity.notFound().build();
      }
    }
    catch (Exception e) {
      log.error("Delete Role Error: {}", roleId, e);
      return ResponseEntity.internalServerError().body(utils.generateResponse(null, false, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
    }
  }
}
