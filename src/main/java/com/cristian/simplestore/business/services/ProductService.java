package com.cristian.simplestore.business.services;

import java.util.List;
import com.cristian.simplestore.persistence.entities.Product;
import com.cristian.simplestore.web.forms.ProductCreateForm;
import com.cristian.simplestore.web.forms.ProductUpdateForm;

public interface ProductService {

  List<Product> findAll();

  Product findById(Long id);

  Product create(Product product);

  Product create(ProductCreateForm form);

  Product update(ProductUpdateForm form);

  void deleteById(Long id);

  public long count();
}
