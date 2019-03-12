package com.cristian.simplestore.tests.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.cristian.simplestore.entities.Category;
import com.cristian.simplestore.respositories.CategoryRepository;
import com.cristian.simplestore.tests.BaseTest;
import com.cristian.simplestore.tests.utils.CategoryTestsUtils;
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
		
		List<Category> responseCategories = sendFindAllCategoriesRequest();
		
		assertThat(responseCategories.size()).isEqualTo(MAX_CATEGORIES_SIZE);
		
	}
	
	@Test
	public void testItFindsACategoryById() throws JsonParseException, JsonMappingException, IOException {
		Category category = utils.saveRandomCategoryOnDB(); 
		
	    Category foundCategory = sendFindCategoryByIdRequest(category.getId());

		assertThat(foundCategory.getName()).isEqualTo(category.getName());
		assertThat(foundCategory.getId()).isEqualTo(category.getId());
	}
	
	@Test
	public void testItCreatesACategory() throws JsonParseException, JsonMappingException, IOException {
		Category category = utils.generateRandomCategory();
		
		Category createdCategory = sendCategoryCreateRequest(category);

		assertThat(createdCategory.getName()).isEqualTo(category.getName());
	}
	
	@Test
	public void testItUpdatesACategory() throws JsonParseException, JsonMappingException, IOException {
		Category category = utils.saveRandomCategoryOnDB();
		
		String newName = new Faker().name().firstName();
		category.setName(newName);
		
		Category updatedCategory = sendCategoryUpdateRequest(category);

		assertThat(updatedCategory.getName()).isEqualTo(newName);
	}
	
	@Test(expected = NoSuchElementException.class)
	public void testItDeletesACategory() throws JsonParseException, JsonMappingException, IOException {
		Category category = utils.saveRandomCategoryOnDB();
		
		sendCategoryDeleteRequest(category);
			
		categoryRepository.findById(category.getId()).get();
	}
	
	public List<Category> sendFindAllCategoriesRequest() throws JsonParseException, JsonMappingException, IOException {
		String url = "/api/admin/categories";
		
		String jsonResponse = this.restTemplate.getForObject(url, String.class);		
		
		List<Category> responseCategories = (List<Category>) getContentFromJsonRespose(jsonResponse, List.class);
		return responseCategories;
	} 
	
	public Category sendFindCategoryByIdRequest(Long id) throws JsonParseException, JsonMappingException, IOException {
		String url = "/api/admin/categories/" + id;
		
		String jsonResponse = this.restTemplate.getForObject(url, String.class);
		
	    Category foundCategory = (Category) getContentFromJsonRespose(jsonResponse, Category.class);
	    return foundCategory;
	} 
	
	public Category sendCategoryCreateRequest(Category category) throws JsonParseException, JsonMappingException, IOException {	
		String url = "/api/admin/categories";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		Map<String, Object> body = mapObject(category);
		
		ResponseEntity<String> jsonResponse = sendRequest(url, HttpMethod.POST, headers, body);
		Category createdCategory = (Category) getContentFromJsonRespose(jsonResponse.getBody(), Category.class);
		
		return createdCategory;
	}
	
	public Category sendCategoryUpdateRequest(Category category) throws JsonParseException, JsonMappingException, IOException {	
		String url = "/api/admin/categories/" + category.getId();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		Map<String, Object> body = mapObject(category);
		
		ResponseEntity<String> jsonResponse = sendRequest(url, HttpMethod.PUT, headers, body);
		Category updatedCategory = (Category) getContentFromJsonRespose(jsonResponse.getBody(), Category.class);
		
		return updatedCategory;
	}
	
	public void sendCategoryDeleteRequest(Category category) throws JsonParseException, JsonMappingException, IOException {	
		String url = "/api/admin/categories/" + category.getId();
		this.restTemplate.delete(url);
	}
	
	public ResponseEntity<String> sendRequest(String url, HttpMethod method, HttpHeaders headers, Map<String, Object> body) {
		MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();		
		
		body.forEach((key, value) -> { 
				if (value instanceof List) {
					requestBody.addAll(key, (List<? extends Object>) value);
				} else {
					requestBody.add(key, value);
				}
			}
		);
		
		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
		ResponseEntity<String> response = this.restTemplate.exchange(url, method, requestEntity, String.class);
		
		return response;
	}
	
	public <T> Object getContentFromJsonRespose(String jsonResponse, Class<T> classType) throws JsonParseException, JsonMappingException, IOException {	
	    ObjectMapper mapper = new ObjectMapper();
	    Map mapResponse = mapper.readValue(jsonResponse, Map.class);
	    
	    T content = mapper.convertValue(mapResponse.get("content"), classType);
	    return content;
	}
	
	public Map<String, Object> mapObject(Object o) {
		 ObjectMapper mapper = new ObjectMapper();
		 Map<String, Object> map = mapper.convertValue(o, Map.class);
		 return map;
	}  
}
