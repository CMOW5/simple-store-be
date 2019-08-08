package com.cristian.simplestore.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cristian.simplestore.domain.category.repository.CategoryRepository;

@Component
public class DbCleaner {

  @Autowired
  CategoryRepository categoryRepository;

  public void cleanAllTables() {
    categoryRepository.deleteAll();
  }

  public void cleanCategoriesTable() {
    categoryRepository.deleteAll();
  }
}