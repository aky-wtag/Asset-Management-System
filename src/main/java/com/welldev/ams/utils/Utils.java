package com.welldev.ams.utils;

import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.stereotype.Service;

import com.welldev.ams.model.response.BaseResponse;

@Service
public class Utils {
  public BaseResponse generateResponse(Object data, boolean isSuccess, int statusCode, String message){
    BaseResponse br = new BaseResponse();
    br.setSuccessful(isSuccess);
    br.setStatusCode(statusCode);
    br.setMessage(message);
    br.setData(data);
    return br;
  }
}
