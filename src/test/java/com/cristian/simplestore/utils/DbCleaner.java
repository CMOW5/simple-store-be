package com.cristian.simplestore.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.cristian.simplestore.persistence.respositories.CategoryRepository;
import com.cristian.simplestore.persistence.respositories.ImageRepository;
import com.cristian.simplestore.persistence.respositories.ProductRepository;

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
