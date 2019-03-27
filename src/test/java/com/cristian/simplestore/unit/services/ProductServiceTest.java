package com.cristian.simplestore.unit.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cristian.simplestore.BaseTest;
import com.cristian.simplestore.business.services.ProductService;
import com.cristian.simplestore.persistence.entities.Category;
import com.cristian.simplestore.persistence.entities.Product;
import com.cristian.simplestore.persistence.respositories.CategoryRepository;
import com.cristian.simplestore.persistence.respositories.ImageRepository;
import com.cristian.simplestore.persistence.respositories.ProductRepository;
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
	ImageRepository imageRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	private ProductTestsUtils utils;
	
	@Before
	public void setUp() {
		cleanUpDb();
	}
	
	@After
    public void tearDown() {
		cleanUpDb();
    }
	
	public void cleanUpDb() {
		productRepository.deleteAll();
		imageRepository.deleteAll();
		categoryRepository.deleteAll();
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
		
		assertThatTwoProductsAreEqual(expectedProduct, product);
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
		
		assertThatTwoProductsAreEqual(expectedProduct, product);
	}
	
	@Test
	public void testItCreatesAProductWithForm() {
		ProductCreateForm productForm = utils.generateRandomProductCreateForm();
	
		Product expectedProduct = productService.create(productForm);
		
		assertThatTwoProductsAreEqual(expectedProduct, productForm.getModel());
		assertThat(expectedProduct.getImages().size()).isNotZero();
		assertThat(expectedProduct.getImages().size()).isEqualTo(productForm.getImages().size());
	}
		
	@Test
	public void testItUpdatesAProductWithForm() {
		Product productToUpdate = utils.saveRandomProductOnDB();
		ProductUpdateForm newProductData = utils.generateRandomProductUpdateForm();
		newProductData.setId(productToUpdate.getId());
		
		Product expectedProduct = productService.update(newProductData);
		
		assertThatTwoProductsAreEqual(expectedProduct, newProductData.getModel());
		assertThat(expectedProduct.getImages().size()).isNotZero();
		assertThat(expectedProduct.getImages().size()).isEqualTo(newProductData.getNewImages().size());
	}
	
	@Test
	public void testItDeletesAProductImages() {
		Product productToUpdate = utils.saveRandomProductOnDBWithImages();
		ProductUpdateForm newProductData = utils.generateRandomProductUpdateForm();
		newProductData.setId(productToUpdate.getId());
		newProductData.setNewImages(null);
		
		List<Long> imagesIdsToDelete = new ArrayList<Long>();
		productToUpdate.getImages().forEach(image -> imagesIdsToDelete.add(image.getId()));
		newProductData.setImagesIdsToDelete(imagesIdsToDelete);
		
		Product expectedProduct = productService.update(newProductData);
		
		assertThatTwoProductsAreEqual(expectedProduct, newProductData.getModel());
		assertThat(expectedProduct.getImages().size()).isZero();
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
	
	private void assertThatTwoProductsAreEqual(Product p1, Product p2) {
		assertThat(p1.getName()).isEqualTo(p2.getName());
		assertThat(p1.getDescription()).isEqualTo(p2.getDescription());
		assertThat(p1.getPrice()).isEqualTo(p2.getPrice());
		assertThat(p1.getPriceSale()).isEqualTo(p2.getPriceSale());
		assertThat(p1.isInSale()).isEqualTo(p2.isInSale());
		assertThat(p1.isActive()).isEqualTo(p2.isActive());
		assertThat(p1.getStock()).isEqualTo(p2.getStock());
		assertTrue(twoCategoriesAreEqual(p1.getCategory(), p2.getCategory()));
	}
	
	private boolean twoCategoriesAreEqual(Category c1, Category c2) {
		Optional<Category> category1 = Optional.ofNullable(c1);
		Optional<Category> category2 = Optional.ofNullable(c2);
		return category1.equals(category2);
	}
}
