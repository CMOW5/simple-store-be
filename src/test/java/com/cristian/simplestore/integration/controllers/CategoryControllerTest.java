package com.cristian.simplestore.integration.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.cristian.simplestore.persistence.entities.Category;
import com.cristian.simplestore.persistence.respositories.CategoryRepository;
import com.cristian.simplestore.BaseTest;
import com.cristian.simplestore.utils.CategoryTestsUtils;
import com.cristian.simplestore.utils.FormBuilder;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CategoryControllerTest extends BaseTest {
			
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private CategoryTestsUtils utils;
	
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
		long MAX_CATEGORIES_SIZE = 4;
		utils.saveRandomCategoriesOnDB(MAX_CATEGORIES_SIZE);
		
		ResponseEntity<String> response = sendFindAllCategoriesRequest();
		
		List<Category> responseCategories = (List<Category>) getContentFromJsonRespose(response.getBody(), List.class);
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseCategories.size()).isEqualTo(MAX_CATEGORIES_SIZE);	
	}
	
	@Test
	public void testItFindsACategoryById() throws JsonParseException, JsonMappingException, IOException {
		Category category = utils.saveRandomCategoryOnDB(); 
		
		ResponseEntity<String> response = sendFindCategoryByIdRequest(category.getId());
		
	    Category foundCategory = (Category) getContentFromJsonRespose(response.getBody(), Category.class);

	    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(foundCategory.getName()).isEqualTo(category.getName());
		assertThat(foundCategory.getId()).isEqualTo(category.getId());
	}
	
	@Test
	public void testItDoesNotFindACategoryById() throws JsonParseException, JsonMappingException, IOException {
		ResponseEntity<String> response = sendFindCategoryByIdRequest(new Long(1));
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}
		
	@Test
	public void testItCreatesACategory() throws JsonParseException, JsonMappingException, IOException {
		Category category = utils.generateRandomCategory();
		
		FormBuilder form = new FormBuilder();
		form.add("name", category.getName());
			// .add("parentCategory", parentCategoryId);
			// .add("image", "some image");
		
		
		ResponseEntity<String> response = sendCategoryCreateRequest(form);
		Category createdCategory = (Category) getContentFromJsonRespose(response.getBody(), Category.class);
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(createdCategory.getName()).isEqualTo(category.getName());
	}
	
	@Test
	public void testItUpdatesACategory() throws JsonParseException, JsonMappingException, IOException {
		Category category = utils.saveRandomCategoryOnDB();
		
		String newName = new Faker().name().firstName();
		
		FormBuilder form = new FormBuilder();
		form.add("name", newName);
			// .add("parentCategory", parentCategoryId);
			// .add("image", "some image");
		
		ResponseEntity<String> response = sendCategoryUpdateRequest(category.getId(), form);
		Category updatedCategory = (Category) getContentFromJsonRespose(response.getBody(), Category.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(updatedCategory.getName()).isEqualTo(newName);
	}
	
	// @Test
	public void testItCorrectlyUpdatesTheParentCategory() throws JsonParseException, JsonMappingException, IOException {
		Category category = utils.saveRandomCategoryOnDB();
		Category parentCategory = utils.saveRandomCategoryOnDB();
		
		category.setParentCategory(parentCategory);
		category = this.categoryRepository.save(category);
		
		// here we manually create a circular reference
		// Between the current category and its parent
		// example A -> B -> A is not allowed
		// it should update to null -> B -> A
		parentCategory.setParentCategory(category);
		
		// ResponseEntity<String> response = sendCategoryUpdateRequest(parentCategory);
		// Category updatedCategory = (Category) getContentFromJsonRespose(response.getBody(), Category.class);

		// assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		// assertThat(updatedCategory.getName()).isEqualTo(newName);
	}
	
	@Test
	public void testItReturnsNotFoundWhenUpdating() throws JsonParseException, JsonMappingException, IOException {
		Long nonExistentCategoryId = new Long(1);
		
		FormBuilder form = new FormBuilder();
		form.add("name", "some name");
			
		ResponseEntity<String> response = sendCategoryUpdateRequest(nonExistentCategoryId, form);
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}
	
	@Test(expected = NoSuchElementException.class)
	public void testItDeletesACategory() throws JsonParseException, JsonMappingException, IOException {
		Category category = utils.saveRandomCategoryOnDB();
		
		ResponseEntity<String> response = sendCategoryDeleteRequest(category.getId());
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
		categoryRepository.findById(category.getId()).get();
	}
	
	@Test
	public void testItReturnsNotFoundWhenDeleting() throws JsonParseException, JsonMappingException, IOException {
		Category category = utils.generateRandomCategory();
		category.setId(new Long(1));
		
		ResponseEntity<String> response = sendCategoryDeleteRequest(category.getId());
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}
	
	public ResponseEntity<String> sendFindAllCategoriesRequest() throws JsonParseException, JsonMappingException, IOException {		
		String url = "/api/admin/categories";
		ResponseEntity<String> response = this.restTemplate.getForEntity(url, String.class);		
		return response;
	} 
	
	public ResponseEntity<String> sendFindCategoryByIdRequest(Long id) throws JsonParseException, JsonMappingException, IOException {
		String url = "/api/admin/categories/" + id;
		ResponseEntity<String> response = this.restTemplate.getForEntity(url, String.class);
		return response;
	} 
	
	public ResponseEntity<String> sendCategoryCreateRequest(FormBuilder form) throws JsonParseException, JsonMappingException, IOException {	
		String url = "/api/admin/categories";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		MultiValueMap<String, Object> body = form.build();
		
		ResponseEntity<String> jsonResponse = sendRequest(url, HttpMethod.POST, headers, body);		
		return jsonResponse;
	}
	
	public ResponseEntity<String> sendCategoryUpdateRequest(Long categoryId, FormBuilder form) throws JsonParseException, JsonMappingException, IOException {	
		String url = "/api/admin/categories/" + categoryId;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		MultiValueMap<String, Object> body = form.build();
	
		ResponseEntity<String> response = sendRequest(url, HttpMethod.PUT, headers, body);
		return response;
	}
	
	public ResponseEntity<String> sendCategoryDeleteRequest(Long categoryId) throws JsonParseException, JsonMappingException, IOException {	
		String url = "/api/admin/categories/" + categoryId;
		HttpHeaders headers = new HttpHeaders();
		HttpMethod method = HttpMethod.DELETE;
				
		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(headers);
		ResponseEntity<String> response = this.restTemplate.exchange(url, method, requestEntity, String.class);
		return response;
	}
	
	public ResponseEntity<String> sendRequest(String url, HttpMethod method, HttpHeaders headers, MultiValueMap<String, Object> body) {
		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
		ResponseEntity<String> response = this.restTemplate.exchange(url, method, requestEntity, String.class);
		return response;
	}
	
	public <T> Object getContentFromJsonRespose(String jsonResponse, Class<T> classType) throws JsonParseException, JsonMappingException, IOException {	
	    ObjectMapper mapper = new ObjectMapper();
	    Map<?, ?> mapResponse = mapper.readValue(jsonResponse, Map.class);
	    
	    T content = mapper.convertValue(mapResponse.get("content"), classType);
	    return content;
	}
}
