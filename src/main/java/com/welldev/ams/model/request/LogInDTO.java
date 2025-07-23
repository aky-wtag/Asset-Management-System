package com.welldev.ams.model.request;

import lombok.Data;

@Data
public class LogInDTO {
  private String email;
  private String password;
}
