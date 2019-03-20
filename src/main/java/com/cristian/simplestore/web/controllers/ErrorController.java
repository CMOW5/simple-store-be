package com.cristian.simplestore.web.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cristian.simplestore.web.utils.response.ApiResponse;

@ControllerAdvice(basePackageClasses = ErrorController.class)
@ResponseBody
public class ErrorController {
	
	ApiResponse response = new ApiResponse();
	
	@ExceptionHandler(BindException.class)
	ResponseEntity<?> handleControllersValidationException(HttpServletRequest request, 
			BindException exception) {
		return response.errors(exception)
				.status(HttpStatus.BAD_REQUEST)
				.build();
	}
}
