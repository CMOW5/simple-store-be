package com.cristian.simplestore.utils.category;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cristian.simplestore.persistence.entities.CategoryEntity;
import com.cristian.simplestore.persistence.entities.ImageEntity;
import com.cristian.simplestore.persistence.repositories.CategoryRepository;
import com.cristian.simplestore.utils.image.ImageTestsUtils;
import com.github.javafaker.Faker;

@Component
public class CategoryGenerator {
	
	private Faker faker = new Faker();
	private ImageTestsUtils imageUtils;
	private CategoryRepository categoryRepository;

	@Autowired
	private CategoryGenerator(CategoryRepository categoryRepository, ImageTestsUtils imageUtils) {
		this.categoryRepository = categoryRepository;
		this.imageUtils = imageUtils;
	}
	
	public CategoryEntity generateRandomCategory() {
		return new Builder().randomName().randomImage().randomParent().build();
	}
	
	public String generateRandomCategoryName() {
		return faker.name().name();
	}
	
	public CategoryEntity saveRandomCategoryOnDb() {
		return new Builder().randomName().randomImage().save();
	}
	
	public List<CategoryEntity> saveRandomCategoriesOnDb(int size) {
		List<CategoryEntity> categories = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			categories.add(saveRandomCategoryOnDb());
		}
		return categories;
	}
	
	public class Builder {
		String name;
		ImageEntity image;
		CategoryEntity parent;
		
		public Builder randomName() {
			this.name = generateRandomCategoryName();
			return this;
		}
		
		public Builder name(String name) {
			this.name = name;
			return this;
		}
		
		public Builder randomImage() {
			this.image = imageUtils.saveRandomImageOnDb();
			return this;
		}
		
		public Builder image(ImageEntity image) {
			this.image = image;
			return this;
		}
		
		public Builder randomParent() {
			this.parent = saveRandomCategoryOnDb();
			return this;
		}
		
		public Builder parent(CategoryEntity parent) {
			this.parent = parent;
			return this;
		}
		
		public CategoryEntity build() {
			return new CategoryEntity(name, image, parent);
		}
		
		public CategoryEntity save() {
			return categoryRepository.save(build());
		}
	}
}
