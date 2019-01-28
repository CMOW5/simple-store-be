package com.cristian.simplestore.category;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cristian.simplestore.response.CustomResponse;

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
	public Map<String, Object> create(Category category, @RequestParam("image") MultipartFile image) {
		Category createdCategory = categoryService.create(category, image);
		
		response.attachContent(createdCategory);
		return response.build();
	}
	
	@CrossOrigin
	@RequestMapping(value = "/api/admin/categories/{id}", method = RequestMethod.PUT)
	public Map<String, Object> update(@PathVariable long id, 
			Category category, @RequestParam(required = false) Long imageIdToDelete, 
			@RequestParam(required = false) MultipartFile image) {
		Category updatedCategory = categoryService.update(id, category, image, imageIdToDelete);
		
		response.attachContent(updatedCategory);
		return response.build();
	}
	
	@RequestMapping(value = "/api/admin/categories/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable long id) {
		categoryService.delete(id);
	}
	
	@RequestMapping(value = "/api/admin/categories/count", method = RequestMethod.GET)
	public Map<String, Object> count() {
		long categoriesCount = categoryService.count();
		
		response.attachContent(categoriesCount);
		return response.build();	
	}
	
}
