package com.welldev.ams.model.db;

import java.time.ZonedDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class MaintenanceLog extends BaseDomain {
  @ManyToOne
  private Asset asset;
  private ZonedDateTime maintenanceDate;
  private String description;
  private String performedBy;
}
