package com.cristian.simplestore.persistence.database.seeders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.cristian.simplestore.persistence.entities.Category;
import com.cristian.simplestore.persistence.entities.Image;
import com.cristian.simplestore.persistence.entities.Product;
import com.cristian.simplestore.persistence.repositories.ProductRepository;
import com.github.javafaker.Faker;

// @Service
public class ProductSeeder {
  
//  private static double MAX_PRICE = 10000000000.0;
//  private static int MAX_STOCK = 100000000;
//
//  private Faker faker = new Faker();
//  
//  @Autowired
//  CategorySeeder categorySeeder;
//  
//  @Autowired
//  ProductRepository productRepository;
//
//  public String generateRandomName() {
//    return faker.name().name();
//  }
//
//  public String generateRandomDescription() {
//    return faker.lorem().sentence();
//  }
//
//  public double generateRandomPrice() {
//    return Double.valueOf(faker.commerce().price(0, MAX_PRICE));
//  }
//
//  public long generateRandomStock() {
//    return (long) faker.number().numberBetween(0, MAX_STOCK);
//  }
//  
//  public Product saveRandomProductOnDB() {
//    Product product = new Product();
//    product.setName(generateRandomName());
//    product.setDescription(generateRandomDescription());
//    product.setPrice(generateRandomPrice());
//    product.setPriceSale(generateRandomPrice());
//    product.setStock(generateRandomStock());
//    product.setActive(true);
//    Category category = categorySeeder.createRandomCategoryOnDB();
//    product.setCategory(category);
//    Image image1 = categorySeeder.generateRandomImageOnDB();
//    Image image2 = categorySeeder.generateRandomImageOnDB();
//    product.addImage(image1);
//    product.addImage(image2);
//    return productRepository.save(product);
//}
//
//  public void seed(int size) {
//    for (int i = 0; i < size; i++) {
//      try {
//        saveRandomProductOnDB();
//      } catch (DataIntegrityViolationException e) {
//      }
//    }
//  }
}
