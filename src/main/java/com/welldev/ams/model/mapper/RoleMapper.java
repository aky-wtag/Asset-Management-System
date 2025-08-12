package com.welldev.ams.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.welldev.ams.model.db.Role;
import com.welldev.ams.model.request.RoleDTO;

@Mapper(componentModel = "spring")
public interface RoleMapper {
  Role roleToEntity(RoleDTO roleDTO);

  void updateRole(RoleDTO roleDTO, @MappingTarget Role role);

  RoleDTO toDto(Role role);
}
