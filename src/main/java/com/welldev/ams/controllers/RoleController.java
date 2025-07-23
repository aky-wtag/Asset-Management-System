package com.welldev.ams.controllers;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.welldev.ams.model.request.RoleDTO;
import com.welldev.ams.model.response.BaseResponse;
import com.welldev.ams.service.RoleService;

@RestController
@RequestMapping("/role")
public class RoleController {
  private final RoleService roleService;

  public RoleController(RoleService roleService) {
    this.roleService = roleService;
  }

  @PostMapping("/create-role")
  @PreAuthorize("hasAnyRole('ADMIN')")
  ResponseEntity<BaseResponse> createRole(@Valid @RequestBody RoleDTO roleDTO) {
    return roleService.createRole(roleDTO);
  }

  @GetMapping("/get-roles")
  @PreAuthorize("hasAnyRole('ADMIN')")
  ResponseEntity<BaseResponse> getRoles(
      @RequestParam(required = false) String roleName,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int pageSize,
      @RequestParam(defaultValue = "createdAt") String sortBy,
      @RequestParam(defaultValue = "desc") String order) {
    return roleService.getRoles(roleName, page, pageSize, sortBy, order);
  }

  @GetMapping("/get-role")
  @PreAuthorize("hasAnyRole('ADMIN')")
  ResponseEntity<BaseResponse> getRole(@RequestParam String roleId) {
    return roleService.getRole(roleId);
  }

  @PutMapping("/update-role")
  @PreAuthorize("hasAnyRole('ADMIN')")
  ResponseEntity<BaseResponse> updateRole(@Valid @RequestBody RoleDTO roleDTO, @RequestParam String roleId) {
    return roleService.updateRole(roleDTO,roleId);
  }

  @DeleteMapping("/delete-role")
  @PreAuthorize("hasAnyRole('ADMIN')")
  ResponseEntity<BaseResponse> deleteRole(@RequestParam String roleId) {
    return roleService.deleteRole(roleId);
  }
}
