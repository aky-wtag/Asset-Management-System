package com.welldev.ams.model.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LocationDTO {
  @NotEmpty(message = "Building Can Not Be Empty")
  private String building;
  @NotEmpty(message = "Floor Can Not Be Empty")
  private String floor;
  @NotEmpty(message = "Room Number Can Not Be Empty")
  private String roomNumber;
  @NotEmpty(message = "City Can Not Be Empty")
  private String city;
  private Boolean active = true;
  private Boolean deleted = false;
}
