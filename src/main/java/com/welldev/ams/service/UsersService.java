package com.welldev.ams.service;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import com.welldev.ams.model.db.Users;
import com.welldev.ams.model.dto.UserResponseDto;
import com.welldev.ams.model.request.UserDTO;
import com.welldev.ams.model.response.BaseResponse;

public interface UsersService {
  UserResponseDto createUser(UserDTO userDTO);

  UserResponseDto updateUser(UserDTO userDTO, String userId);

  Page<UserResponseDto> getUsers(String username, String email, String department, int page, int size, String sortBy, String order);

  UserResponseDto getUser(String userId);

  void deleteUser(String userId);
}
