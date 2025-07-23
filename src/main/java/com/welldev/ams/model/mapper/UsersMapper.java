package com.welldev.ams.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.welldev.ams.model.db.Users;
import com.welldev.ams.model.request.UserDTO;

@Mapper(componentModel = "spring")
public interface UsersMapper {
  @Mapping(target = "roles", ignore = true)
  Users toUserEntity(UserDTO userDTO);


  @Mapping(target = "id", ignore = true)
  @Mapping(target = "roles", ignore = true)
  @Mapping(target = "password", ignore = true) // Password update should be handled explicitly
  void updateUserFromDto(UserDTO userDTO, @MappingTarget Users user);
}


