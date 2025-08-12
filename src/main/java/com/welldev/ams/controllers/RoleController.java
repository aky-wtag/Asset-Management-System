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

import com.welldev.ams.model.request.RoleDTO;
import com.welldev.ams.model.response.BaseResponse;
import com.welldev.ams.service.RoleService;
import com.welldev.ams.utils.Utils;

@RestController
@RequestMapping("/role")
public class RoleController {
  private final RoleService roleService;
  private final Utils utils;

  public RoleController(RoleService roleService, Utils utils) {
    this.roleService = roleService;
    this.utils = utils;
  }

  @PostMapping
  @PreAuthorize("hasAnyRole('ADMIN')")
  ResponseEntity<BaseResponse> createRole(@Valid @RequestBody RoleDTO roleDTO) {
    RoleDTO created =  roleService.createRole(roleDTO);
    return ResponseEntity.status(HttpStatus.CREATED.value()).body(utils.generateResponse(created,true, HttpStatus.CREATED.value(), "Role Created Successfully"));
  }

  @GetMapping
  @PreAuthorize("hasAnyRole('ADMIN')")
  ResponseEntity<BaseResponse> getRoles(
      @RequestParam(required = false) String roleName,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int pageSize,
      @RequestParam(defaultValue = "createdAt") String sortBy,
      @RequestParam(defaultValue = "desc") String order) {
    Page<RoleDTO> roles = roleService.getRoles(roleName, page, pageSize, sortBy, order);
    return ResponseEntity.status(HttpStatus.OK.value()).body(utils.generateResponse(roles,true, HttpStatus.OK.value(), ""));
  }

  @GetMapping("/{roleId}")
  @PreAuthorize("hasAnyRole('ADMIN')")
  ResponseEntity<BaseResponse> getRole(@PathVariable String roleId) {
    RoleDTO role = roleService.getRole(roleId);
    return ResponseEntity.status(HttpStatus.OK.value()).body(utils.generateResponse(role,true, HttpStatus.OK.value(), ""));
  }

  @PutMapping("/{roleId}")
  @PreAuthorize("hasAnyRole('ADMIN')")
  ResponseEntity<BaseResponse> updateRole(@Valid @RequestBody RoleDTO roleDTO, @PathVariable String roleId) {
    RoleDTO updated = roleService.updateRole(roleDTO,roleId);
    return ResponseEntity.status(HttpStatus.OK.value()).body(utils.generateResponse(updated,true, HttpStatus.OK.value(), "Role Created Successfully"));
  }

  @DeleteMapping("/{roleId}")
  @PreAuthorize("hasAnyRole('ADMIN')")
  ResponseEntity<BaseResponse> deleteRole(@PathVariable String roleId) {
    roleService.deleteRole(roleId);
    return ResponseEntity.noContent().build();
  }
}
