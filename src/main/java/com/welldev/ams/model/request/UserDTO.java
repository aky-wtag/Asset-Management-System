package com.welldev.ams.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserDTO {
  @NotEmpty(message = "Username Can Not Be Empty")
  private String username;
  @NotEmpty(message = "Email Can Not Be Empty")
  @Email(message = "Please provide a valid email address")
  private String email;
  private String password;
  private String role;
  @NotEmpty(message = "Department Can Not Be Empty")
  private String department;
  private Boolean active = true;
  private Boolean deleted = false;
}
