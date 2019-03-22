package com.cristian.simplestore.web.controllers.interceptors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import com.cristian.simplestore.web.utils.response.ApiResponse;

@ControllerAdvice(basePackages = "com.cristian.simplestore.web.controllers")
@ResponseBody
public class ValidationExceptionInterceptor {

	@ExceptionHandler(BindException.class)
	public ResponseEntity<?> handleControllersValidationException(HttpServletRequest request, 
			BindException exception) {
		return new ApiResponse()
				.errors(exception)
				.status(HttpStatus.BAD_REQUEST)
				.build();
	}
	
	
}
