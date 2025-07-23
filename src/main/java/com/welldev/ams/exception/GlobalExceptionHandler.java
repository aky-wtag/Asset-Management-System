package com.welldev.ams.exception;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException exception) {
    List<String> errorMessage = exception.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(DefaultMessageSourceResolvable::getDefaultMessage)
        .collect(Collectors.toList());

    Map<String, Object> response = new HashMap<>();
    response.put("timestamp", ZonedDateTime.now().toString());
    response.put("status", HttpStatus.BAD_REQUEST.value());
    response.put("error", "Bad Request");
    response.put("message", errorMessage);

    return  new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<Map<String, Object>> handleMissingParams(MissingServletRequestParameterException exception) {
    String parameterName = exception.getParameterName();
    String message = "The required parameter '" + parameterName + "' is missing.";

    Map<String, Object> body = new HashMap<>();
    body.put("timestamp", ZonedDateTime.now());
    body.put("status", HttpStatus.BAD_REQUEST.value());
    body.put("error", "Bad Request");
    body.put("message", message);

    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<Map<String, Object>> handleAccessDeniedException(AccessDeniedException ex) {
    Map<String, Object> body = Map.of(
        "timestamp", ZonedDateTime.now(),
        "status", HttpStatus.FORBIDDEN.value(),
        "error", "Forbidden",
        "message", "You do not have permission to access this resource."
    );

    return new ResponseEntity<>(body, HttpStatus.FORBIDDEN);
  }
}
