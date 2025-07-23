package com.welldev.ams.service;

import org.springframework.http.ResponseEntity;

import com.welldev.ams.model.request.UserDTO;
import com.welldev.ams.model.response.BaseResponse;

public interface UsersService {
  ResponseEntity<BaseResponse> createUser(UserDTO userDTO);

  ResponseEntity<BaseResponse> updateUser(UserDTO userDTO, String userId);

  ResponseEntity<BaseResponse> getUsers(String username, String email, String department, int page, int size, String sortBy, String order);

  ResponseEntity<BaseResponse> getUser(String userId);

  ResponseEntity<BaseResponse> deleteUser(String userId);
}
