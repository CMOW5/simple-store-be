package com.cristian.simplestore.controllers;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cristian.simplestore.forms.CategoryCreateForm;
import com.cristian.simplestore.forms.CategoryUpdateForm;
import com.cristian.simplestore.utils.response.CustomResponse;
;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TestController {

	private CustomResponse response = new CustomResponse();

	// BindException

	@RequestMapping(value = "/test", method = RequestMethod.POST)
	public Map<String, Object> create(
			// @PathVariable Long id, 
			@Valid CategoryUpdateForm form, 
			MultipartFile image,
			// HttpServletRequest request,
			BindingResult validationResult) {

		String some = "some";

		if (validationResult.hasErrors()) {
			some = "dsds";
		}
		// Category category = categoryForm.getModel();

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
