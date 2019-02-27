package com.cristian.simplestore.tests.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cristian.simplestore.entities.Product;
import com.cristian.simplestore.respositories.ProductRepository;
import com.cristian.simplestore.services.ProductService;
import com.cristian.simplestore.tests.BaseTest;
import com.github.javafaker.Faker;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTest extends BaseTest {
	
	private final double MAX_PRICE = 10000000000.0;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ProductService productService;
	
	@Before
	public void setUp() {
		// save a couple of products
		saveRandomProduct();
		saveRandomProduct();
		saveRandomProduct();
		saveRandomProduct();
		saveRandomProduct();
	}
	
	/**
	 * create a product instance with random data
	 * @return the newly created user
	 */
	public Product createRandomProduct() {
		Faker faker = new Faker();
		String name = faker.commerce().productName(); 
		String price = faker.commerce().price(0, Double.MAX_VALUE); 
		return new Product(name, Double.valueOf(price));
	}
	
	/**
	 * stores a random product into the database
	 * @return the newly created product
	 */
	public Product saveRandomProduct() {
		Faker faker = new Faker();
		String name = faker.commerce().productName(); 
		String price = faker.commerce().price(0, MAX_PRICE); 
		return productRepository.save(new Product(name, Double.valueOf(price)));
	}
	
	// @Test
	public void testItFindsAllProducts() throws Exception {
		List<Product> products = this.productService.findAllProducts();
		assertThat(products.size()).isGreaterThanOrEqualTo(5);
	}
	
	// @Test
	public void testItFindsAProductById() throws Exception {
		Product product = saveRandomProduct();
		Product storedProduct = productService.findById(product.getId());
		assertThat(storedProduct.getId()).isEqualTo(product.getId());
	}
	
	// @Test
	public void testItCreatesAProduct() {
		// TODO: check whether the new product name is already on db
		Product product = createRandomProduct();
		Product storedProduct = productService.create(product);
		assertThat(product.getId()).isEqualTo(storedProduct.getId());
	}
	
	// @Test
	public void testItUpdatesAProduct() {
		// TODO: check whether the new product name is already on db
		Faker faker = new Faker();
		Product product = saveRandomProduct();
		
		product.setName((faker.commerce().productName()));
		product.setPrice(Double.valueOf((faker.commerce().price(0, MAX_PRICE))));
		
		Product updatedProduct = productService.update(product.getId(), product);
		
		assertThat(product.getName()).isEqualTo(updatedProduct.getName());
		assertThat(product.getPrice()).isEqualTo(updatedProduct.getPrice());
	}
	
	// @Test
	public void testItDeletesAProduct() {
		Product product = saveRandomProduct();
		productService.deleteById(product.getId());
		
		assertThat(productService.findById(product.getId())).isNull();
	}
}
