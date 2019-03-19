package com.cristian.simplestore.tests.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cristian.simplestore.business.services.CategoryService;
import com.cristian.simplestore.persistence.entities.Category;
import com.cristian.simplestore.persistence.respositories.CategoryRepository;
import com.cristian.simplestore.tests.BaseTest;
import com.cristian.simplestore.tests.utils.CategoryTestsUtils;
import com.cristian.simplestore.web.forms.CategoryCreateForm;
import com.cristian.simplestore.web.forms.CategoryUpdateForm;

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
		
		List<Category> foundCategories = this.categoryService.findAll();
		
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
		CategoryCreateForm form = utils.generateRandomCategoryCreateForm();
		
		Category savedCategory = this.categoryService.create(form);
		
		assertThat(savedCategory.getName()).isEqualTo(form.getName());
	}
	
	@Test 
	public void update() {
		Category categoryToUpdate = utils.saveRandomCategoryOnDB();
		CategoryUpdateForm newCategoryData = utils.generateRandomCategoryUpdateForm();
		newCategoryData.setId(categoryToUpdate.getId());
		
		Category updatedCategory = this.categoryService.update(newCategoryData);
		
		assertThat(updatedCategory.getId()).isEqualTo(newCategoryData.getId());
		assertThat(updatedCategory.getName()).isEqualTo(newCategoryData.getName());
	}
	 
	@Test(expected = EntityNotFoundException.class)
	public void delete() {
		Category categoryToDelete = utils.saveRandomCategoryOnDB();
		
		this.categoryService.deleteById(categoryToDelete.getId());
		this.categoryService.findCategoryById(categoryToDelete.getId());
	}
	

}
