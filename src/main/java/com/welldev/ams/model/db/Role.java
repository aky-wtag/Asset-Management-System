package com.welldev.ams.model.db;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class Role extends  BaseDomain{
  private String name;
}
