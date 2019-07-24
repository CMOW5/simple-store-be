package com.cristian.simplestore.infrastructure.error;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cristian.simplestore.infrastructure.controllers.dto.ApiResponse;

@ControllerAdvice
@ResponseBody
public class EntityNotFoundHandler {

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<?> handleEntityNotFound(EntityNotFoundException exception) {
		return new ApiResponse().status(HttpStatus.NOT_FOUND).content(null).build();
	}
}
