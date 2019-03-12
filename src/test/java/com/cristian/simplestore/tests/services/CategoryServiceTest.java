package com.cristian.simplestore.tests.services;

import static org.assertj.core.api.Assertions.assertThat;

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
import com.cristian.simplestore.tests.utils.CategoryTestsUtils;
import com.github.javafaker.Faker;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceTest extends BaseTest {
	
	@Autowired 
	CategoryService categoryService;
	
	@Autowired
	CategoryTestsUtils utils;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	
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
		long CATEGORIES_SIZE = 4;
		utils.saveRandomCategoriesOnDB(CATEGORIES_SIZE);
		
		List<Category> foundCategories = this.categoryService.findAllCategories();
		
		assertThat(foundCategories.size()).isEqualTo(CATEGORIES_SIZE);
	}
	 
	@Test
	public void findCategoryById() {
		Category category = utils.saveRandomCategoryOnDB();
		
		Category foundCategory = this.categoryService.findCategoryById(category.getId());
		assertThat(foundCategory.getId()).isEqualTo(category.getId());
	}
	
	@Test
	public void create() {
		Category category = utils.generateRandomCategory();
		
		Category savedCategory = this.categoryService.create(category);
		
		assertThat(savedCategory.getId()).isEqualTo(category.getId());
		assertThat(savedCategory.getName()).isEqualTo(category.getName());
	}
	
	@Test 
	public void update() {
		Category categoryToUpdate = utils.saveRandomCategoryOnDB();
		
		String newName = new Faker().commerce().department();
		categoryToUpdate.setName(newName);
		Category updatedCategory = this.categoryService.update(categoryToUpdate.getId(), categoryToUpdate);
		
		assertThat(updatedCategory.getId()).isEqualTo(categoryToUpdate.getId());
		assertThat(updatedCategory.getName()).isEqualTo(categoryToUpdate.getName());
	}
	 
	@Test
	public void delete() {
		Category categoryToDelete = utils.saveRandomCategoryOnDB();
		
		this.categoryService.deleteById(categoryToDelete.getId());
		Category deletedCategory = 
				this.categoryService.findCategoryById(categoryToDelete.getId());
		
		assertThat(deletedCategory).isNull();
	}
	

}
