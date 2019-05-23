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
import com.cristian.simplestore.persistence.entities.Category;
import com.cristian.simplestore.persistence.entities.Image;
import com.cristian.simplestore.persistence.entities.Product;
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
    List<Product> createdProducts = productGenerator.saveRandomProductsOnDB(MAX_PRODUCTS_SIZE);

    List<Product> expectedProducts = productService.findAll();

    assertThat(expectedProducts.size()).isEqualTo(createdProducts.size());
  }

  @Test
  public void testItFindsAProductById() throws Exception {
    Product product = productGenerator.saveRandomProductOnDB();

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
    Product product = productGenerator.generateRandomProduct();

    Product expectedProduct = productService.create(product);

    assertThatTwoProductsAreEqual(expectedProduct, product);
  }

  @Test
  public void testItCreatesAProductWithForm() {
	ProductCreateRequest productCreateRequest = productRequestUtils.generateRandomProductCreateForm();

    Product expectedProduct = productService.create(productCreateRequest);

    assertThatTwoProductsAreEqual(expectedProduct, productCreateRequest.getModel());
    assertThat(expectedProduct.getImages().size()).isNotZero();
    assertThat(expectedProduct.getImages().size()).isEqualTo(productCreateRequest.getImages().size());
  }

  @Test
  public void testItUpdatesAProductWithForm() {
    Product productToUpdate = productGenerator.saveRandomProductOnDB();
    ProductUpdateRequest newProductData =
    		productRequestUtils.generateRandomProductUpdateRequest(productToUpdate.getId());
    int expectedImagesSize = productToUpdate.getImages().size() + newProductData.getNewImages().size();
    
    Product expectedProduct = productService.update(newProductData);

    assertThatTwoProductsAreEqual(expectedProduct, newProductData.getModel());
    assertThat(expectedProduct.getImages().size()).isNotZero();
    assertThat(expectedProduct.getImages().size()).isEqualTo(expectedImagesSize);
  }

  @Test
  public void testItDeletesAProductImages() {
    Product productToUpdate = productGenerator.saveRandomProductOnDB();
    ProductUpdateRequest newProductData =
    		productRequestUtils.new ProductUpdateRequestBuilder(productToUpdate.getId()).randomName()
    		.randomDescription()
    		.randomCategory()
    		.randomPriceSale()
    		.active()
    		.imagesIdsToDelete(getIdsFromImages(productToUpdate.getImages()))
    		.build();
    
    Product expectedProduct = productService.update(newProductData);

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
    Product product = productGenerator.saveRandomProductOnDB();
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

  private List<Long> getIdsFromImages(List<Image> images) {
    List<Long> imagesIds = new ArrayList<>();
    images.forEach((image) -> imagesIds.add(image.getId()));
    return imagesIds;
  }
}
