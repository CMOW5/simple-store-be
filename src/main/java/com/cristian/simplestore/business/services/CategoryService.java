package com.cristian.simplestore.business.services;

import java.util.List;
import org.springframework.data.domain.Page;
import com.cristian.simplestore.persistence.entities.Category;
import com.cristian.simplestore.web.dto.request.category.CategoryCreateRequest;
import com.cristian.simplestore.web.dto.request.category.CategoryUpdateRequest;

public interface CategoryService {

  List<Category> findAll();
  
  Page<Category> findAll(int page, int size);

  Category findById(Long id);

  Category create(Category category);

  Category create(CategoryCreateRequest form);

  Category update(Long id, Category category);

  Category update(CategoryUpdateRequest form);

  void deleteById(Long id);

  long count();
}
