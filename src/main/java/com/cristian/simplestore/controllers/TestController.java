package com.cristian.simplestore.controllers;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cristian.simplestore.response.CustomResponse;
import com.cristian.simplestore.validators.CategoryCreateValidator;
import com.cristian.simplestore.category.Category;
import com.cristian.simplestore.form.CategoryCreateForm;
import com.cristian.simplestore.form.CategoryUpdateForm;
import com.cristian.simplestore.form.NewUserForm;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TestController {

	private CustomResponse response = new CustomResponse();

	// BindException

	@RequestMapping(value = "/test/{id}", method = RequestMethod.PUT)
	public Map<String, Object> create(@PathVariable Long id, @Valid CategoryUpdateForm form, MultipartFile image,
			// @Valid NewUserForm newUserForm,

			// HttpServletRequest request,
			BindingResult validationResult) {

		String some = "some";

		if (validationResult.hasErrors()) {
			String a = "dsds";
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
