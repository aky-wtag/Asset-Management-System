package com.welldev.ams.controllers;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.welldev.ams.model.dto.UserResponseDto;
import com.welldev.ams.model.request.UserDTO;
import com.welldev.ams.model.response.BaseResponse;
import com.welldev.ams.service.UsersService;
import com.welldev.ams.utils.Utils;

@RestController
@RequestMapping("/user")
public class UserController {
  private final UsersService usersService;
  private final Utils utils;

  public UserController(UsersService usersService,Utils utils) {
    this.usersService = usersService;
    this.utils = utils;
  }
  @PostMapping
  @PreAuthorize("hasAnyRole('ADMIN')")
  ResponseEntity<BaseResponse> createUser(@Valid @RequestBody UserDTO userDTO) {
    UserResponseDto user = usersService.createUser(userDTO);
    return ResponseEntity.status(HttpStatus.OK.value()).body(utils.generateResponse(user,true, HttpStatus.OK.value(), "User Created Successfully"));
  }

  @GetMapping
  @PreAuthorize("hasAnyRole('ADMIN')")
  ResponseEntity<BaseResponse> getRoles(
      @RequestParam(required = false) String username,
      @RequestParam(required = false) String email,
      @RequestParam(required = false) String department,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int pageSize,
      @RequestParam(defaultValue = "createdAt") String sortBy,
      @RequestParam(defaultValue = "desc") String order) {
    Page<UserResponseDto> users =  usersService.getUsers(username, email, department, page, pageSize, sortBy, order);
    return ResponseEntity.status(HttpStatus.OK.value()).body(utils.generateResponse(users,true, HttpStatus.OK.value(), ""));
  }

  @GetMapping("/{userId}")
  @PreAuthorize("hasAnyRole('ADMIN')")
  ResponseEntity<BaseResponse> getUser(@PathVariable String userId) {
    UserResponseDto user =  usersService.getUser(userId);
    return ResponseEntity.status(HttpStatus.OK.value()).body(utils.generateResponse(user,true, HttpStatus.OK.value(), ""));
  }

  @PutMapping("/{userId}")
  @PreAuthorize("hasAnyRole('ADMIN')")
  ResponseEntity<BaseResponse> updateUser(@Valid @RequestBody UserDTO userDTO, @PathVariable String userId) {
    UserResponseDto updated =  usersService.updateUser(userDTO,userId);
    return ResponseEntity.status(HttpStatus.OK.value()).body(utils.generateResponse(updated,true, HttpStatus.OK.value(), "User Updated"));
  }

  @DeleteMapping("/{userId}")
  @PreAuthorize("hasAnyRole('ADMIN')")
  ResponseEntity<BaseResponse> deleteUser(@PathVariable String userId) {
    usersService.deleteUser(userId);
    return ResponseEntity.noContent().build();
  }
}
