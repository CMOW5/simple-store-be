package com.cristian.simplestore.controllers;

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

import com.cristian.simplestore.forms.ProductCreateForm;
import com.cristian.simplestore.forms.TestForm;
import com.cristian.simplestore.services.ImageService;
import com.cristian.simplestore.utils.response.CustomResponse;
;

@RestController
public class TestController {

	private CustomResponse<Object> response = new CustomResponse<Object>();

	@Autowired ImageService imageService;
	
	@PersistenceContext EntityManager entityManager;

	@RequestMapping(value = "/test", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> create(@Valid TestForm form) {
		String some = "some";
		response.content(some);
		return response.build();
	}
	
	@ExceptionHandler(BindException.class)
	public ResponseEntity<Map<String, Object>> handleValidationException(
			BindException ex) {
		
		String some = "some error form";
		response.content(null);
		response.attach("errors", ex.getFieldErrors());
		return response.build();
	}
	

	// @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Data integrity violation")
//	@ExceptionHandler(BindException.class)
//	public Map<String, Object> handleError(HttpServletRequest req, Exception ex) {
//		// logger.error("Request: " + req.getRequestURL() + " raised " + ex);
//		response.attachContent("some error");
//		return response.build();
//	}
}
