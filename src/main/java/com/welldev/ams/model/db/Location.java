package com.welldev.ams.model.db;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class Location extends BaseDomain {
  private String building;
  private String floor;
  private String roomNumber;
  private String city;
}
