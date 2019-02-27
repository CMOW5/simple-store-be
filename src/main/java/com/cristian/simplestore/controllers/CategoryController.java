package com.cristian.simplestore.controllers;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cristian.simplestore.entities.Category;
import com.cristian.simplestore.forms.CategoryCreateForm;
import com.cristian.simplestore.forms.CategoryUpdateForm;
import com.cristian.simplestore.services.CategoryService;
import com.cristian.simplestore.utils.response.CustomResponse;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoryController {
	
	private CustomResponse response = new CustomResponse();
	
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping(value = "/api/admin/categories", method = RequestMethod.GET)
	public Map<String, Object> findAllCategories() {
		List<Category> categories = categoryService.findAllCategories();
		
		response.attachContent(categories);
		return response.build();
	}
	
	@RequestMapping(value = "/api/admin/categories/{id}", method = RequestMethod.GET)
	public Map<String, Object> findCategoryById(@PathVariable long id) {
		Category category = categoryService.findCategoryById(id);
		
		response.attachContent(category);
		return response.build();
	}
	
	@RequestMapping(value = "/api/admin/categories", method = RequestMethod.POST)
	public Map<String, Object> create(
			CategoryCreateForm form, 
			@RequestParam(required = false) MultipartFile image,
			BindingResult validationResult) {
		
		if (validationResult.hasErrors()) {
            return response.attachContent(validationResult.getFieldErrors()).build();
        }
		
		Category createdCategory = categoryService.create(form.getModel(), image);
		
		response.attachContent(createdCategory);
		return response.build();
	}
	
	@RequestMapping(value = "/api/admin/categories/{id}", method = RequestMethod.PUT)
	public Map<String, Object> update(
			@PathVariable Long id, 
			@RequestParam(required = false) MultipartFile newImage,
			@RequestParam(required = false) Long imageIdToDelete,
			@Valid CategoryUpdateForm form, 
			BindingResult validationResult) {

		if (validationResult.hasErrors()) {
            return response.attachContent(validationResult.getFieldErrors()).build();
        }
		
		Category updatedCategory = categoryService.update(id, form.getModel(), newImage, imageIdToDelete);
		
		response.attachContent(updatedCategory);
		return response.build();
	}
	
	@RequestMapping(value = "/api/admin/categories/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable Long id) {
		categoryService.deleteById(id);
	}
	
	@RequestMapping(value = "/api/admin/categories/count", method = RequestMethod.GET)
	public Map<String, Object> count() {
		long categoriesCount = categoryService.count();
		
		response.attachContent(categoriesCount);
		return response.build();	
	}
	
  @ResponseStatus(
		  value=HttpStatus.INTERNAL_SERVER_ERROR,
          reason="Data integrity violation") 
  @ExceptionHandler(Exception.class)
  public Map<String, Object> handleError(HttpServletRequest req, Exception ex) {
    // logger.error("Request: " + req.getRequestURL() + " raised " + ex);
	response.attachContent(ex.getMessage());
    return response.build();
  }
	
}
