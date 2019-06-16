package com.cristian.simplestore.unit.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.cristian.simplestore.BaseTest;
import com.cristian.simplestore.business.services.product.ProductService;
import com.cristian.simplestore.persistence.entities.CategoryEntity;
import com.cristian.simplestore.persistence.entities.ImageEntity;
import com.cristian.simplestore.persistence.entities.ProductEntity;
import com.cristian.simplestore.utils.product.ProductGenerator;
import com.cristian.simplestore.utils.product.ProductRequestUtils;
import com.cristian.simplestore.web.dto.request.product.ProductCreateRequest;
import com.cristian.simplestore.web.dto.request.product.ProductUpdateRequest;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ProductServiceTest extends BaseTest {

  @Autowired
  private ProductService productService;

  @Autowired ProductGenerator productGenerator;
  
  @Autowired ProductRequestUtils productRequestUtils;
  
  @Test
  public void testItFindsAllProducts() throws Exception {
    int MAX_PRODUCTS_SIZE = 4;
    List<ProductEntity> createdProducts = productGenerator.saveRandomProductsOnDB(MAX_PRODUCTS_SIZE);

    List<ProductEntity> expectedProducts = productService.findAll();

    assertThat(expectedProducts.size()).isEqualTo(createdProducts.size());
  }

  @Test
  public void testItFindsAProductById() throws Exception {
    ProductEntity productEntity = productGenerator.saveRandomProductOnDB();

    ProductEntity expectedProduct = productService.findById(productEntity.getId());

    assertThatTwoProductsAreEqual(expectedProduct, productEntity);
  }

  @Test(expected = EntityNotFoundException.class)
  public void testItDoesNotFindAProductById() {
    long nonExistentProductId = 1;
    productService.findById(nonExistentProductId);
  }

  @Test
  public void testItCreatesAProduct() {
    ProductEntity productEntity = productGenerator.generateRandomProduct();

    ProductEntity expectedProduct = productService.create(productEntity);

    assertThatTwoProductsAreEqual(expectedProduct, productEntity);
  }

  @Test
  public void testItCreatesAProductWithForm() {
	ProductCreateRequest productCreateRequest = productRequestUtils.generateRandomProductCreateForm();

    ProductEntity expectedProduct = productService.create(productCreateRequest);

    assertThatTwoProductsAreEqual(expectedProduct, productCreateRequest.getModel());
    assertThat(expectedProduct.getImages().size()).isNotZero();
    assertThat(expectedProduct.getImages().size()).isEqualTo(productCreateRequest.getImages().size());
  }

  @Test
  public void testItUpdatesAProductWithForm() {
    ProductEntity productToUpdate = productGenerator.saveRandomProductOnDB();
    ProductUpdateRequest newProductData =
    		productRequestUtils.generateRandomProductUpdateRequest(productToUpdate.getId());
    int expectedImagesSize = productToUpdate.getImages().size() + newProductData.getNewImages().size();
    
    ProductEntity expectedProduct = productService.update(newProductData);

    assertThatTwoProductsAreEqual(expectedProduct, newProductData.getModel());
    assertThat(expectedProduct.getImages().size()).isNotZero();
    assertThat(expectedProduct.getImages().size()).isEqualTo(expectedImagesSize);
  }

  @Test
  public void testItDeletesAProductImages() {
    ProductEntity productToUpdate = productGenerator.saveRandomProductOnDB();
    ProductUpdateRequest newProductData =
    		productRequestUtils.new ProductUpdateRequestBuilder(productToUpdate.getId()).randomName()
    		.randomDescription()
    		.randomCategory()
    		.randomPriceSale()
    		.active()
    		.imagesIdsToDelete(getIdsFromImages(productToUpdate.getImages()))
    		.build();
    
    ProductEntity expectedProduct = productService.update(newProductData);

    assertThatTwoProductsAreEqual(expectedProduct, newProductData.getModel());
    assertThat(expectedProduct.getImages().size()).isZero();
  }

  @Test(expected = EntityNotFoundException.class)
  public void testItDoesNotUpdateAProductWithForm() {
    long nonExistentProductId = 1;
    ProductUpdateRequest newProductData =
    		productRequestUtils.generateRandomProductUpdateRequest(nonExistentProductId);

    productService.update(newProductData);
  }

  @Test(expected = EntityNotFoundException.class)
  public void testItDeletesAProduct() {
    ProductEntity productEntity = productGenerator.saveRandomProductOnDB();
    productService.deleteById(productEntity.getId());
    productService.findById(productEntity.getId());
  }

  @Test(expected = EntityNotFoundException.class)
  public void testItDoesNotFoundDeleteProduct() {
    long nonExistentProductId = 1;
    productService.deleteById(nonExistentProductId);
  }

  private void assertThatTwoProductsAreEqual(ProductEntity p1, ProductEntity p2) {
    assertThat(p1.getName()).isEqualTo(p2.getName());
    assertThat(p1.getDescription()).isEqualTo(p2.getDescription());
    assertThat(p1.getPrice()).isEqualTo(p2.getPrice());
    assertThat(p1.getPriceSale()).isEqualTo(p2.getPriceSale());
    assertThat(p1.isInSale()).isEqualTo(p2.isInSale());
    assertThat(p1.isActive()).isEqualTo(p2.isActive());
    assertThat(p1.getStock()).isEqualTo(p2.getStock());
    assertTrue(twoCategoriesAreEqual(p1.getCategory(), p2.getCategory()));
  }

  private boolean twoCategoriesAreEqual(CategoryEntity c1, CategoryEntity c2) {
    Optional<CategoryEntity> category1 = Optional.ofNullable(c1);
    Optional<CategoryEntity> category2 = Optional.ofNullable(c2);
    return category1.equals(category2);
  }

  private List<Long> getIdsFromImages(List<ImageEntity> images) {
    List<Long> imagesIds = new ArrayList<>();
    images.forEach((image) -> imagesIds.add(image.getId()));
    return imagesIds;
  }
}
