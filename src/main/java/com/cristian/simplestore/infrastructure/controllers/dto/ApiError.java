package com.cristian.simplestore.infrastructure.controllers.dto;

import org.springframework.validation.FieldError;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApiError {

  private String field;
  private String defaultMessage;
  private Object rejectedValue;

  public ApiError(FieldError fieldError) {
    this.field = fieldError.getField();
    this.defaultMessage = fieldError.getDefaultMessage();
    this.rejectedValue = fieldError.getRejectedValue();
  }

  public ApiError(String field, String defaultMessage, Object rejectedValue) {
    this.field = field;
    this.defaultMessage = defaultMessage;
    this.rejectedValue = rejectedValue;
  }
}
