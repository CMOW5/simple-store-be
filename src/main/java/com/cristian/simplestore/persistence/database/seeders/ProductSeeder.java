package com.cristian.simplestore.persistence.database.seeders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.cristian.simplestore.persistence.entities.CategoryEntity;
import com.cristian.simplestore.persistence.entities.ImageEntity;
import com.cristian.simplestore.persistence.entities.ProductEntity;
import com.cristian.simplestore.persistence.repositories.ProductRepository;
import com.github.javafaker.Faker;

@Service
public class ProductSeeder {
  
  private static double MAX_PRICE = 10000000000.0;
  private static int MAX_STOCK = 100000000;

  private Faker faker = new Faker();
  
  @Autowired
  CategorySeeder categorySeeder;
  
  @Autowired
  ProductRepository productRepository;

  public String generateRandomName() {
    return faker.name().name();
  }

  public String generateRandomDescription() {
    return faker.lorem().sentence();
  }

  public double generateRandomPrice() {
    return Double.valueOf(faker.commerce().price(0, MAX_PRICE));
  }

  public long generateRandomStock() {
    return (long) faker.number().numberBetween(0, MAX_STOCK);
  }
  
  public ProductEntity saveRandomProductOnDB() {
    ProductEntity productEntity = new ProductEntity();
    productEntity.setName(generateRandomName());
    productEntity.setDescription(generateRandomDescription());
    productEntity.setPrice(generateRandomPrice());
    productEntity.setPriceSale(generateRandomPrice());
    productEntity.setStock(generateRandomStock());
    productEntity.setActive(true);
    CategoryEntity category = categorySeeder.createRandomCategoryOnDB();
    productEntity.setCategory(category);
    ImageEntity image1 = categorySeeder.generateRandomImageOnDB();
    ImageEntity image2 = categorySeeder.generateRandomImageOnDB();
    productEntity.addImage(image1);
    productEntity.addImage(image2);
    return productRepository.save(productEntity);
}

  public void seed(int size) {
    for (int i = 0; i < size; i++) {
      try {
        saveRandomProductOnDB();
      } catch (DataIntegrityViolationException e) {
      }
    }
  }
}
