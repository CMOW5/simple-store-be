package com.cristian.simplestore.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.cristian.simplestore.persistence.repositories.CategoryRepository;
import com.cristian.simplestore.persistence.repositories.ImageRepository;
import com.cristian.simplestore.persistence.repositories.ProductRepository;

@Component
public class DbCleaner {

  @Autowired
  CategoryRepository categoryRepository;

  @Autowired
  ProductRepository productRepository;

  @Autowired
  ImageRepository imageRepository;

  public void cleanAllTables() {
    categoryRepository.deleteAll();
    imageRepository.deleteAll();
    productRepository.deleteAll();
  }

  public void cleanCategoriesTable() {
    categoryRepository.deleteAll();
  }

  public void cleanProductsTable() {
    productRepository.deleteAll();
  }

  public void cleanImagesTable() {
    imageRepository.deleteAll();
  }
}
