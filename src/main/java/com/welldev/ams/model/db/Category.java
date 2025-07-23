package com.welldev.ams.model.db;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class Category extends BaseDomain {
  private String name; // e.g., Laptop, Monitor, Chair
  private String description;
}
