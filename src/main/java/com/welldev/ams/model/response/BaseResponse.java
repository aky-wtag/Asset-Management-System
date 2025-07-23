package com.welldev.ams.model.response;

import lombok.Data;

@Data
public class BaseResponse {
  private int statusCode;
  private String message;
  private boolean successful;
  private Object data;
}
