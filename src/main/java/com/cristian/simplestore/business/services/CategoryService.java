package com.cristian.simplestore.business.services;

import java.util.List;
import com.cristian.simplestore.persistence.entities.Category;
import com.cristian.simplestore.web.forms.CategoryCreateForm;
import com.cristian.simplestore.web.forms.CategoryUpdateForm;

public interface CategoryService {

  List<Category> findAll();

  Category findById(Long id);

  Category create(Category category);

  Category create(CategoryCreateForm form);

  Category update(Long id, Category category);

  Category update(CategoryUpdateForm form);

  void deleteById(Long id);

  long count();
}
