package com.welldev.ams.model.request;

import java.time.ZonedDateTime;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AssetDTO {
  @NotEmpty(message = "Name Can Not Be Empty")
  private String name;
  @NotEmpty(message = "Serial Number Can Not Be Empty")
  private String serialNumber;
  @NotEmpty(message = "Category Can Not Be Empty")
  private String category;
  @NotEmpty(message = "Vendor Can Not Be Empty")
  private String vendor;
  @NotEmpty(message = "Location Can Not Be Empty")
  private String location;
  @NotNull(message = "Purchase Date Can Not Be Empty")
  private ZonedDateTime purchaseDate;
  @NotEmpty(message = "Status Can Not Be Empty")
  private String status;
  private Boolean active = true;
  private Boolean deleted = false;
}
