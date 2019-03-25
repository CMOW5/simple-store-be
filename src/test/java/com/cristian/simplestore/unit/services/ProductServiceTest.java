package com.cristian.simplestore.unit.services;

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

import com.cristian.simplestore.business.services.ProductService;
import com.cristian.simplestore.persistence.entities.Product;
import com.cristian.simplestore.persistence.respositories.ProductRepository;
import com.cristian.simplestore.BaseTest;
import com.cristian.simplestore.utils.ProductTestsUtils;
import com.cristian.simplestore.web.forms.ProductCreateForm;
import com.cristian.simplestore.web.forms.ProductUpdateForm;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTest extends BaseTest {
		
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
		int MAX_PRODUCTS_SIZE = 4;
		List<Product> createdProducts = utils.saveRandomProductsOnDB(MAX_PRODUCTS_SIZE);
				
		List<Product> expectedProducts = this.productService.findAll();
		
		assertThat(expectedProducts.size()).isEqualTo(createdProducts.size());
	}
	
	@Test
	public void testItFindsAProductById() throws Exception {
		Product product = utils.saveRandomProductOnDB();
		
		Product expectedProduct = productService.findById(product.getId());
		
		assertThat(expectedProduct.getId()).isEqualTo(product.getId());
	}
	
	@Test(expected = EntityNotFoundException.class)
	public void testItDoesNotFindAProductById() {
		long nonExistentProductId = 1;
		productService.findById(nonExistentProductId);
	}
	
	@Test
	public void testItCreatesAProduct() {
		Product product = utils.generateRandomProduct();
		
		Product expectedProduct = productService.create(product);
		
		assertThat(expectedProduct.getId()).isEqualTo(product.getId());
	}
	
	@Test
	public void testItCreatesAProductWithForm() {
		ProductCreateForm productForm = 
				utils.generateRandomProductCreateForm();
		
		Product expectedProduct = productService.create(productForm);
		
		assertThat(expectedProduct.getName()).isEqualTo(productForm.getName());
		assertThat(expectedProduct.getDescription()).isEqualTo(productForm.getDescription());
		assertThat(expectedProduct.getPrice()).isEqualTo(productForm.getPrice());
	}
	
	@Test
	public void testItUpdatesAProduct() {
		Product productToUpdate = utils.saveRandomProductOnDB();
		Product newProductData = utils.generateRandomProduct();
		
		Product expectedProduct = productService.update(productToUpdate.getId(), newProductData);
		
		assertThat(expectedProduct.getName()).isEqualTo(newProductData.getName());
		assertThat(expectedProduct.getPrice()).isEqualTo(newProductData.getPrice());
	}
		
	@Test
	public void testItUpdatesAProductWithForm() {
		Product productToUpdate = utils.saveRandomProductOnDB();
		ProductUpdateForm newProductData = 
				utils.generateRandomProductUpdateForm();
		newProductData.setId(productToUpdate.getId());
		
		Product expectedProduct = productService.update(newProductData);
		
		assertThat(expectedProduct.getName()).isEqualTo(newProductData.getName());
		assertThat(expectedProduct.getDescription()).isEqualTo(newProductData.getDescription());
		assertThat(expectedProduct.getPrice()).isEqualTo(newProductData.getPrice());
	}
	
	@Test(expected = EntityNotFoundException.class)
	public void testItDoesNotUpdateAProductWithForm() {
		long nonExistentProductId = 1;
		ProductUpdateForm newProductData = 
				utils.generateRandomProductUpdateForm();
		newProductData.setId(nonExistentProductId);
		
		productService.update(newProductData);
	}
	
	@Test(expected = EntityNotFoundException.class)
	public void testItDeletesAProduct() {
		Product product = utils.saveRandomProductOnDB();
		productService.deleteById(product.getId());
		productService.findById(product.getId());		
	}
	
	@Test(expected = EntityNotFoundException.class)
	public void testItDoesNotFoundDeleteProduct() {
		long nonExistentProductId = 1;
		productService.deleteById(nonExistentProductId);
	}
}
