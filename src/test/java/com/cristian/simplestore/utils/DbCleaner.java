package com.cristian.simplestore.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.cristian.simplestore.persistence.repositories.CategoryRepository;
import com.cristian.simplestore.persistence.repositories.ImageRepository;
import com.cristian.simplestore.persistence.repositories.ProductRepository;
import com.cristian.simplestore.persistence.repositories.UserRepository;

@Component
public class DbCleaner {

  @Autowired
  CategoryRepository categoryRepository;

  @Autowired
  ProductRepository productRepository;

  @Autowired
  ImageRepository imageRepository;
  
  @Autowired 
  UserRepository userRepository;

  public void cleanAllTables() {
    categoryRepository.deleteAll();
    imageRepository.deleteAll();
    productRepository.deleteAll();
    userRepository.deleteAll();
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

  public void cleanUsersTable() {
    userRepository.deleteAll();
  }
}
