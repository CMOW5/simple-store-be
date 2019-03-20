package com.cristian.simplestore.integration.controllers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.cristian.simplestore.persistence.entities.Product;
import com.cristian.simplestore.persistence.respositories.ProductRepository;
import com.cristian.simplestore.BaseTest;
import com.cristian.simplestore.utils.ProductTestsUtils;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProductControllerTest extends BaseTest {
		
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ProductTestsUtils utils;
	
	@Before
	public void setUp() {
		productRepository.deleteAll();
	}
	
	@After
    public void tearDown() {
		productRepository.deleteAll();
    }
	
	@Test
	public void testItFindsAllProducts() throws JsonParseException, JsonMappingException, IOException {
		long MAX_PRODUCTS_SIZE = 4;
		utils.saveRandomProductsOnDB(MAX_PRODUCTS_SIZE);
		
		ResponseEntity<String> response = sendFindAllProductsRequest();
		
		List<Product> foundProducts = (List<Product>) getContentFromJsonRespose(response.getBody(), List.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(foundProducts.size()).isEqualTo(MAX_PRODUCTS_SIZE);		
	}
	
	@Test
	public void testItFindsAProductById() throws JsonParseException, JsonMappingException, IOException {
		Product product = utils.saveRandomProductOnDB(); 
		
		ResponseEntity<String> response = sendFindProductByIdRequest(product.getId());
		
		Product foundProduct = (Product) getContentFromJsonRespose(response.getBody(), Product.class);
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(foundProduct.getName()).isEqualTo(product.getName());
		assertThat(foundProduct.getPrice()).isEqualTo(product.getPrice());
	}
	
	@Test
	public void testItDoesNotFindAProductById() throws JsonParseException, JsonMappingException, IOException {
		ResponseEntity<String> response = sendFindProductByIdRequest(new Long(1));
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}
	
	@Test
	public void testItCreatesAProduct() throws JsonParseException, JsonMappingException, IOException {
		Product product = utils.generateRandomProduct();
		
		ResponseEntity<String> response = sendProductCreateRequest(product);
		
		Product createdProduct = (Product) getContentFromJsonRespose(response.getBody(), Product.class);
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(createdProduct.getName()).isEqualTo(product.getName());
	}
	
	@Test
	public void testItUpdatesAProduct() throws JsonParseException, JsonMappingException, IOException {
		Product product = utils.saveRandomProductOnDB();
		Product newProductData = utils.generateRandomProduct();
		newProductData.setId(product.getId());
		
		ResponseEntity<String> response = sendProductUpdateRequest(newProductData);
		
		Product updatedProduct= (Product) getContentFromJsonRespose(response.getBody(), Product.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(updatedProduct.getName()).isEqualTo(newProductData.getName());
	}
	
	@Test
	public void testItReturnsNotFoundWhenUpdating() throws JsonParseException, JsonMappingException, IOException {
		Product newProductData = utils.generateRandomProduct();
		newProductData.setId(new Long(1)); 
		
		ResponseEntity<String> response = sendProductUpdateRequest(newProductData);
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}
	
	@Test(expected = NoSuchElementException.class)
	public void testItDeletesAProduct() throws JsonParseException, JsonMappingException, IOException {
		Product product = utils.saveRandomProductOnDB();
		
		ResponseEntity<String> response = sendProductDeleteRequest(product);
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
		productRepository.findById(product.getId()).get();
	}
	
	public void testItReturnsNotFoundWhenDeleting() throws JsonParseException, JsonMappingException, IOException {
		Product product = utils.generateRandomProduct();
		product.setId(new Long(1));
		
		ResponseEntity<String> response = sendProductDeleteRequest(product);
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}
	
	public ResponseEntity<String> sendFindAllProductsRequest() throws JsonParseException, JsonMappingException, IOException {
		String url = "/api/admin/products";
		ResponseEntity<String> response = this.restTemplate.getForEntity(url, String.class);		
		return response;
	} 
	
	public ResponseEntity<String> sendFindProductByIdRequest(Long id) throws JsonParseException, JsonMappingException, IOException {
		String url = "/api/admin/products/" + id;
		ResponseEntity<String> response = this.restTemplate.getForEntity(url, String.class);
	    return response;
	} 
	
	public ResponseEntity<String> sendProductCreateRequest(Product product) throws JsonParseException, JsonMappingException, IOException {	
		String url = "/api/admin/products";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		Map<String, Object> body = mapObject(product);
		
		ResponseEntity<String> response = sendRequest(url, HttpMethod.POST, headers, body);		
		return response;
	}
	
	public ResponseEntity<String> sendProductUpdateRequest(Product product) throws JsonParseException, JsonMappingException, IOException {	
		String url = "/api/admin/products/" + product.getId();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		Map<String, Object> body = mapObject(product);
		
		ResponseEntity<String> response = sendRequest(url, HttpMethod.PUT, headers, body);		
		return response;
	}
	
	public ResponseEntity<String> sendProductDeleteRequest(Product product) throws JsonParseException, JsonMappingException, IOException {	
		String url = "/api/admin/products/" + product.getId();		
		HttpHeaders headers = new HttpHeaders();
		HttpMethod method = HttpMethod.DELETE;
				
		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(headers);
		ResponseEntity<String> response = this.restTemplate.exchange(url, method, requestEntity, String.class);
		return response;
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
