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
import org.springframework.test.context.junit4.SpringRunner;
import com.cristian.simplestore.integration.controllers.BaseIntegrationTest;
import com.cristian.simplestore.integration.controllers.product.request.AuthenticatedProductRequest;
import com.cristian.simplestore.persistence.entities.ImageEntity;
import com.cristian.simplestore.persistence.entities.ProductEntity;
import com.cristian.simplestore.utils.MultiPartFormBuilder;
import com.cristian.simplestore.utils.product.ProductFormUtils;
import com.cristian.simplestore.utils.product.ProductGenerator;
import com.cristian.simplestore.utils.request.JsonResponse;
import com.cristian.simplestore.web.dto.response.ProductResponse;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProductControllerTest extends BaseIntegrationTest {

  @Autowired
  private ProductGenerator productsGenerator;
  
  @Autowired
  private ProductFormUtils productsFormUtils;
  
  @Autowired
  private AuthenticatedProductRequest request;

  @Test
  public void testItFindsAllProducts()
      throws JsonParseException, JsonMappingException, IOException {
    int MAX_PRODUCTS_SIZE = 4;
    List<ProductEntity> productEntities = productsGenerator.saveRandomProductsOnDB(MAX_PRODUCTS_SIZE);

    JsonResponse response = request.sendFindAllProductsRequest();

    List<?> foundProducts =
        (List<?>) response.getContent(List.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(foundProducts.size()).isEqualTo(productEntities.size());
  }

  @Test
  public void testItFindsAProductById()
      throws JsonParseException, JsonMappingException, IOException {
    ProductEntity productEntity = productsGenerator.saveRandomProductOnDB();

    JsonResponse response = request.sendFindProductByIdRequest(productEntity.getId());

    ProductResponse foundProduct = (ProductResponse) response
        .getContent(ProductResponse.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThatProductResponseIsEqualToProduct(foundProduct, productEntity);
  }

  @Test
  public void testItDoesNotFindAProductById()
      throws JsonParseException, JsonMappingException, IOException {
    Long nonExistentProductId = 1L;
    JsonResponse response = request.sendFindProductByIdRequest(nonExistentProductId);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }

  @Test
  public void testItCreatesAProduct() throws JsonParseException, JsonMappingException, IOException {
    MultiPartFormBuilder form = productsFormUtils.generateRandomProductCreateRequestForm();

    JsonResponse response = request.sendProductCreateRequest(form);

    ProductResponse createdProduct = (ProductResponse) response
        .getContent(ProductResponse.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    assertThatProductResponseIsEqualToProduct(createdProduct, form);
    assertThat(createdProduct.getImages().size())
        .isEqualTo(((List<?>) form.getAll("images")).size());
  }

  @Test
  public void testItUpdatesAProduct() throws JsonParseException, JsonMappingException, IOException {
    ProductEntity productToUpdate = productsGenerator.saveRandomProductOnDB();
    MultiPartFormBuilder form = productsFormUtils.generateRandomProductUpdateRequestForm();

    JsonResponse response = request.sendProductUpdateRequest(productToUpdate.getId(), form);

    ProductResponse updatedProduct = (ProductResponse) response
        .getContent(ProductResponse.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThatProductResponseIsEqualToProduct(updatedProduct, form);
    assertThat(updatedProduct.getImages().size()).isEqualTo(
        ((List<?>) form.getAll("newImages")).size() + productToUpdate.getImages().size());
  }

  @Test
  public void testItUpdatesAProductImages()
      throws JsonParseException, JsonMappingException, IOException {
    ProductEntity productEntity = productsGenerator.saveRandomProductOnDB();
    MultiPartFormBuilder form = productsFormUtils.generateRandomProductUpdateRequestForm();
    form.add("imagesIdsToDelete", getIdsFromImages(productEntity.getImages()));

    JsonResponse response = request.sendProductUpdateRequest(productEntity.getId(), form);
    ProductResponse updatedProduct = (ProductResponse) response
        .getContent(ProductResponse.class);

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

    JsonResponse response = request.sendProductUpdateRequest(nonExistentId, form);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
  }

  @Test
  public void testItDeletesAProduct() throws JsonParseException, JsonMappingException, IOException {
    ProductEntity productEntity = productsGenerator.saveRandomProductOnDB();

    JsonResponse response = request.sendProductDeleteRequest(productEntity.getId());

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    
    response = request.sendFindProductByIdRequest(productEntity.getId());
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }

  private void assertThatProductResponseIsEqualToProduct(ProductResponse expectedProduct,
      ProductEntity productEntity) {
    assertThat(expectedProduct.getName()).isEqualTo(productEntity.getName());
    assertThat(expectedProduct.getDescription()).isEqualTo(productEntity.getDescription());
    assertThat(expectedProduct.getPrice()).isEqualTo(productEntity.getPrice());
    assertThat(expectedProduct.getPriceSale()).isEqualTo(productEntity.getPriceSale());
    assertThat(expectedProduct.isInSale()).isEqualTo(productEntity.isInSale());
    assertThat(expectedProduct.isActive()).isEqualTo(productEntity.isActive());
    assertThat(expectedProduct.getStock()).isEqualTo(productEntity.getStock());
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

  private List<Long> getIdsFromImages(List<ImageEntity> images) {
    List<Long> imagesIds = new ArrayList<>();
    images.forEach((image) -> imagesIds.add(image.getId()));
    return imagesIds;
  }
}
