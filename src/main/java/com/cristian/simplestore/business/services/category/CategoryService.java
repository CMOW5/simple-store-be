package com.cristian.simplestore.business.services.category;

import java.util.List;
import org.springframework.data.domain.Page;
import com.cristian.simplestore.persistence.entities.CategoryEntity;
import com.cristian.simplestore.web.dto.request.category.CategoryCreateRequest;
import com.cristian.simplestore.web.dto.request.category.CategoryUpdateRequest;

public interface CategoryService {

  List<CategoryEntity> findAll();
  
  Page<CategoryEntity> findAll(int page, int size);

  CategoryEntity findById(Long id);

  CategoryEntity create(CategoryEntity category);

  CategoryEntity create(CategoryCreateRequest form);

  CategoryEntity update(Long id, CategoryEntity category);

  CategoryEntity update(CategoryUpdateRequest form);

  void deleteById(Long id);

  long count();
}
