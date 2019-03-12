package com.cristian.simplestore.tests.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cristian.simplestore.entities.Category;
import com.cristian.simplestore.respositories.CategoryRepository;
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
