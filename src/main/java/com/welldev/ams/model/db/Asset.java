package com.welldev.ams.model.db;

import java.time.ZonedDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class Asset extends BaseDomain{

  private String name;
  private String serialNumber;

  @ManyToOne
  private Category category;

  @ManyToOne
  private Vendor vendor;

  @ManyToOne
  private Location location;

  private ZonedDateTime purchaseDate;
  private String status; // Available, Assigned, In Maintenance, etc.
}
