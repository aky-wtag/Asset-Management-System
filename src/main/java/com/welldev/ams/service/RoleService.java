package com.welldev.ams.service;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import com.welldev.ams.model.request.RoleDTO;
import com.welldev.ams.model.response.BaseResponse;

public interface RoleService {
  RoleDTO createRole(RoleDTO role);

  RoleDTO updateRole(RoleDTO role, String roleId);

  Page<RoleDTO> getRoles(String roleName, int page, int size, String sortBy, String order);

  RoleDTO getRole(String roleId);

  void  deleteRole(String roleId);
}
