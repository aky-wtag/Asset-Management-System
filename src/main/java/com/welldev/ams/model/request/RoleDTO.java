package com.welldev.ams.model.request;

import java.util.UUID;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class RoleDTO {
  private UUID id;
  @NotEmpty(message = "Role Name Can Not Be Empty")
  private String name;
  private Boolean active = true;
  private Boolean deleted = false;
}
