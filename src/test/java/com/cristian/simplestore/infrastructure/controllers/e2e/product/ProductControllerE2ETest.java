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
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProductControllerE2ETest extends BaseIntegrationTest {

  @Autowired
  private ProductGenerator productsGenerator;
  
  @Autowired
  private ProductFormUtils productsFormUtils;
  
  @Autowired
  private JsonRequestSender requestSender;
      
  @Test
  public void testItFindsAllProducts()
      throws JsonParseException, JsonMappingException, IOException {
    int MAX_PRODUCTS_SIZE = 4;
    List<Product> productEntities = productsGenerator.saveRandomProductsOnDB(MAX_PRODUCTS_SIZE);

    RequestEntity<?> request = ProductRequestFactory.createFindAllProductsRequest().build();
    JsonResponse response = requestSender.send(request);
    
    List<?> foundProducts =
        (List<?>) response.getContent(List.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(foundProducts.size()).isEqualTo(productEntities.size());
  }
  
  @Test
  public void testItFindsAProductById()
      throws JsonParseException, JsonMappingException, IOException {
    Product Product = productsGenerator.saveRandomProductOnDB();

    RequestEntity<?> request = ProductRequestFactory.createFindProductByIdRequest(Product.getId()).build();
    JsonResponse response = requestSender.send(request);
    
    ProductDto foundProduct = (ProductDto) response
        .getContent(ProductDto.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThatProductDtoIsEqualToProduct(foundProduct, Product);
  }
  
  @Test
  public void testItDoesNotFindAProductById()
      throws JsonParseException, JsonMappingException, IOException {
    Long nonExistentProductId = 1L;
    
    RequestEntity<?> request = ProductRequestFactory.createFindProductByIdRequest(nonExistentProductId).build();
    JsonResponse response = requestSender.send(request);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }
  
  @Test
  public void testItCreatesAProduct() throws JsonParseException, JsonMappingException, IOException {
    MultiPartFormBuilder form = productsFormUtils.generateRandomProductCreateRequestForm();

    RequestEntity<?> request = ProductRequestFactory.createProductCreateRequest(form).build();
    JsonResponse response = requestSender.send(request);

    ProductDto createdProduct = (ProductDto) response
        .getContent(ProductDto.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    assertThatProductDtoIsEqualToProduct(createdProduct, form);
    assertThat(createdProduct.getImages().size())
        .isEqualTo(((List<?>) form.getAll("images")).size());
  }
  
  @Test
  public void testItUpdatesAProduct() throws JsonParseException, JsonMappingException, IOException {
    Product productToUpdate = productsGenerator.saveRandomProductOnDB();
    MultiPartFormBuilder form = productsFormUtils.generateRandomProductUpdateRequestForm();

    RequestEntity<?> request = ProductRequestFactory.createProductUpdateRequest(productToUpdate.getId(), form).build();
    JsonResponse response = requestSender.send(request);
    
    ProductDto updatedProduct = (ProductDto) response
        .getContent(ProductDto.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThatProductDtoIsEqualToProduct(updatedProduct, form);
    assertThat(updatedProduct.getImages().size()).isEqualTo(
        ((List<?>) form.getAll("images")).size() + productToUpdate.getImages().size());
  }
  
  @Test
  public void testItUpdatesAProductImages()
      throws JsonParseException, JsonMappingException, IOException {
    Product Product = productsGenerator.saveRandomProductOnDB();
    MultiPartFormBuilder form = productsFormUtils.generateRandomProductUpdateRequestForm();
    form.add("imagesIdsToDelete", getIdsFromImages(Product.getImages()));

    RequestEntity<?> request = ProductRequestFactory.createProductUpdateRequest(Product.getId(), form).build();
    JsonResponse response = requestSender.send(request);
    
    ProductDto updatedProduct = (ProductDto) response
        .getContent(ProductDto.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThatProductDtoIsEqualToProduct(updatedProduct, form);
    assertThat(updatedProduct.getImages().size())
        .isEqualTo(((List<?>) form.getAll("images")).size());
  }
  
  @Test
  public void testItReturnsNotFoundWhenUpdating()
      throws JsonParseException, JsonMappingException, IOException {
    Long nonExistentId = 1L;

    MultiPartFormBuilder form = new MultiPartFormBuilder();
    form.add("name", "some name").add("description", "some description");

    RequestEntity<?> request = ProductRequestFactory.createProductUpdateRequest(nonExistentId, form).build();
    JsonResponse response = requestSender.send(request);
   
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }
  
  @Test
  public void testItDeletesAProduct() throws JsonParseException, JsonMappingException, IOException {
    Product Product = productsGenerator.saveRandomProductOnDB();

    RequestEntity<?> request = ProductRequestFactory.createProductDeleteRequest(Product.getId()).build();
    JsonResponse response = requestSender.send(request);
    
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    
    response = requestSender.send(request);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }
  
  private void assertThatProductDtoIsEqualToProduct(ProductDto expectedProduct,
      Product Product) {
    assertThat(expectedProduct.getName()).isEqualTo(Product.getName());
    assertThat(expectedProduct.getDescription()).isEqualTo(Product.getDescription());
    assertThat(expectedProduct.getPrice()).isEqualTo(Product.getPrice());
    assertThat(expectedProduct.getPriceSale()).isEqualTo(Product.getPriceSale());
    assertThat(expectedProduct.isInSale()).isEqualTo(Product.isInSale());
    assertThat(expectedProduct.isActive()).isEqualTo(Product.isActive());
    assertThat(expectedProduct.getStock()).isEqualTo(Product.getStock());
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
    images.forEach((image) -> imagesIds.add(image.getId()));
    return imagesIds;
  }
}
