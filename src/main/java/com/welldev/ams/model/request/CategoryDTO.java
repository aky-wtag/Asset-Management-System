package com.welldev.ams.model.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CategoryDTO {
  @NotEmpty(message = "Category Name Can Not Be Empty")
  private String name; // e.g., Laptop, Monitor, Chair
  private String description;
  private Boolean active = true;
  private Boolean deleted = false;
}
