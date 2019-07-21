package com.cristian.simplestore.web.controllers.interceptors;

import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import com.cristian.simplestore.web.utils.response.ApiError;
import com.cristian.simplestore.web.utils.response.ApiResponse;

// @ControllerAdvice
// @ResponseBody
public class MaxFileUploadInterceptor {

  @ExceptionHandler(MaxUploadSizeExceededException.class)
  public ResponseEntity<?> handleMaxUploadSize(HttpServletRequest request,
      MaxUploadSizeExceededException exception) {
    ApiError error = new ApiError("file", exception.getMessage(), null);
    return new ApiResponse().addError(error).status(HttpStatus.BAD_REQUEST).build();
  }
}
