package com.cristian.simplestore.business.services.product;

import java.util.List;
import org.springframework.data.domain.Page;
import com.cristian.simplestore.persistence.entities.ProductEntity;
import com.cristian.simplestore.web.dto.request.product.ProductCreateRequest;
import com.cristian.simplestore.web.dto.request.product.ProductUpdateRequest;

public interface ProductService {

  List<ProductEntity> findAll();
  
  Page<ProductEntity> findAll(int page, int size);

  ProductEntity findById(Long id);

  ProductEntity create(ProductEntity productEntity);

  ProductEntity create(ProductCreateRequest form);

  ProductEntity update(ProductUpdateRequest form);

  void deleteById(Long id);

  public long count();

  
}
