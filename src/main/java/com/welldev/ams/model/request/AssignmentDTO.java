package com.welldev.ams.model.request;

import java.time.ZonedDateTime;

import lombok.Data;

@Data
public class AssignmentDTO {
  private String assetId;
  private String userId;
  private ZonedDateTime assignedDate;
  private ZonedDateTime returnDate;
  private String remarks;
  private boolean active = true;
  private boolean deleted = false;
}
