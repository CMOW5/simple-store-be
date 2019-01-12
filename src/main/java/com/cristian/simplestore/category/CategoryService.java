package com.cristian.simplestore.category;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
	
	 @Autowired
	 private CategoryRepository categoryRepository;
	 
	 public List<Category> findAllCategories() {
		 List<Category> foundCategories = new ArrayList<Category>();
		 this.categoryRepository.findAll().forEach(foundCategories::add);
		 return foundCategories;
	 }
	 
	 public Category findCategoryById(long id) {
		 Optional<Category> foundCategory = this.categoryRepository.findById(id);
		 return foundCategory.isPresent() ? foundCategory.get() : null;
	 }
	 
	 public Category create(Category category) {
		 return this.categoryRepository.save(category);
	 }
	 
	 public Category update(long id, Category category) {
		 category.setId(id);
		 return this.categoryRepository.save(category);
	 }
	 
	 public void delete(long id) {
		 this.categoryRepository.deleteById(id);
	 }
	 
	 public long count() {
		 return this.categoryRepository.count();
	 }
}
