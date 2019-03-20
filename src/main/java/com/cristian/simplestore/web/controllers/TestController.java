package com.cristian.simplestore.web.controllers;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cristian.simplestore.business.services.ImageService;
import com.cristian.simplestore.web.forms.TestForm;
import com.cristian.simplestore.web.utils.response.CustomResponse;


@RestController
public class TestController {

	private CustomResponse response = new CustomResponse();

	@Autowired ImageService imageService;
	
	@PersistenceContext EntityManager entityManager;

	@RequestMapping(value = "/test", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> create(@Valid TestForm form) {
		String some = "some";
		response.content(some);
		return response.build();
	}
//	
//	@ExceptionHandler(BindException.class)
//	public ResponseEntity<Map<String, Object>> handleValidationErrors(
//			BindException exception) {
//		return response.errors(exception)
//				.status(HttpStatus.BAD_REQUEST)
//				.build();
//		
//		
//	}
	

	// @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Data integrity violation")
//	@ExceptionHandler(BindException.class)
//	public Map<String, Object> handleError(HttpServletRequest req, Exception ex) {
//		// logger.error("Request: " + req.getRequestURL() + " raised " + ex);
//		response.attachContent("some error");
//		return response.build();
//	}
}
