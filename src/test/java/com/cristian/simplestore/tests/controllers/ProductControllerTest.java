package com.cristian.simplestore.tests.controllers;

import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.*;


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
	 * @return a product with random data
	 */
	public Product generateRandomProduct() {
		Faker faker = new Faker();
		String name = faker.commerce().productName(); 
		String price = faker.commerce().price(0, Double.MAX_VALUE); 
		return new Product(name, Double.valueOf(price));
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
	
	public <T> Object getContentFromJsonRespose(String jsonResponse, Class<T> classType) throws JsonParseException, JsonMappingException, IOException {	
	    ObjectMapper mapper = new ObjectMapper();
	    Map mapResponse = mapper.readValue(jsonResponse, Map.class);
	    
	    T content = mapper.convertValue(mapResponse.get("content"), classType);
	    return content;
	}

	// @Test
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
	
	// @Test
	public void testItFindsAProductById() throws JsonParseException, JsonMappingException, IOException {
		Product product = saveRandomProductOnDB(); 
		
		String jsonResponse = this.restTemplate.getForObject("/api/admin/products/" + product.getId(), String.class);
	    Product foundProduct = (Product) getContentFromJsonRespose(jsonResponse, Product.class);

		assertThat(foundProduct.getName()).isEqualTo(product.getName());
		assertThat(foundProduct.getPrice()).isEqualTo(product.getPrice());
	}
	
	// @Test
	public void testItCreatesAProduct() throws JsonParseException, JsonMappingException, IOException {
		Product product = generateRandomProduct();
		
		String jsonResponse = this.restTemplate.postForObject("/api/admin/products", product, String.class);
		Product createdProduct = (Product) getContentFromJsonRespose(jsonResponse, Product.class);
		
		
		assertThat(createdProduct.getName()).isEqualTo(product.getName());
		assertThat(createdProduct.getPrice()).isEqualTo(product.getPrice());
	}
	
	// @Test
	public void testItDeletesAProduct() throws JsonParseException, JsonMappingException, IOException {
		Product product = saveRandomProductOnDB();
		
		this.restTemplate.delete("/api/admin/products/" + product.getId());
		String jsonResponse = this.restTemplate.getForObject("/api/admin/products/" + product.getId(), String.class);
		Product deletedProduct  = (Product) getContentFromJsonRespose(jsonResponse, Product.class);

		
		assertThat(deletedProduct).isNull();
	}
}
