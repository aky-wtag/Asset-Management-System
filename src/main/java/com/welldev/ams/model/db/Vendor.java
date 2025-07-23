package com.welldev.ams.model.db;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class Vendor extends BaseDomain {
  private String name;
  private String contactPerson;
  private String email;
  private String phone;
}
