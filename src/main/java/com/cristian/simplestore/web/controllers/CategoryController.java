package com.cristian.simplestore.web.controllers;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cristian.simplestore.business.services.CategoryService;
import com.cristian.simplestore.persistence.entities.Category;
import com.cristian.simplestore.web.forms.CategoryCreateForm;
import com.cristian.simplestore.web.forms.CategoryUpdateForm;
import com.cristian.simplestore.web.utils.response.ApiResponse;


@RestController
@RequestMapping("/api/admin/categories")
public class CategoryController {
	
	private ApiResponse response = new ApiResponse();
	
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping
	public ResponseEntity<?> findAllCategories() {
		List<Category> categories = categoryService.findAll();
		return response.status(HttpStatus.OK)
					.content(categories)
					.build();
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> findCategoryById(@PathVariable long id) {
		Category foundCategory = categoryService.findCategoryById(id);
		return response.status(HttpStatus.OK)
				.content(foundCategory)
				.build();
	}
	
	@PostMapping
	public ResponseEntity<?> create(
			@Valid CategoryCreateForm form) {
		Category createdCategory = categoryService.create(form);
		return response.status(HttpStatus.CREATED)
					.content(createdCategory)
					.build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> update(
			@Valid CategoryUpdateForm form) {
		Category updatedCategory = categoryService.update(form);
		return response.status(HttpStatus.OK)
				.content(updatedCategory)
				.build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		categoryService.deleteById(id);
		return response.status(HttpStatus.NO_CONTENT)
				.content(null)
				.build();
	}
	
	@GetMapping("/count")
	public ResponseEntity<?> count() {
		long categoriesCount = categoryService.count();
		return response.status(HttpStatus.OK)
				.content(categoriesCount)
				.build();	
	}
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<?> handleEntityNotFoundException(
			EntityNotFoundException exception) {
		return response.status(HttpStatus.NOT_FOUND)
				.content(null)
				.build();
	}	
}
