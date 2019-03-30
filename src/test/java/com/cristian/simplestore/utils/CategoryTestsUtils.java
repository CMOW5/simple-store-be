package com.cristian.simplestore.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
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
		String name = generateRandomName();
		Category category = new Category();
		category.setName(name);
		return category;
	}
	
	public CategoryCreateForm generateRandomCategoryCreateForm() {
		CategoryCreateForm form = new CategoryCreateForm();
		
		String name = generateRandomName();
		Category parentCategory = saveRandomCategoryOnDb();
		MultipartFile image = imageBuilder.createMockMultipartImage();
		
		form.setName(name);
		form.setParentCategory(parentCategory);
		form.setImage(image);
		
		return form;
	}
	
	public CategoryUpdateForm generateRandomCategoryUpdateForm(Long id) {
		CategoryUpdateForm form = new CategoryUpdateForm();
		
		String name = generateRandomName();
		Category parentCategory = saveRandomCategoryOnDb();
		MultipartFile newImage = imageBuilder.createMockMultipartImage();
		
		form.setId(id);
		form.setName(name);
		form.setParentCategory(parentCategory);
		form.setNewImage(newImage);
		
		return form;
	}
	
	public FormBuilder generateRandomCategoryCreateRequesForm() {
		String name = generateRandomName();
		Category parentCategory = saveRandomCategoryOnDb();
		Resource image = imageUtils.storeImageOnDisk();
		
		FormBuilder form = new FormBuilder();
			form.add("name", name)
			.add("parentCategory", parentCategory.getId())
			.add("image", image);
			
		return form;
	}
	
	public FormBuilder generateRandomCategoryUpdateRequesForm() {
		String name = generateRandomName();
		Category parentCategory = saveRandomCategoryOnDb();
		Resource image = imageUtils.storeImageOnDisk();
		
		FormBuilder form = new FormBuilder();
			form.add("name", name)
			.add("parentCategory", parentCategory.getId())
			.add("newImage", image);
			
		return form;
	}
	
	/**
	 * stores a random category into the database
	 * @return the newly created category
	 */
	public Category saveRandomCategoryOnDb() {
		String name = faker.name().firstName();
		Image image = imageUtils.saveRandomImageOnDb();
		
		Category category = new Category();
		category.setName(name);
		category.addImage(image);
		
		return categoryRepository.save(category);
	}
	
	/**
	 * stores a random category into the database
	 * @return the newly created category
	 */
	public Category saveRandomCategoryOnDbWithParent() {
		Category category = saveRandomCategoryOnDb();
		Category parentCategory = saveRandomCategoryOnDb();
		category.setParentCategory(parentCategory);		
		return categoryRepository.save(category);
	}
	
	/**
	 * stores a couple of random categories on the database
	 * @param numberOfCategories
	 * @return the newly created categories
	 */
	public List<Category> saveRandomCategoriesOnDb(long numberOfCategories) {
		List<Category> savedCategories = new ArrayList<Category>();
		
		for (int i = 0; i < numberOfCategories; i++) {
			savedCategories.add(saveRandomCategoryOnDb());
		}
		
		return savedCategories;
	}
	
	private String generateRandomName() {
		return faker.name().firstName();
	}
}
