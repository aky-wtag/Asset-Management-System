package com.welldev.ams.service;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import com.welldev.ams.model.db.Users;
import com.welldev.ams.model.request.UserDTO;
import com.welldev.ams.model.response.BaseResponse;

public interface UsersService {
  UserDTO createUser(UserDTO userDTO);

  UserDTO updateUser(UserDTO userDTO, String userId);

  Page<UserDTO> getUsers(String username, String email, String department, int page, int size, String sortBy, String order);

  UserDTO getUser(String userId);

  void deleteUser(String userId);
}
