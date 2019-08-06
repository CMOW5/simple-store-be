package com.cristian.simplestore.infrastructure.controllers.e2e.product;

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
import org.springframework.http.RequestEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.cristian.simplestore.domain.models.Image;
import com.cristian.simplestore.domain.models.Product;
import com.cristian.simplestore.infrastructure.controllers.BaseIntegrationTest;
import com.cristian.simplestore.infrastructure.controllers.dto.ProductDto;
import com.cristian.simplestore.infrastructure.controllers.e2e.product.databuilder.ProductFormUtils;
import com.cristian.simplestore.infrastructure.controllers.e2e.product.requestfactory.ProductRequestFactory;
import com.cristian.simplestore.utils.MultiPartFormBuilder;
import com.cristian.simplestore.utils.product.ProductGenerator;
import com.cristian.simplestore.utils.request.JsonRequestSender;
import com.cristian.simplestore.utils.request.JsonResponse;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProductControllerE2ETest extends BaseIntegrationTest {

  private static final String IMAGES_KEY = "images";
	
  @Autowired
  private ProductGenerator productsGenerator;
  
  @Autowired
  private ProductFormUtils productsFormUtils;
  
  @Autowired
  private JsonRequestSender requestSender;
      
  @Test
  public void testItFindsAllProducts() throws IOException {
    int maxProductsSize = 4;
    List<Product> productEntities = productsGenerator.saveRandomProductsOnDB(maxProductsSize);

    RequestEntity<?> request = ProductRequestFactory.createFindAllProductsRequest().build();
    JsonResponse response = requestSender.send(request);
    
    List<?> foundProducts =
        (List<?>) response.getContent(List.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(foundProducts.size()).isEqualTo(productEntities.size());
  }
  
  @Test
  public void testItFindsAProductById() throws IOException {
    Product product = productsGenerator.saveRandomProductOnDB();

    RequestEntity<?> request = ProductRequestFactory.createFindProductByIdRequest(product.getId()).build();
    JsonResponse response = requestSender.send(request);
    
    ProductDto foundProduct = response.getContent(ProductDto.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThatProductDtoIsEqualToProduct(foundProduct, product);
  }
  
  @Test
  public void testItDoesNotFindAProductById() {
    Long nonExistentProductId = 1L;
    
    RequestEntity<?> request = ProductRequestFactory.createFindProductByIdRequest(nonExistentProductId).build();
    JsonResponse response = requestSender.send(request);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }
  
  @Test
  public void testItCreatesAProduct() throws IOException {
    MultiPartFormBuilder form = productsFormUtils.generateRandomProductCreateRequestForm();

    RequestEntity<?> request = ProductRequestFactory.createProductCreateRequest(form).build();
    JsonResponse response = requestSender.send(request);

    ProductDto createdProduct = response.getContent(ProductDto.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    assertThatProductDtoIsEqualToProduct(createdProduct, form);
    assertThat(createdProduct.getImages().size())
        .isEqualTo((form.getAll(IMAGES_KEY)).size());
  }
  
  @Test
  public void testItUpdatesAProduct() throws IOException {
    Product productToUpdate = productsGenerator.saveRandomProductOnDB();
    MultiPartFormBuilder form = productsFormUtils.generateRandomProductUpdateRequestForm();

    RequestEntity<?> request = ProductRequestFactory.createProductUpdateRequest(productToUpdate.getId(), form).build();
    JsonResponse response = requestSender.send(request);
    
    ProductDto updatedProduct = response.getContent(ProductDto.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThatProductDtoIsEqualToProduct(updatedProduct, form);
    assertThat(updatedProduct.getImages().size()).isEqualTo(
        (form.getAll(IMAGES_KEY)).size() + productToUpdate.getImages().size());
  }
  
  @Test
  public void testItUpdatesAProductImages() throws IOException {
    Product product = productsGenerator.saveRandomProductOnDB();
    MultiPartFormBuilder form = productsFormUtils.generateRandomProductUpdateRequestForm();
    form.add("imagesIdsToDelete", getIdsFromImages(product.getImages()));

    RequestEntity<?> request = ProductRequestFactory.createProductUpdateRequest(product.getId(), form).build();
    JsonResponse response = requestSender.send(request);
    
    ProductDto updatedProduct = response.getContent(ProductDto.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThatProductDtoIsEqualToProduct(updatedProduct, form);
    assertThat(updatedProduct.getImages().size())
        .isEqualTo((form.getAll(IMAGES_KEY)).size());
  }
  
  @Test
  public void testItReturnsNotFoundWhenUpdating() {
    Long nonExistentId = 1L;

    MultiPartFormBuilder form = new MultiPartFormBuilder();
    form.add("name", "some name").add("description", "some description");

    RequestEntity<?> request = ProductRequestFactory.createProductUpdateRequest(nonExistentId, form).build();
    JsonResponse response = requestSender.send(request);
   
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }
  
  @Test
  public void testItDeletesAProduct() {
    Product product = productsGenerator.saveRandomProductOnDB();

    RequestEntity<?> request = ProductRequestFactory.createProductDeleteRequest(product.getId()).build();
    JsonResponse response = requestSender.send(request);
    
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    
    response = requestSender.send(request);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }
  
  private void assertThatProductDtoIsEqualToProduct(ProductDto expectedProduct,
      Product product) {
    assertThat(expectedProduct.getName()).isEqualTo(product.getName());
    assertThat(expectedProduct.getDescription()).isEqualTo(product.getDescription());
    assertThat(expectedProduct.getPrice()).isEqualTo(product.getPrice());
    assertThat(expectedProduct.getPriceSale()).isEqualTo(product.getPriceSale());
    assertThat(expectedProduct.isInSale()).isEqualTo(product.isInSale());
    assertThat(expectedProduct.isActive()).isEqualTo(product.isActive());
    assertThat(expectedProduct.getStock()).isEqualTo(product.getStock());
  }

  private void assertThatProductDtoIsEqualToProduct(ProductDto expectedProduct,
      MultiPartFormBuilder form) {
    assertThat(expectedProduct.getName()).isEqualTo(form.get("name"));
    assertThat(expectedProduct.getDescription()).isEqualTo(form.get("description"));
    assertThat(expectedProduct.getPrice()).isEqualTo(form.get("price"));
    assertThat(expectedProduct.getPriceSale()).isEqualTo(form.get("priceSale"));
    assertThat(expectedProduct.isInSale()).isEqualTo(form.get("inSale"));
    assertThat(expectedProduct.isActive()).isEqualTo(form.get("active"));
    assertThat(expectedProduct.getStock()).isEqualTo(form.get("stock"));
    assertThat(expectedProduct.getCategory().getId()).isEqualTo(form.get("categoryId"));
  }

  private List<Long> getIdsFromImages(List<Image> images) {
    List<Long> imagesIds = new ArrayList<>();
    images.forEach(image -> imagesIds.add(image.getId()));
    return imagesIds;
  }
}
