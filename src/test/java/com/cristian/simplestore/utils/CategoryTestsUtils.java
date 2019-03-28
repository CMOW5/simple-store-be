package com.cristian.simplestore.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.cristian.simplestore.persistence.entities.Category;
import com.cristian.simplestore.persistence.entities.Image;
import com.cristian.simplestore.persistence.respositories.CategoryRepository;
import com.cristian.simplestore.web.forms.CategoryCreateForm;
import com.cristian.simplestore.web.forms.CategoryUpdateForm;
import com.github.javafaker.Faker;

@Component
public class CategoryTestsUtils {
	
	@Autowired 
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ImageBuilder imageBuilder;
	
	@Autowired
	private ImageTestsUtils imageUtils;
	
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
		Category parentCategory = this.saveRandomCategoryOnDB();
		MultipartFile image = this.imageBuilder.createMockMultipartImage();
		
		form.setName(name);
		form.setParentCategory(parentCategory);
		form.setImage(image);
		
		return form;
	}
	
	public CategoryUpdateForm generateRandomCategoryUpdateForm(Long id) {
		CategoryUpdateForm form = new CategoryUpdateForm();
		
		String name = faker.name().firstName();
		Category parentCategory = this.saveRandomCategoryOnDB();
		MultipartFile newImage = this.imageBuilder.createMockMultipartImage();
		
		form.setId(id);
		form.setName(name);
		form.setParentCategory(parentCategory);
		form.setNewImage(newImage);
		
		return form;
	}
	
	/**
	 * stores a random category into the database
	 * @return the newly created category
	 */
	public Category saveRandomCategoryOnDB() {
		String name = faker.name().firstName();
		Image image = imageUtils.saveRandomImageOnDb();
		
		Category category = new Category();
		category.setName(name);
		category.addImage(image);
		
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
