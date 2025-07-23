package com.welldev.ams.model.db;

import java.time.ZonedDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class AssetRequest extends BaseDomain {
  @ManyToOne
  private Users requestedBy;
  private String assetName;
  private String reason;
  private String status; // PENDING, APPROVED, REJECTED
  private ZonedDateTime requestDate;
}
