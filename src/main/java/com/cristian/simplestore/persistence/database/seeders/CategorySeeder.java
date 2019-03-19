package com.cristian.simplestore.persistence.database.seeders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.cristian.simplestore.persistence.entities.Category;
import com.cristian.simplestore.persistence.entities.Image;
import com.cristian.simplestore.persistence.respositories.CategoryRepository;
import com.cristian.simplestore.persistence.respositories.ImageRepository;
import com.github.javafaker.Faker;

@Service
public class CategorySeeder {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ImageRepository imageRepository;
	
	private Faker faker;
	
	public static final long MAX_CATEGORIES = 10; 
	
	public CategorySeeder() {
		faker = new Faker();
	}
	
	public void seed() {
		for (int i = 0; i < MAX_CATEGORIES; i++) {
			try {
				Category category = createRandomCategoryOnDB();
				this.createRandomCategoriesWithParentOnDB(category, 4);
			} catch (DataIntegrityViolationException e) {
				continue;
			}
			
		}
	}
	
	private Category createRandomCategoryOnDB() {
		Category category = new Category();
		category.setName(faker.commerce().department());
		category.addImage(generateRandomImageOnDB());
		categoryRepository.save(category);
		return category;
	}
	
	private void createRandomCategoriesWithParentOnDB(Category parentCategory, long categoriesSize) {
		for (int i = 0; i < categoriesSize; i++) {
			Category category1 = new Category();
			category1.setName(this.faker.commerce().department());
			category1.setParentCategory(parentCategory);
			category1.addImage(generateRandomImageOnDB());
			categoryRepository.save(category1);
			
			Category category2 = new Category();
			category2.setName(this.faker.commerce().department());
			category2.setParentCategory(category1);
			category2.addImage(generateRandomImageOnDB());
			categoryRepository.save(category2);
		}
	}
	
	private Image generateRandomImageOnDB() {
		String baseUrl = "https://picsum.photos/";
		int width = this.faker.number().numberBetween(200, 800);
		int height = this.faker.number().numberBetween(200, 800);
		
		String imageName = baseUrl + width + "/" + height;
		
		Image image = new Image();
		image.setName(imageName);
		imageRepository.save(image);
		return image;
	}
	
}
