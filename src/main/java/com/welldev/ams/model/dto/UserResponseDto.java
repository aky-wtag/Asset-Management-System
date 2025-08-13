package com.welldev.ams.model.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class UserResponseDto {
  private UUID id;
  private String email;
  private String role;
  private String department;
  private Boolean active = true;
  private Boolean deleted = false;
}
