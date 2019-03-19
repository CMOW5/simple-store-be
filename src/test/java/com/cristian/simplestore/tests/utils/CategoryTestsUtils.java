package com.cristian.simplestore.tests.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cristian.simplestore.persistence.entities.Category;
import com.cristian.simplestore.persistence.respositories.CategoryRepository;
import com.cristian.simplestore.web.forms.CategoryCreateForm;
import com.cristian.simplestore.web.forms.CategoryUpdateForm;
import com.github.javafaker.Faker;

@Component
public class CategoryTestsUtils {
	
	@Autowired 
	private CategoryRepository categoryRepository;
	
	private Faker faker = new Faker();
	
	/**
	 * create a category instance with random data
	 * @return the newly created category
	 */
	public Category generateRandomCategory() {
		String name = faker.name().firstName();
		Category category = new Category();
		category.setName(name);
		return category;
	}
	
	public CategoryCreateForm generateRandomCategoryCreateForm() {
		CategoryCreateForm form = new CategoryCreateForm();
		
		String name = faker.name().firstName();
		Category parentCategory = null;
		form.setName(name);
		form.setParentCategory(parentCategory);
		// form.setImage(image);
		return form;
	}
	
	public CategoryUpdateForm generateRandomCategoryUpdateForm() {
		CategoryUpdateForm form = new CategoryUpdateForm();
		
		String name = faker.name().firstName();
		Category parentCategory = null;
		form.setName(name);
		form.setParentCategory(parentCategory);
		// form.setImage(image);
		return form;
	}
	
	/**
	 * stores a random category into the database
	 * @return the newly created category
	 */
	public Category saveRandomCategoryOnDB() {
		String name = faker.name().firstName();
		Category category = new Category();
		category.setName(name);
		return categoryRepository.save(category);
	}
	
	/**
	 * stores a couple of random categories on the database
	 * @param numberOfCategories
	 * @return the newly created categories
	 */
	public List<Category> saveRandomCategoriesOnDB(long numberOfCategories) {
		List<Category> savedCategories = new ArrayList<Category>();
		
		for (int i = 0; i < numberOfCategories; i++) {
			savedCategories.add(saveRandomCategoryOnDB());
		}
		
		return savedCategories;
	}
}
