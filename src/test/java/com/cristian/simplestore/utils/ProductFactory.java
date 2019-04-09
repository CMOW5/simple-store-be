package com.cristian.simplestore.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.cristian.simplestore.persistence.entities.Product;
import com.cristian.simplestore.web.dto.request.product.ProductCreateRequest;
import com.cristian.simplestore.web.dto.request.product.ProductUpdateRequest;
import com.github.javafaker.Faker;

@Component
public class ProductFactory {

  private Faker faker = new Faker();
  private static double MAX_PRICE = 10000000000.0;
  private static int MAX_STOCK = 100000000;

  @Autowired
  CategoryTestsUtils categoryUtils;

  @Autowired
  ImageTestsUtils imageUtils;

  public Product generateRandomProduct() {
    Product product = new Product();

    product.setName(generateRandomName());
    product.setDescription(generateRandomDescription());
    product.setPrice(generateRandomPrice());
    product.setPriceSale(generateRandomPrice());
    product.setInSale(false);
    product.setActive(true);
    product.setStock(generateRandomStock());
    product.setCategory(categoryUtils.saveRandomCategoryOnDb());

    return product;
  }

  public ProductCreateRequest generateRandomProductCreateForm() {
    ProductCreateRequest form = new ProductCreateRequest();

    form.setName(generateRandomName());
    form.setDescription(generateRandomDescription());
    form.setPrice(generateRandomPrice());
    form.setPriceSale(generateRandomPrice());
    form.setInSale(false);
    form.setActive(true);
    form.setStock(generateRandomStock());
    form.setCategory(categoryUtils.saveRandomCategoryOnDb());
    form.setImages(imageUtils.generateMockMultiPartFiles(2));

    return form;
  }

  public ProductUpdateRequest generateRandomProductUpdateRequest(Long id) {
    ProductUpdateRequest form = new ProductUpdateRequest();

    form.setId(id);
    form.setName(generateRandomName());
    form.setDescription(generateRandomDescription());
    form.setPrice(generateRandomPrice());
    form.setPriceSale(generateRandomPrice());
    form.setInSale(false);
    form.setActive(true);
    form.setStock(generateRandomStock());
    form.setCategory(categoryUtils.saveRandomCategoryOnDb());
    form.setNewImages(imageUtils.generateMockMultiPartFiles(2));

    return form;
  }

  private String generateRandomName() {
    return faker.name().name();
  }

  private String generateRandomDescription() {
    return faker.lorem().sentence();
  }

  private double generateRandomPrice() {
    return Double.valueOf(faker.commerce().price(0, MAX_PRICE));
  }

  private long generateRandomStock() {
    return (long) faker.number().numberBetween(0, MAX_STOCK);
  }
}
