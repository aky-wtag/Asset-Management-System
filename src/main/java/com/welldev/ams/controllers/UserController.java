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
import com.welldev.ams.model.request.UserDTO;
import com.welldev.ams.model.response.BaseResponse;
import com.welldev.ams.service.UsersService;

@RestController
@RequestMapping("/user")
public class UserController {
  private final UsersService usersService;

  public UserController(UsersService usersService) {
    this.usersService = usersService;
  }
  @PostMapping("/create-user")
  @PreAuthorize("hasAnyRole('ADMIN')")
  ResponseEntity<BaseResponse> createUser(@Valid @RequestBody UserDTO userDTO) {
    return usersService.createUser(userDTO);
  }

  @GetMapping("/get-users")
  @PreAuthorize("hasAnyRole('ADMIN')")
  ResponseEntity<BaseResponse> getRoles(
      @RequestParam(required = false) String username,
      @RequestParam(required = false) String email,
      @RequestParam(required = false) String department,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int pageSize,
      @RequestParam(defaultValue = "createdAt") String sortBy,
      @RequestParam(defaultValue = "desc") String order) {
    return usersService.getUsers(username, email, department, page, pageSize, sortBy, order);
  }

  @GetMapping("/get-user")
  @PreAuthorize("hasAnyRole('ADMIN')")
  ResponseEntity<BaseResponse> getUser(@RequestParam String userId) {
    return usersService.getUser(userId);
  }

  @PutMapping("/update-user")
  @PreAuthorize("hasAnyRole('ADMIN')")
  ResponseEntity<BaseResponse> updateUser(@Valid @RequestBody UserDTO userDTO, @RequestParam String userId) {
    return usersService.updateUser(userDTO,userId);
  }

  @DeleteMapping("/delete-user")
  @PreAuthorize("hasAnyRole('ADMIN')")
  ResponseEntity<BaseResponse> deleteUser(@RequestParam String userId) {
    return usersService.deleteUser(userId);
  }
}
