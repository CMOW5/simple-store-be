package com.cristian.simplestore.category;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cristian.simplestore.response.CustomResponse;

@RestController
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
	public Map<String, Object> create(@RequestBody Category category) {
		Category createdCategory = categoryService.create(category);
		
		response.attachContent(createdCategory);
		return response.build();
	}
	
	@RequestMapping(value = "/api/admin/categories/{id}", method = RequestMethod.PUT)
	public Map<String, Object> update(@PathVariable long id, @RequestBody Category category) {
		Category updatedCategory = categoryService.update(id, category);
		
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
