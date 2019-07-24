package com.cristian.simplestore.utils.category;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cristian.simplestore.domain.models.Category;
import com.cristian.simplestore.domain.models.Image;
import com.cristian.simplestore.domain.ports.repository.CategoryRepository;
import com.cristian.simplestore.domain.services.image.CreateImageService;
import com.cristian.simplestore.utils.image.MockImageFileFactory;
import com.github.javafaker.Faker;

@Component
public class CategoryGenerator {
	
	private Faker faker = new Faker();
	private CategoryRepository categoryRepository;
	private CreateImageService createImageService;

	@Autowired
	private CategoryGenerator(CategoryRepository categoryRepository, CreateImageService createImageService) {
		this.categoryRepository = categoryRepository;
		this.createImageService = createImageService;
	}
	
	public Category generateRandomCategory() {
		return new Builder().randomName().randomImage().randomParent().build();
		// return new Builder().randomName().randomImage().build();
	}
	
	public String generateRandomCategoryName() {
		return faker.name().name();
	}
	
	public Category saveRandomCategoryOnDb() {
		return new Builder().randomName().randomImage().save();
	}
	
	public List<Category> saveRandomCategoriesOnDb(int size) {
		List<Category> categories = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			categories.add(saveRandomCategoryOnDb());
		}
		return categories;
	}
	
	public class Builder {
		String name;
		Image image;
		Category parent;
		
		public Builder randomName() {
			this.name = generateRandomCategoryName();
			return this;
		}
		
		public Builder name(String name) {
			this.name = name;
			return this;
		}
		
		public Builder randomImage() {
			this.image = createImageService.create(MockImageFileFactory.createMockMultiPartFile());
			return this;
		}
		
		public Builder image(Image image) {
			this.image = image;
			return this;
		}
		
		public Builder randomParent() {
			this.parent = saveRandomCategoryOnDb();
			return this;
		}
		
		public Builder parent(Category parent) {
			this.parent = parent;
			return this;
		}
		
		public Category build() {
			return new Category(name, image, parent);
		}
		
		public Category save() {
			return categoryRepository.save(build());
		}
	}
}
