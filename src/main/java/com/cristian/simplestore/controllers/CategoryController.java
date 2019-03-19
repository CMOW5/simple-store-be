package com.cristian.simplestore.controllers;

import java.util.List;
import java.util.Map;

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

import com.cristian.simplestore.entities.Category;
import com.cristian.simplestore.forms.CategoryCreateForm;
import com.cristian.simplestore.forms.CategoryUpdateForm;
import com.cristian.simplestore.services.CategoryService;
import com.cristian.simplestore.utils.response.CustomResponse;

@RestController
@RequestMapping("/api/admin/categories")
public class CategoryController {
	
	private CustomResponse<Object> response = new CustomResponse<>();
	
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping
	public ResponseEntity<Map<String, Object>> findAllCategories() {
		List<Category> categories = categoryService.findAll();
		return response.status(HttpStatus.OK)
					.content(categories)
					.build();
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Map<String, Object>> findCategoryById(@PathVariable long id) {
		Category foundCategory = categoryService.findCategoryById(id);
		return response.status(HttpStatus.OK)
				.content(foundCategory)
				.build();
	}
	
	@PostMapping
	public ResponseEntity<Map<String, Object>> create(
			@Valid CategoryCreateForm form) {
		Category createdCategory = categoryService.create(form);
		return response.status(HttpStatus.CREATED)
					.content(createdCategory)
					.build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Map<String, Object>> update(
			@Valid CategoryUpdateForm form) {
		Category updatedCategory = categoryService.update(form);
		return response.status(HttpStatus.OK)
				.content(updatedCategory)
				.build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {
		categoryService.deleteById(id);
		return response.status(HttpStatus.NO_CONTENT)
				.content(null)
				.build();
	}
	
	@GetMapping("/count")
	public ResponseEntity<Map<String, Object>> count() {
		long categoriesCount = categoryService.count();
		return response.status(HttpStatus.OK)
				.content(categoriesCount)
				.build();	
	}
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<Map<String, Object>> handleEntityNotFoundException(
			EntityNotFoundException ex) {
		return response.status(HttpStatus.NOT_FOUND)
				.content(null)
				.build();
	}
	
//	@ExceptionHandler(BindException.class)
//	public ResponseEntity<Map<String, Object>> handleValidationException(
//			BindException ex) {
//		
//
//		return response.status(HttpStatus.NOT_FOUND)
//				.content(ex.getFieldErrors())
//				.build();
//	}
//	
	
	// org.springframework.validation.BindException
	
//  @ResponseStatus(
//		  value=HttpStatus.INTERNAL_SERVER_ERROR,
//          reason="Data integrity violation") 
//  @ExceptionHandler(Exception.class)
//  public Map<String, Object> handleError(HttpServletRequest req, Exception ex) {
//    // logger.error("Request: " + req.getRequestURL() + " raised " + ex);
//	response.attachContent(ex.getMessage());
//    return response.build();
//  }
	
}
