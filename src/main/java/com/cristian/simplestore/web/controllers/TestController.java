package com.cristian.simplestore.web.controllers;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cristian.simplestore.business.services.ImageService;
import com.cristian.simplestore.web.forms.TestForm;
import com.cristian.simplestore.web.utils.response.ApiResponse;


@RestController
public class TestController {

	private ApiResponse response = new ApiResponse();

	@Autowired ImageService imageService;
	
	@PersistenceContext EntityManager entityManager;

	@RequestMapping(value = "/test", method = RequestMethod.POST)
	public ResponseEntity<?> create(@Valid TestForm form) {
		String some = "some";
		response.content(some);
		return response.build();
	}
}
