package com.cristian.simplestore.integration.controllers.product;

import static org.assertj.core.api.Assertions.assertThat;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import com.cristian.simplestore.integration.controllers.BaseIntegrationTest;
import com.cristian.simplestore.integration.controllers.product.request.AuthenticatedProductRequest;
import com.cristian.simplestore.persistence.entities.Image;
import com.cristian.simplestore.persistence.entities.Product;
import com.cristian.simplestore.utils.MultiPartFormBuilder;
import com.cristian.simplestore.utils.ProductTestsUtils;
import com.cristian.simplestore.utils.RequestBuilder;
import com.cristian.simplestore.web.dto.response.ProductResponse;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProductControllerTest extends BaseIntegrationTest {

  @Autowired
  private ProductTestsUtils productsUtils;
  
  @Autowired
  private AuthenticatedProductRequest request;

  @Test
  public void testItFindsAllProducts()
      throws JsonParseException, JsonMappingException, IOException {
    long MAX_PRODUCTS_SIZE = 4;
    List<Product> products = productsUtils.saveRandomProductsOnDB(MAX_PRODUCTS_SIZE);

    ResponseEntity<String> response = request.sendFindAllProductsRequest();

    List<?> foundProducts =
        (List<?>) RequestBuilder.getContentFromJsonRespose(response.getBody(), List.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(foundProducts.size()).isEqualTo(products.size());
  }

  @Test
  public void testItFindsAProductById()
      throws JsonParseException, JsonMappingException, IOException {
    Product product = productsUtils.saveRandomProductOnDB();

    ResponseEntity<String> response = request.sendFindProductByIdRequest(product.getId());

    ProductResponse foundProduct = (ProductResponse) RequestBuilder
        .getContentFromJsonRespose(response.getBody(), ProductResponse.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThatProductResponseIsEqualToProduct(foundProduct, product);
  }

  @Test
  public void testItDoesNotFindAProductById()
      throws JsonParseException, JsonMappingException, IOException {
    Long nonExistentProductId = 1L;
    ResponseEntity<String> response = request.sendFindProductByIdRequest(nonExistentProductId);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }

  @Test
  public void testItCreatesAProduct() throws JsonParseException, JsonMappingException, IOException {
    MultiPartFormBuilder form = productsUtils.generateRandomProductCreateRequestForm();

    ResponseEntity<String> response = request.sendProductCreateRequest(form);

    ProductResponse createdProduct = (ProductResponse) RequestBuilder
        .getContentFromJsonRespose(response.getBody(), ProductResponse.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    assertThatProductResponseIsEqualToProduct(createdProduct, form);
    assertThat(createdProduct.getImages().size())
        .isEqualTo(((List<?>) form.getAll("images")).size());
  }

  @Test
  public void testItUpdatesAProduct() throws JsonParseException, JsonMappingException, IOException {
    Product productToUpdate = productsUtils.saveRandomProductOnDB();
    MultiPartFormBuilder form = productsUtils.generateRandomProductUpdateRequestForm();

    ResponseEntity<String> response = request.sendProductUpdateRequest(productToUpdate.getId(), form);

    ProductResponse updatedProduct = (ProductResponse) RequestBuilder
        .getContentFromJsonRespose(response.getBody(), ProductResponse.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThatProductResponseIsEqualToProduct(updatedProduct, form);
    assertThat(updatedProduct.getImages().size()).isEqualTo(
        ((List<?>) form.getAll("newImages")).size() + productToUpdate.getImages().size());
  }

  @Test
  public void testItUpdatesAProductImages()
      throws JsonParseException, JsonMappingException, IOException {
    Product product = productsUtils.saveRandomProductOnDBWithImages();
    MultiPartFormBuilder form = productsUtils.generateRandomProductUpdateRequestForm();
    form.add("imagesIdsToDelete", getIdsFromImages(product.getImages()));

    ResponseEntity<String> response = request.sendProductUpdateRequest(product.getId(), form);
    ProductResponse updatedProduct = (ProductResponse) RequestBuilder
        .getContentFromJsonRespose(response.getBody(), ProductResponse.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThatProductResponseIsEqualToProduct(updatedProduct, form);
    assertThat(updatedProduct.getImages().size())
        .isEqualTo(((List<?>) form.getAll("newImages")).size());
  }

  @Test
  public void testItReturnsNotFoundWhenUpdating()
      throws JsonParseException, JsonMappingException, IOException {
    Long nonExistentId = 1L;

    MultiPartFormBuilder form = new MultiPartFormBuilder();
    form.add("name", "some name").add("description", "some description");

    ResponseEntity<String> response = request.sendProductUpdateRequest(nonExistentId, form);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
  }

  @Test
  public void testItDeletesAProduct() throws JsonParseException, JsonMappingException, IOException {
    Product product = productsUtils.saveRandomProductOnDB();

    ResponseEntity<String> response = request.sendProductDeleteRequest(product.getId());

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    
    response = request.sendFindProductByIdRequest(product.getId());
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }

  @Test
  public void testItReturnsNotFoundWhenDeleting()
      throws JsonParseException, JsonMappingException, IOException {
    Long nonExistentId = 1L;

    ResponseEntity<String> response = request.sendProductDeleteRequest(nonExistentId);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }

  private void assertThatProductResponseIsEqualToProduct(ProductResponse expectedProduct,
      Product product) {
    assertThat(expectedProduct.getName()).isEqualTo(product.getName());
    assertThat(expectedProduct.getDescription()).isEqualTo(product.getDescription());
    assertThat(expectedProduct.getPrice()).isEqualTo(product.getPrice());
    assertThat(expectedProduct.getPriceSale()).isEqualTo(product.getPriceSale());
    assertThat(expectedProduct.isInSale()).isEqualTo(product.isInSale());
    assertThat(expectedProduct.isActive()).isEqualTo(product.isActive());
    assertThat(expectedProduct.getStock()).isEqualTo(product.getStock());
  }

  private void assertThatProductResponseIsEqualToProduct(ProductResponse expectedProduct,
      MultiPartFormBuilder form) {
    assertThat(expectedProduct.getName()).isEqualTo(form.get("name"));
    assertThat(expectedProduct.getDescription()).isEqualTo(form.get("description"));
    assertThat(expectedProduct.getPrice()).isEqualTo(form.get("price"));
    assertThat(expectedProduct.getPriceSale()).isEqualTo(form.get("priceSale"));
    assertThat(expectedProduct.isInSale()).isEqualTo(form.get("inSale"));
    assertThat(expectedProduct.isActive()).isEqualTo(form.get("active"));
    assertThat(expectedProduct.getStock()).isEqualTo(form.get("stock"));
    assertThat(expectedProduct.getCategory().getId()).isEqualTo(form.get("category"));
  }

  private List<Long> getIdsFromImages(List<Image> images) {
    List<Long> imagesIds = new ArrayList<>();
    images.forEach((image) -> imagesIds.add(image.getId()));
    return imagesIds;
  }
}