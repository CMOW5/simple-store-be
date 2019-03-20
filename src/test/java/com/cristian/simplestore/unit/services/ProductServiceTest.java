package com.cristian.simplestore.unit.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cristian.simplestore.business.services.ProductService;
import com.cristian.simplestore.persistence.entities.Product;
import com.cristian.simplestore.persistence.respositories.ProductRepository;
import com.cristian.simplestore.BaseTest;
import com.cristian.simplestore.utils.ProductTestsUtils;
import com.cristian.simplestore.web.forms.ProductCreateForm;
import com.cristian.simplestore.web.forms.ProductUpdateForm;
import com.github.javafaker.Faker;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTest extends BaseTest {
	
	private final double MAX_PRICE = 10000000000.0;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ProductService productService;
	
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
	public void testItFindsAllProducts() throws Exception {
		// create a couple of products on db
		int MAX_PRODUCTS_SIZE = 4;
		List<Product> createdProducts = new ArrayList<Product>();
		for (int i = 0; i < MAX_PRODUCTS_SIZE; i++) {
			createdProducts.add(utils.saveRandomProductOnDB());
		}
				
		List<Product> products = this.productService.findAll();
		assertThat(products.size()).isEqualTo(MAX_PRODUCTS_SIZE);
	}
	
	@Test
	public void testItFindsAProductById() throws Exception {
		Product product = utils.saveRandomProductOnDB();
		Product storedProduct = productService.findById(product.getId());
		assertThat(storedProduct.getId()).isEqualTo(product.getId());
	}
	
	@Test
	public void testItCreatesAProduct() {
		Product product = utils.generateRandomProduct();
		Product storedProduct = productService.create(product);
		assertThat(product.getId()).isEqualTo(storedProduct.getId());
	}
	
	@Test
	public void testItCreatesAProductWithForm() {
		ProductCreateForm productForm = 
				utils.generateRandomProductCreateForm();
		
		Product storedProduct = productService.create(productForm);
		assertThat(productForm.getName()).isEqualTo(storedProduct.getName());
		assertThat(productForm.getDescription()).isEqualTo(storedProduct.getDescription());
		assertThat(productForm.getPrice()).isEqualTo(storedProduct.getPrice());
	}
	
	@Test
	public void testItUpdatesAProduct() {
		Faker faker = new Faker();
		Product product = utils.saveRandomProductOnDB();
		
		product.setName((faker.commerce().productName()));
		product.setPrice(Double.valueOf((faker.commerce().price(0, MAX_PRICE))));
		
		Product updatedProduct = productService.update(product.getId(), product);
		
		assertThat(product.getName()).isEqualTo(updatedProduct.getName());
		assertThat(product.getPrice()).isEqualTo(updatedProduct.getPrice());
	}
	
	@Test
	public void testItUpdatesAProductWithForm() {
		Product productToUpdate = utils.saveRandomProductOnDB();
		ProductUpdateForm newProductData = 
				utils.generateRandomProductUpdateForm();
		newProductData.setId(productToUpdate.getId());
		
		Product updatedProduct = productService.update(newProductData);
		
		assertThat(newProductData.getName()).isEqualTo(updatedProduct.getName());
		assertThat(newProductData.getDescription()).isEqualTo(updatedProduct.getDescription());
		assertThat(newProductData.getPrice()).isEqualTo(updatedProduct.getPrice());
	}
	
	@Test(expected = EntityNotFoundException.class)
	public void testItDeletesAProduct() {
		Product product = utils.saveRandomProductOnDB();
		productService.deleteById(product.getId());
		productService.findById(product.getId());		
	}
}
