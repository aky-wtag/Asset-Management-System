package com.welldev.ams.service;

import org.springframework.http.ResponseEntity;

import com.welldev.ams.model.request.RoleDTO;
import com.welldev.ams.model.response.BaseResponse;

public interface RoleService {
  ResponseEntity<BaseResponse> createRole(RoleDTO role);

  ResponseEntity<BaseResponse> updateRole(RoleDTO role, String roleId);

  ResponseEntity<BaseResponse> getRoles(String roleName, int page, int size, String sortBy, String order);

  ResponseEntity<BaseResponse> getRole(String roleId);

  ResponseEntity<BaseResponse> deleteRole(String roleId);
}
