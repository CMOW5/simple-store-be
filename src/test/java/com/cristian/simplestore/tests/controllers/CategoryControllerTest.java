package com.cristian.simplestore.tests.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.cristian.simplestore.entities.Category;
import com.cristian.simplestore.respositories.CategoryRepository;
import com.cristian.simplestore.tests.BaseTest;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CategoryControllerTest extends BaseTest {
		
	// private static final Logger log = LoggerFactory.getLogger(SimpleStoreApplication.class);
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
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
	public void testItFindsAllCategories() throws JsonParseException, JsonMappingException, IOException {
		
		int MAX_CATEGORIES_SIZE = 4;
		List<Category> categoriesToSave = new ArrayList<>();
		
		// save a couple of categories on db
		for (int i = 0; i < MAX_CATEGORIES_SIZE; i++) {
			categoriesToSave.add(saveRandomCategoryOnDB());
		}
				
		String jsonResponse = this.restTemplate.getForObject("/api/admin/categories", String.class);		
		
		List<Category> responseCategories = (List<Category>) getContentFromJsonRespose(jsonResponse, List.class);
		assertThat(responseCategories.size()).isEqualTo(MAX_CATEGORIES_SIZE);
		
	}
	
	@Test
	public void testItFindsACategoryById() throws JsonParseException, JsonMappingException, IOException {
		Category category = saveRandomCategoryOnDB(); 
		
		String jsonResponse = this.restTemplate.getForObject("/api/admin/categories/" + category.getId(), String.class);
	    Category foundCategory = (Category) getContentFromJsonRespose(jsonResponse, Category.class);

		assertThat(foundCategory.getName()).isEqualTo(category.getName());
		assertThat(foundCategory.getId()).isEqualTo(category.getId());
	}
	
	@Test
	public void testItCreatesACategory() throws JsonParseException, JsonMappingException, IOException {
		Category category = generateRandomCategory();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add("name", category.getName());
		
		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
		
		// String jsonResponse = this.restTemplate.postForObject("/api/admin/categories", category, String.class);
		// Category createdCategory = (Category) getContentFromJsonRespose(jsonResponse, Category.class);
		String serverUrl = "/api/admin/categories";
		
		// ResponseEntity<String> response = restTemplate.postForEntity(serverUrl, requestEntity, String.class);
		String jsonResponse = this.restTemplate.postForObject(serverUrl, requestEntity, String.class);
		Category createdCategory = (Category) getContentFromJsonRespose(jsonResponse, Category.class);

		assertThat(createdCategory.getName()).isEqualTo(category.getName());
	}
	
	@Test
	public void testItUpdatesACategory() throws JsonParseException, JsonMappingException, IOException {
		Category category = saveRandomCategoryOnDB();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		
		String newName = new Faker().name().firstName();
		
		body.add("name", newName);
		
		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
		
		// String jsonResponse = this.restTemplate.postForObject("/api/admin/categories", category, String.class);
		// Category createdCategory = (Category) getContentFromJsonRespose(jsonResponse, Category.class);
		String serverUrl = "/api/admin/categories/" + category.getId();
		
		// ResponseEntity<String> response = restTemplate.postForEntity(serverUrl, requestEntity, String.class);
		ResponseEntity<String> jsonResponse = this.restTemplate.exchange(serverUrl, HttpMethod.PUT, requestEntity, String.class);

		Category updatedCategory = (Category) getContentFromJsonRespose(jsonResponse.getBody(), Category.class);

		assertThat(updatedCategory.getName()).isEqualTo(newName);
	}
	
	@Test
	public void testItDeletesACategory() throws JsonParseException, JsonMappingException, IOException {
		Category category = saveRandomCategoryOnDB();
		
		this.restTemplate.delete("/api/admin/categories/" + category.getId());
		String jsonResponse = this.restTemplate.getForObject("/api/admin/categories/" + category.getId(), String.class);
		Category deletedCategory  = (Category) getContentFromJsonRespose(jsonResponse, Category.class);

		
		assertThat(deletedCategory).isNull();
	}
	
	public <T> Object getContentFromJsonRespose(String jsonResponse, Class<T> classType) throws JsonParseException, JsonMappingException, IOException {	
	    ObjectMapper mapper = new ObjectMapper();
	    Map mapResponse = mapper.readValue(jsonResponse, Map.class);
	    
	    T content = mapper.convertValue(mapResponse.get("content"), classType);
	    return content;
	}
}
