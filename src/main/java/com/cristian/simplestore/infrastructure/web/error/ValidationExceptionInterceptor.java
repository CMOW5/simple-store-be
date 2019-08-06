package com.cristian.simplestore.infrastructure.web.error;

import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cristian.simplestore.infrastructure.web.dto.ApiResponse;

// @ControllerAdvice(basePackages = "com.cristian.simplestore.web.controllers")
// @ResponseBody
public class ValidationExceptionInterceptor {

  @ExceptionHandler(BindException.class)
  public ResponseEntity<?> handleControllersValidationException(HttpServletRequest request,
      BindException exception) {
    return new ApiResponse().errors(exception).status(HttpStatus.BAD_REQUEST).build();
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> handleControllersValidationMethodArgumentNotValidException(
      HttpServletRequest request, MethodArgumentNotValidException exception) {
    return new ApiResponse().errors(exception.getBindingResult().getFieldErrors())
        .status(HttpStatus.BAD_REQUEST).build();
  }


}
