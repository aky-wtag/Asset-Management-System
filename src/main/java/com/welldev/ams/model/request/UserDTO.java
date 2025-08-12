package com.welldev.ams.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
public class UserDTO {
  @JsonIgnore
  @NotEmpty(message = "Username Can Not Be Empty")
  private String username;
  @NotEmpty(message = "Email Can Not Be Empty")
  @Email(message = "Please provide a valid email address")
  private String email;
  @JsonIgnore
  private String password;
  private String role;
  @NotEmpty(message = "Department Can Not Be Empty")
  private String department;
  private Boolean active = true;
  private Boolean deleted = false;
}
