package com.cristian.simplestore.tests.controllers;

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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.cristian.simplestore.entities.Category;
import com.cristian.simplestore.entities.Product;
import com.cristian.simplestore.respositories.ProductRepository;
import com.cristian.simplestore.tests.BaseTest;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProductControllerTest extends BaseTest {
	
	private final double MAX_PRICE = 10000000000.0;
	
	// private static final Logger log = LoggerFactory.getLogger(SimpleStoreApplication.class);
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private ProductRepository productRepository;
	
	/**
	 * create a product instance with random data
	 * @return the newly created product
	 */
	public Product generateRandomProduct() {
		Faker faker = new Faker();
		Product product = new Product();
		
		String name = faker.commerce().productName();
		String description = faker.commerce().productName();
		String price = faker.commerce().price(0, Double.MAX_VALUE); 
		String priceSale = faker.commerce().price(0, Double.MAX_VALUE);
		boolean inSale = false;
		boolean active = true;
		Category category = null;
		Long units = (long) faker.number().numberBetween(0, 200);

		product.setName(name);
		product.setDescription(description);
		product.setPrice(Double.valueOf(price));
		product.setPriceSale(Double.valueOf(priceSale));
		product.setInSale(inSale);
		product.setActive(active);
		product.setCategory(category);
		product.setUnits(units);
		
		return product;
	}
	
	/**
	 * stores a random product into the database
	 * @return the newly created product
	 */
	public Product saveRandomProductOnDB() {
		Faker faker = new Faker();
		String name = faker.commerce().productName(); 
		String price = faker.commerce().price(0, MAX_PRICE); 
		return productRepository.save(new Product(name, Double.valueOf(price)));
	}
	
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
		
		int MAX_PRODUCTS_SIZE = 4;
		List<Product> productsToSave = new ArrayList<>();
		
		// save a couple of products on db
		for (int i = 0; i < MAX_PRODUCTS_SIZE; i++) {
			productsToSave.add(saveRandomProductOnDB());
		}
				
		String jsonResponse = this.restTemplate.getForObject("/api/admin/products", String.class);		
		
		List<Product> responseProducts = (List<Product>) getContentFromJsonRespose(jsonResponse, List.class);
		assertThat(responseProducts.size()).isGreaterThanOrEqualTo(MAX_PRODUCTS_SIZE);
		
	}
	
	@Test
	public void testItFindsAProductById() throws JsonParseException, JsonMappingException, IOException {
		Product product = saveRandomProductOnDB(); 
		
		String jsonResponse = this.restTemplate.getForObject("/api/admin/products/" + product.getId(), String.class);
	    Product foundProduct = (Product) getContentFromJsonRespose(jsonResponse, Product.class);

		assertThat(foundProduct.getName()).isEqualTo(product.getName());
		assertThat(foundProduct.getPrice()).isEqualTo(product.getPrice());
	}
	
	@Test
	public void testItCreatesAProduct() throws JsonParseException, JsonMappingException, IOException {
		Product product = generateRandomProduct();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add("name", product.getName());
		body.add("description", product.getDescription());
		body.add("price", product.getPrice());
		body.add("priceSale", product.getPriceSale());
		body.add("inSale", product.isInSale());
		body.add("active", product.isActive());
		body.add("category", product.getCategory());
		body.add("units", product.getUnits());
		body.add("images", null);
		
		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
		
		// String jsonResponse = this.restTemplate.postForObject("/api/admin/categories", category, String.class);
		// Category createdCategory = (Category) getContentFromJsonRespose(jsonResponse, Category.class);
		String serverUrl = "/api/admin/products";
		
		// ResponseEntity<String> response = restTemplate.postForEntity(serverUrl, requestEntity, String.class);
		String jsonResponse = this.restTemplate.postForObject(serverUrl, requestEntity, String.class);
		Product createdProduct = (Product) getContentFromJsonRespose(jsonResponse, Product.class);

		assertThat(createdProduct.getName()).isEqualTo(product.getName());
	}
	
	@Test
	public void testItUpdatesACategory() throws JsonParseException, JsonMappingException, IOException {
		Product product = saveRandomProductOnDB();
		Product newProductData = generateRandomProduct();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
				
		body.add("name", newProductData.getName());
		body.add("description", newProductData.getDescription());
		body.add("price", newProductData.getPrice());
		body.add("priceSale", newProductData.getPriceSale());
		body.add("inSale", newProductData.isInSale());
		body.add("active", newProductData.isActive());
		body.add("category", newProductData.getCategory());
		body.add("units", newProductData.getUnits());
		
		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
		
		String serverUrl = "/api/admin/products/" + product.getId();
		
		ResponseEntity<String> jsonResponse = this.restTemplate.exchange(serverUrl, HttpMethod.PUT, requestEntity, String.class);

		Product updatedProduct = (Product) getContentFromJsonRespose(jsonResponse.getBody(), Product.class);

		assertThat(updatedProduct.getName()).isEqualTo(newProductData.getName());
	}
	
	@Test
	public void testItDeletesAProduct() throws JsonParseException, JsonMappingException, IOException {
		Product product = saveRandomProductOnDB();
		
		this.restTemplate.delete("/api/admin/products/" + product.getId());
		String jsonResponse = this.restTemplate.getForObject("/api/admin/products/" + product.getId(), String.class);
		Product deletedProduct  = (Product) getContentFromJsonRespose(jsonResponse, Product.class);

		
		assertThat(deletedProduct).isNull();
	}
	
	public <T> Object getContentFromJsonRespose(String jsonResponse, Class<T> classType) throws JsonParseException, JsonMappingException, IOException {	
	    ObjectMapper mapper = new ObjectMapper();
	    Map mapResponse = mapper.readValue(jsonResponse, Map.class);
	    
	    T content = mapper.convertValue(mapResponse.get("content"), classType);
	    return content;
	}
}
