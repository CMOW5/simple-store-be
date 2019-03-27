package com.cristian.simplestore.integration.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.MultiValueMap;

import com.cristian.simplestore.BaseTest;
import com.cristian.simplestore.persistence.entities.Category;
import com.cristian.simplestore.persistence.entities.Image;
import com.cristian.simplestore.persistence.entities.Product;
import com.cristian.simplestore.persistence.respositories.ProductRepository;
import com.cristian.simplestore.utils.ApiRequestUtils;
import com.cristian.simplestore.utils.CategoryTestsUtils;
import com.cristian.simplestore.utils.FormBuilder;
import com.cristian.simplestore.utils.ImageBuilder;
import com.cristian.simplestore.utils.ProductResponseDTO;
import com.cristian.simplestore.utils.ProductTestsUtils;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProductControllerTest extends BaseTest {

	@Autowired
	TestRestTemplate restTemplate;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductTestsUtils productsUtils;

	@Autowired
	private CategoryTestsUtils categoriesUtils;

	@Autowired
	private ImageBuilder imageBuilder;

	private ApiRequestUtils apiUtils;

	@Before
	public void setUp() {
		this.apiUtils = new ApiRequestUtils(restTemplate);
		productRepository.deleteAll();
	}

	@After
	public void tearDown() {
		productRepository.deleteAll();
	}

	@Test
	public void testItFindsAllProducts() throws JsonParseException, JsonMappingException, IOException {
		long MAX_PRODUCTS_SIZE = 4;
		this.productsUtils.saveRandomProductsOnDB(MAX_PRODUCTS_SIZE);

		ResponseEntity<String> response = sendFindAllProductsRequest();

		List<ProductResponseDTO> foundProducts = 
				(List<ProductResponseDTO>) this.apiUtils.getContentFromJsonRespose(response.getBody(), List.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(foundProducts.size()).isEqualTo(MAX_PRODUCTS_SIZE);
	}

	@Test
	public void testItFindsAProductById() throws JsonParseException, JsonMappingException, IOException {
		Product product = this.productsUtils.saveRandomProductOnDB();

		ResponseEntity<String> response = sendFindProductByIdRequest(product.getId());

		ProductResponseDTO foundProduct = (ProductResponseDTO) this.apiUtils.getContentFromJsonRespose(response.getBody(), ProductResponseDTO.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(foundProduct.getName()).isEqualTo(product.getName());
		assertThat(foundProduct.getDescription()).isEqualTo(product.getDescription());
		assertThat(foundProduct.getPrice()).isEqualTo(product.getPrice());
		assertThat(foundProduct.getPriceSale()).isEqualTo(product.getPriceSale());
		assertThat(foundProduct.isInSale()).isEqualTo(product.isInSale());
		assertThat(foundProduct.isActive()).isEqualTo(product.isActive());
		assertThat(foundProduct.getStock()).isEqualTo(product.getStock());
	}

	@Test
	public void testItDoesNotFindAProductById() throws JsonParseException, JsonMappingException, IOException {
		ResponseEntity<String> response = sendFindProductByIdRequest(new Long(1));

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@Test
	public void testItCreatesAProduct() throws JsonParseException, JsonMappingException, IOException {
		Product product = this.productsUtils.generateRandomProduct();
		Category category = this.categoriesUtils.saveRandomCategoryOnDB();
		int IMAGES_SIZE = 2;
		
		FormBuilder form = new FormBuilder();
		form.add("name", product.getName())
			.add("description", product.getDescription())
			.add("price", product.getPrice())
			.add("priceSale", product.getPriceSale())
			.add("inSale", product.isInSale())
			.add("active", product.isActive())
			.add("stock", product.getStock())
			.add("category", category.getId())
			.add("images", imageBuilder.createImages(IMAGES_SIZE));

		ResponseEntity<String> response = sendProductCreateRequest(form);
		
		ProductResponseDTO createdProduct = (ProductResponseDTO) this.apiUtils.getContentFromJsonRespose(response.getBody(), ProductResponseDTO.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(createdProduct.getName()).isEqualTo(product.getName());
		assertThat(createdProduct.getDescription()).isEqualTo(product.getDescription());
		assertThat(createdProduct.getPrice()).isEqualTo(product.getPrice());
		assertThat(createdProduct.getPriceSale()).isEqualTo(product.getPriceSale());
		assertThat(createdProduct.isInSale()).isEqualTo(product.isInSale());
		assertThat(createdProduct.isActive()).isEqualTo(product.isActive());
		assertThat(createdProduct.getStock()).isEqualTo(product.getStock());
		assertThat(createdProduct.getImages().size()).isEqualTo(IMAGES_SIZE);

//		Map<?, ?> json = this.apiUtils.mapJsonRespose(response.getBody());
//
//		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
//		assertThat(json.get("name")).isEqualTo(product.getName());
//		assertThat(json.get("description")).isEqualTo(product.getDescription());
//		assertThat(json.get("price")).isEqualTo(product.getPrice());
//		assertThat(json.get("priceSale")).isEqualTo(product.getPriceSale());
//		assertThat(json.get("inSale")).isEqualTo(product.isInSale());
//		assertThat(json.get("active")).isEqualTo(product.isActive());
//		assertThat(json.get("stock").toString()).isEqualTo(product.getStock().toString());
//		
//		ArrayList<Object> images = (ArrayList<Object>) json.get("images");
//		assertThat(images.size()).isEqualTo(2);
	}

	@Test
	public void testItUpdatesAProduct() throws JsonParseException, JsonMappingException, IOException {
		Product product = this.productsUtils.saveRandomProductOnDB();
		Product newProductData = this.productsUtils.generateRandomProduct();
		Category newCategory = this.categoriesUtils.saveRandomCategoryOnDB();
		int IMAGES_SIZE = 2;

		FormBuilder form = new FormBuilder();
		form.add("name", newProductData.getName())
			.add("description", newProductData.getDescription())
			.add("price", newProductData.getPrice())
			.add("priceSale", newProductData.getPriceSale())
			.add("inSale", newProductData.isInSale())
			.add("active", newProductData.isActive())
			.add("stock", newProductData.getStock())
			.add("category", newCategory.getId())
			.add("newImages", imageBuilder.createImages(IMAGES_SIZE));

		ResponseEntity<String> response = sendProductUpdateRequest(product.getId(), form);
		
		ProductResponseDTO updatedProduct = (ProductResponseDTO) this.apiUtils.getContentFromJsonRespose(response.getBody(), ProductResponseDTO.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(updatedProduct.getName()).isEqualTo(newProductData.getName());
		assertThat(updatedProduct.getDescription()).isEqualTo(newProductData.getDescription());
		assertThat(updatedProduct.getPrice()).isEqualTo(newProductData.getPrice());
		assertThat(updatedProduct.getPriceSale()).isEqualTo(newProductData.getPriceSale());
		assertThat(updatedProduct.isInSale()).isEqualTo(newProductData.isInSale());
		assertThat(updatedProduct.isActive()).isEqualTo(newProductData.isActive());
		assertThat(updatedProduct.getStock()).isEqualTo(newProductData.getStock());
		assertThat(updatedProduct.getImages().size()).isEqualTo(IMAGES_SIZE);
	}
	
	@Test
	public void testItUpdatesAProductImages() throws JsonParseException, JsonMappingException, IOException {
		Product product = this.productsUtils.saveRandomProductOnDBWithImages();
		List<Image> images = product.getImages();
		List<Long> imagesIdsToDelete = new ArrayList<>();
		images.forEach(image -> imagesIdsToDelete.add(image.getId()));
	
		FormBuilder form = new FormBuilder();
		form.add("name", product.getName())
			.add("description", product.getDescription())
			.add("price", product.getPrice())
			.add("priceSale", product.getPriceSale())
			.add("inSale", product.isInSale())
			.add("active", product.isActive())
			.add("stock", product.getStock())
			.add("category", product.getId())
			.add("newImages", null)
			.add("imagesIdsToDelete", imagesIdsToDelete);
		
		ResponseEntity<String> response = sendProductUpdateRequest(product.getId(), form);
		ProductResponseDTO updatedProduct = (ProductResponseDTO) this.apiUtils.getContentFromJsonRespose(response.getBody(), ProductResponseDTO.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(updatedProduct.getName()).isEqualTo(product.getName());
		assertThat(updatedProduct.getDescription()).isEqualTo(product.getDescription());
		assertThat(updatedProduct.getPrice()).isEqualTo(product.getPrice());
		assertThat(updatedProduct.getPriceSale()).isEqualTo(product.getPriceSale());
		assertThat(updatedProduct.isInSale()).isEqualTo(product.isInSale());
		assertThat(updatedProduct.isActive()).isEqualTo(product.isActive());
		assertThat(updatedProduct.getStock()).isEqualTo(product.getStock());
		assertThat(updatedProduct.getImages().size()).isZero();
	}

	@Test
	public void testItReturnsNotFoundWhenUpdating() throws JsonParseException, JsonMappingException, IOException {
		Long nonExistentId = new Long(1);

		FormBuilder form = new FormBuilder();
		form.add("name", "some name").add("description", "some description");

		ResponseEntity<String> response = sendProductUpdateRequest(nonExistentId, form);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}

	@Test(expected = NoSuchElementException.class)
	public void testItDeletesAProduct() throws JsonParseException, JsonMappingException, IOException {
		Product product = this.productsUtils.saveRandomProductOnDB();

		ResponseEntity<String> response = sendProductDeleteRequest(product.getId());

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
		productRepository.findById(product.getId()).get();
	}

	@Test
	public void testItReturnsNotFoundWhenDeleting() throws JsonParseException, JsonMappingException, IOException {
		Long nonExistentId = new Long(1);

		ResponseEntity<String> response = sendProductDeleteRequest(nonExistentId);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	private ResponseEntity<String> sendFindAllProductsRequest() {
		String url = "/api/admin/products";
		ResponseEntity<String> response = this.apiUtils.sendRequest(url, HttpMethod.GET, null, null);
		return response;
	}

	private ResponseEntity<String> sendFindProductByIdRequest(Long id)
			throws JsonParseException, JsonMappingException, IOException {
		String url = "/api/admin/products/" + id;
		ResponseEntity<String> response = this.apiUtils.sendRequest(url, HttpMethod.GET, null, null);
		return response;
	}

	private ResponseEntity<String> sendProductCreateRequest(FormBuilder form)
			throws JsonParseException, JsonMappingException, IOException {
		String url = "/api/admin/products";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		MultiValueMap<String, Object> body = form.build();

		ResponseEntity<String> response = this.apiUtils.sendRequest(url, HttpMethod.POST, headers, body);
		return response;
	}

	private ResponseEntity<String> sendProductUpdateRequest(Long productId, FormBuilder form)
			throws JsonParseException, JsonMappingException, IOException {
		String url = "/api/admin/products/" + productId;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		MultiValueMap<String, Object> body = form.build();

		ResponseEntity<String> response = this.apiUtils.sendRequest(url, HttpMethod.PUT, headers, body);
		return response;
	}

	private ResponseEntity<String> sendProductDeleteRequest(Long productId)
			throws JsonParseException, JsonMappingException, IOException {
		String url = "/api/admin/products/" + productId;
		ResponseEntity<String> response = this.apiUtils.sendRequest(url, HttpMethod.DELETE, null, null);
		return response;
	}
}
