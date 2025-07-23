package com.welldev.ams.model.request;

import java.time.ZonedDateTime;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AssetRequestDTO {
  private String requestedBy;
  @NotEmpty(message = "Asset Name Can Not Be Empty")
  private String assetId;
  @NotEmpty(message = "Reason Can Not Be Empty")
  private String reason;
  @NotEmpty(message = "Status Can Not Be Empty")
  private String status; // PENDING, APPROVED, REJECTED
  @NotNull(message = "Request Date Can Not Be Empty")
  private ZonedDateTime requestDate;
  private Boolean active = true;
  private Boolean deleted = false;
}
