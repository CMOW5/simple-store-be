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
import com.cristian.simplestore.utils.response.CustomResponse;
;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TestController {

	private CustomResponse response = new CustomResponse();

	@Autowired ImageService imageService;
	
	@PersistenceContext EntityManager entityManager;

	@RequestMapping(value = "/test", method = RequestMethod.POST)
	// @Transactional
	public Map<String, Object> create(
			// @PathVariable Long id, 
			@Valid ProductCreateForm form 
			// BindingResult validationResult
			) {
		
		
		
		String some = "some";


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
