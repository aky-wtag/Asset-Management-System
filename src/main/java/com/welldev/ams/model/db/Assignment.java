package com.welldev.ams.model.db;

import java.time.ZonedDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class Assignment extends BaseDomain{

  @ManyToOne
  private Asset asset;

  @ManyToOne
  private Users user;

  private ZonedDateTime assignedDate;
  private ZonedDateTime returnDate;

  private String remarks;
}
