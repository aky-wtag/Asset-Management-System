package com.welldev.ams.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class VendorDTO {
  @NotEmpty(message = "Vendor Name Can Not Be Empty")
  private String name;
  @NotEmpty(message = "Contact Person Can Not Be Empty")
  private String contactPerson;
  @NotEmpty(message = "Email Can Not Be Empty")
  @Email(message = "Please provide a valid email address")
  private String email;
  @NotEmpty(message = "Phone Can Not Be Empty")
  private String phone;
  private Boolean active = true;
  private Boolean deleted = false;
}
