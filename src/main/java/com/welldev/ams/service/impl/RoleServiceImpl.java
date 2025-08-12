package com.welldev.ams.service.impl;

import java.util.Optional;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.welldev.ams.model.db.Role;
import com.welldev.ams.model.mapper.RoleMapper;
import com.welldev.ams.model.request.RoleDTO;
import com.welldev.ams.repositories.RoleRepository;
import com.welldev.ams.service.RoleService;

@Slf4j
@Service
public class RoleServiceImpl implements RoleService {
  private final RoleRepository roleRepository;
  private final RoleMapper roleMapper;

  public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper) {
    this.roleRepository = roleRepository;
    this.roleMapper = roleMapper;
  }

  @Override
  public RoleDTO createRole(RoleDTO role) {
      Role saveEntity = roleRepository.save(roleMapper.roleToEntity(role));
      return roleMapper.toDto(saveEntity);
  }

  @Override
  public RoleDTO updateRole(RoleDTO role, String roleId) {
      Optional<Role> roleEntity = roleRepository.findByIdAndActiveAndDeleted(UUID.fromString(roleId), true, false);
      if(roleEntity.isPresent()) {
        roleMapper.updateRole(role, roleEntity.get());
        Role saveEntity = roleRepository.save(roleEntity.get());
        return roleMapper.toDto(saveEntity);
      }
      throw new RuntimeException("Role not found");
  }

  @Override
  public Page<RoleDTO> getRoles(String roleName, int page, int size, String sortBy, String order) {
      Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), sortBy));
      if(roleName != null && !roleName.isEmpty()) {
        Page<Role> roleList = roleRepository.findByNameContainingIgnoreCaseAndDeletedAndActive(roleName, false, true, pageable);
        return roleList.map(roleMapper::toDto);
      }
      else{
        Page<Role> roleList = roleRepository.findByActiveAndDeleted(true, false, pageable);
        return roleList.map(roleMapper::toDto);
      }
  }

  @Override
  public RoleDTO getRole(String roleId) {
      Optional<Role> role = roleRepository.findById(UUID.fromString(roleId));
      if (role.isPresent()) {
        return roleMapper.toDto(role.get());
      }
      throw new RuntimeException("Role not found");
  }

  @Override
  public void deleteRole(String roleId) {
      Optional<Role> role = roleRepository.findByIdAndActiveAndDeleted(UUID.fromString(roleId), true, false);
      if(role.isPresent()) {
        role.get().setDeleted(true);
        roleRepository.save(role.get());
      } else {
        throw new RuntimeException("Location not found");
      }
  }
}
