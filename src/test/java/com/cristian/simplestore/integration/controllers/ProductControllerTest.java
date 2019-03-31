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
import com.cristian.simplestore.persistence.entities.Image;
import com.cristian.simplestore.persistence.entities.Product;
import com.cristian.simplestore.persistence.respositories.ProductRepository;
import com.cristian.simplestore.utils.ApiRequestUtils;
import com.cristian.simplestore.utils.DbCleaner;
import com.cristian.simplestore.utils.FormBuilder;
import com.cristian.simplestore.utils.ProductTestsUtils;
import com.cristian.simplestore.web.dto.ProductResponseDto;
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
  DbCleaner dbCleaner;

  private ApiRequestUtils apiUtils;

  @Before
  public void setUp() {
    this.apiUtils = new ApiRequestUtils(restTemplate);
    cleanUpDb();
  }

  @After
  public void tearDown() {
    cleanUpDb();
  }

  public void cleanUpDb() {
    dbCleaner.cleanProductsTable();
  }

  @Test
  public void testItFindsAllProducts()
      throws JsonParseException, JsonMappingException, IOException {
    long MAX_PRODUCTS_SIZE = 4;
    List<Product> products = productsUtils.saveRandomProductsOnDB(MAX_PRODUCTS_SIZE);

    ResponseEntity<String> response = sendFindAllProductsRequest();

    List<?> foundProducts =
        (List<?>) this.apiUtils.getContentFromJsonRespose(response.getBody(), List.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(foundProducts.size()).isEqualTo(products.size());
  }

  @Test
  public void testItFindsAProductById()
      throws JsonParseException, JsonMappingException, IOException {
    Product product = productsUtils.saveRandomProductOnDB();

    ResponseEntity<String> response = sendFindProductByIdRequest(product.getId());

    ProductResponseDto foundProduct = (ProductResponseDto) apiUtils
        .getContentFromJsonRespose(response.getBody(), ProductResponseDto.class);

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
  public void testItDoesNotFindAProductById()
      throws JsonParseException, JsonMappingException, IOException {
    Long nonExistentProductId = 1L;
    ResponseEntity<String> response = sendFindProductByIdRequest(nonExistentProductId);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }

  @Test
  public void testItCreatesAProduct() throws JsonParseException, JsonMappingException, IOException {
    FormBuilder form = productsUtils.generateRandomProductCreateRequesForm();

    ResponseEntity<String> response = sendProductCreateRequest(form);

    ProductResponseDto createdProduct = (ProductResponseDto) apiUtils
        .getContentFromJsonRespose(response.getBody(), ProductResponseDto.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    assertThat(createdProduct.getName()).isEqualTo(form.get("name"));
    assertThat(createdProduct.getDescription()).isEqualTo(form.get("description"));
    assertThat(createdProduct.getPrice()).isEqualTo(form.get("price"));
    assertThat(createdProduct.getPriceSale()).isEqualTo(form.get("priceSale"));
    assertThat(createdProduct.isInSale()).isEqualTo(form.get("inSale"));
    assertThat(createdProduct.isActive()).isEqualTo(form.get("active"));
    assertThat(createdProduct.getStock()).isEqualTo(form.get("stock"));
    assertThat(createdProduct.getCategory().getId()).isEqualTo(form.get("category"));
    assertThat(createdProduct.getImages().size())
        .isEqualTo(((List<?>) form.getAll("images")).size());
  }

  @Test
  public void testItUpdatesAProduct() throws JsonParseException, JsonMappingException, IOException {
    Product productToUpdate = productsUtils.saveRandomProductOnDB();
    FormBuilder form = productsUtils.generateRandomProductUpdateRequesForm();

    ResponseEntity<String> response = sendProductUpdateRequest(productToUpdate.getId(), form);

    ProductResponseDto updatedProduct = (ProductResponseDto) this.apiUtils
        .getContentFromJsonRespose(response.getBody(), ProductResponseDto.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(updatedProduct.getName()).isEqualTo(form.get("name"));
    assertThat(updatedProduct.getDescription()).isEqualTo(form.get("description"));
    assertThat(updatedProduct.getPrice()).isEqualTo(form.get("price"));
    assertThat(updatedProduct.getPriceSale()).isEqualTo(form.get("priceSale"));
    assertThat(updatedProduct.isInSale()).isEqualTo(form.get("inSale"));
    assertThat(updatedProduct.isActive()).isEqualTo(form.get("active"));
    assertThat(updatedProduct.getStock()).isEqualTo(form.get("stock"));
    assertThat(updatedProduct.getCategory().getId()).isEqualTo(form.get("category"));
    assertThat(updatedProduct.getImages().size()).isEqualTo(
        ((List<?>) form.getAll("newImages")).size() + productToUpdate.getImages().size());
  }

  @Test
  public void testItUpdatesAProductImages()
      throws JsonParseException, JsonMappingException, IOException {
    Product product = productsUtils.saveRandomProductOnDBWithImages();
    FormBuilder form = productsUtils.generateRandomProductUpdateRequesForm();
    form.add("imagesIdsToDelete", getIdsFromImages(product.getImages()));

    ResponseEntity<String> response = sendProductUpdateRequest(product.getId(), form);
    ProductResponseDto updatedProduct = (ProductResponseDto) apiUtils
        .getContentFromJsonRespose(response.getBody(), ProductResponseDto.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(updatedProduct.getName()).isEqualTo(form.get("name"));
    assertThat(updatedProduct.getDescription()).isEqualTo(form.get("description"));
    assertThat(updatedProduct.getPrice()).isEqualTo(form.get("price"));
    assertThat(updatedProduct.getPriceSale()).isEqualTo(form.get("priceSale"));
    assertThat(updatedProduct.isInSale()).isEqualTo(form.get("inSale"));
    assertThat(updatedProduct.isActive()).isEqualTo(form.get("active"));
    assertThat(updatedProduct.getStock()).isEqualTo(form.get("stock"));
    assertThat(updatedProduct.getCategory().getId()).isEqualTo(form.get("category"));
    assertThat(updatedProduct.getImages().size())
        .isEqualTo(((List<?>) form.getAll("newImages")).size());
  }

  @Test
  public void testItReturnsNotFoundWhenUpdating()
      throws JsonParseException, JsonMappingException, IOException {
    Long nonExistentId = 1L;

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
  public void testItReturnsNotFoundWhenDeleting()
      throws JsonParseException, JsonMappingException, IOException {
    Long nonExistentId = 1L;

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

    ResponseEntity<String> response =
        this.apiUtils.sendRequest(url, HttpMethod.POST, headers, body);
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

  private List<Long> getIdsFromImages(List<Image> images) {
    List<Long> imagesIds = new ArrayList<>();
    images.forEach((image) -> imagesIds.add(image.getId()));
    return imagesIds;
  }
}
