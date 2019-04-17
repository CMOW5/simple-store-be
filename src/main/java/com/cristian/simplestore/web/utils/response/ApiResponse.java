package com.cristian.simplestore.web.utils.response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;

public class ApiResponse {

  private static final String CONTENT_KEY = "content";
  private static final String ERRORS_KEY = "errors";
  private static final String STATUS_KEY = "status";

  private HttpStatus status;
  private HttpHeaders headers;
  private Object content;
  private List<ApiError> errors;
  private Map<String, Object> attachments;

  public ApiResponse() {
    this.attachments = new HashMap<>();
    this.errors = new ArrayList<>();
    this.status = HttpStatus.OK;
    this.headers = new HttpHeaders();
    this.attachments.put(CONTENT_KEY, null);
  }

  public ApiResponse status(HttpStatus status) {
    this.status = status;
    this.attachments.put(STATUS_KEY, this.status.value());
    return this;
  }

  public <T> ApiResponse content(T content) {
    this.content = content;
    this.attachments.put(CONTENT_KEY, this.content);
    return this;
  }

  public ApiResponse errors(BindException exception) {
    for (FieldError fieldError : exception.getFieldErrors()) {
      ApiError error = new ApiError(fieldError);
      this.errors.add(error);
    }
    this.attachments.put(ERRORS_KEY, this.errors);
    return this;
  }

  public ApiResponse errors(List<FieldError> fieldErrors) {
    for (FieldError fieldError : fieldErrors) {
      ApiError error = new ApiError(fieldError);
      this.errors.add(error);
    }
    this.attachments.put(ERRORS_KEY, this.errors);
    return this;
  }

  public ApiResponse addError(ApiError error) {
    this.errors.add(error);
    this.attachments.put(ERRORS_KEY, this.errors);
    return this;
  }
  
  public ApiResponse addHeader(String header, String value) {
    this.headers.add(header, value);
    return this;
  }

  public <T> ApiResponse attach(String key, T attachment) {
    this.attachments.put(key, attachment);
    return this;
  }

  public ResponseEntity<?> build() { 
    return new ResponseEntity<>(this.attachments, this.headers, this.status);
  }
}
