package com.cristian.simplestore.controllers;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/admin/categories")
public class CategoryController {
	
	private CustomResponse response = new CustomResponse();
	
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping
	public Map<String, Object> findAllCategories() {
		List<Category> categories = categoryService.findAllCategories();
		
		response.attachContent(categories);
		return response.build();
	}
	
	@GetMapping(value = "/{id}")
	public Map<String, Object> findCategoryById(@PathVariable long id) {
		Category foundCategory = categoryService.findCategoryById(id);
		
		response.attachContent(foundCategory);
		return response.build();
	}
	
	@PostMapping
	public Map<String, Object> create(
			@Valid CategoryCreateForm form) {
		Category createdCategory = categoryService.create(form);
		
		response.attachContent(createdCategory);
		return response.build();
	}
	
	@PutMapping("/{id}")
	public Map<String, Object> update(
			@Valid CategoryUpdateForm form) {
		Category updatedCategory = categoryService.update(form);
		
		response.attachContent(updatedCategory);
		return response.build();
	}
	
	@DeleteMapping("/{id}")
	public Map<String, Object> delete(@PathVariable Long id) {
		categoryService.deleteById(id);
		
		response.attachContent(null);
		return response.build();
	}
	
	@GetMapping("/count")
	public Map<String, Object> count() {
		long categoriesCount = categoryService.count();
		
		response.attachContent(categoriesCount);
		return response.build();	
	}
	
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
