package com.cristian.simplestore.controllers;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cristian.simplestore.forms.ProductCreateForm;
import com.cristian.simplestore.services.ImageService;
import com.cristian.simplestore.utils.response._CustomResponse;
;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TestController {

	private _CustomResponse response = new _CustomResponse();

	@Autowired ImageService imageService;
	
	@PersistenceContext EntityManager entityManager;

	@RequestMapping(value = "/test", method = RequestMethod.GET
				, consumes = "application/json")
	public Map<String, Object> create() {
		
		
		
		String some = "some";
		response.attachContent(some);
		return response.build();
	}
	
	@RequestMapping(value = "/test", method = RequestMethod.GET
			, consumes = "multipart/form-data")
	public Map<String, Object> create2() {
		
		
		String some = "some 2";
		response.attachContent(some);
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
