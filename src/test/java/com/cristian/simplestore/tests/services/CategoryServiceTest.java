package com.cristian.simplestore.tests.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cristian.simplestore.entities.Category;
import com.cristian.simplestore.respositories.CategoryRepository;
import com.cristian.simplestore.services.CategoryService;
import com.cristian.simplestore.tests.BaseTest;
import com.github.javafaker.Faker;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceTest extends BaseTest {
	
	@Autowired 
	CategoryService categoryService;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	/**
	 * create a category instance with random data
	 * @return the newly created category
	 */
	public Category generateRandomCategory() {
		Faker faker = new Faker();
		String name = faker.name().firstName();
		return new Category(name);
	}
	
	/**
	 * stores a random category into the database
	 * @return the newly created category
	 */
	public Category saveRandomCategoryOnDB() {
		Faker faker = new Faker();
		String name = faker.name().firstName();
		return categoryRepository.save(new Category(name));
	}
	
	@Before
	public void setUp() {
		categoryRepository.deleteAll();
	}
	
	@After
    public void tearDown() {
		categoryRepository.deleteAll();
    }
	
	@Test
	public void findAllCategories() {
		// create a couple of categories on db
		int MAX_CATEGORIES_SIZE = 4;
		List<Category> createdCategories = new ArrayList<Category>();
		for (int i = 0; i < MAX_CATEGORIES_SIZE; i++) {
			createdCategories.add(saveRandomCategoryOnDB());
		}
		
		List<Category> foundCategories = this.categoryService.findAllCategories();
		assertThat(foundCategories.size()).isEqualTo(MAX_CATEGORIES_SIZE);
	}
	 
	@Test
	public void findCategoryById() {
		Category category = saveRandomCategoryOnDB();
		
		Category foundCategory = this.categoryService.findCategoryById(category.getId());
		assertThat(foundCategory.getId()).isEqualTo(category.getId());
	}
	
	@Test
	public void create() {
		// TODO: check whether the new category name is already on db
		Category category = generateRandomCategory();
		Category savedCategory = this.categoryService.create(category);
		assertThat(savedCategory.getId()).isEqualTo(category.getId());
		assertThat(savedCategory.getName()).isEqualTo(category.getName());
	}
	
	@Test 
	public void update() {
		// TODO: check whether the new category name is already on db
		Category categoryToUpdate = saveRandomCategoryOnDB();
		String name = new Faker().commerce().department();
		Category category = new Category(name);
		categoryToUpdate.setName(category.getName());
		
		Category updatedCategory = this.categoryService.update(categoryToUpdate.getId(), categoryToUpdate);
		assertThat(updatedCategory.getId()).isEqualTo(categoryToUpdate.getId());
		assertThat(updatedCategory.getName()).isEqualTo(categoryToUpdate.getName());
	}
	 
	@Test
	public void delete() {
		Category categoryToDelete = saveRandomCategoryOnDB();
		
		this.categoryService.deleteById(categoryToDelete.getId());
		Category deletedCategory = 
				this.categoryService.findCategoryById(categoryToDelete.getId());
		assertThat(deletedCategory).isNull();
	}
	

}
