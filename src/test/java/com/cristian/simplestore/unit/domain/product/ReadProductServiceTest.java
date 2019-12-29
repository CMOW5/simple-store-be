package com.cristian.simplestore.unit.domain.product;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.List;
import java.util.Optional;
import org.junit.Test;

import com.cristian.simplestore.domain.product.Product;
import com.cristian.simplestore.domain.product.repository.ProductRepository;
import com.cristian.simplestore.domain.product.service.ReadProductService;
import com.cristian.simplestore.unit.domain.product.databuilder.ProductTestDataBuilder;


public class ReadProductServiceTest {
  
  @Test
  public void testItShowsProduct() {
    // arrange
    Product product = new ProductTestDataBuilder().id(1L).build();
    ProductRepository productRepo = mock(ProductRepository.class);
    
    when(productRepo.findById(product.getId())).thenReturn(Optional.of(product));
    ReadProductService service = new ReadProductService(productRepo);
    
    // act 
    Optional<Product> foundProduct = service.findById(product.getId());
    
    // assert
    verify(productRepo, times(1)).findById(product.getId());
    assertTrue(foundProduct.isPresent());
  }
  
  @Test
  public void testItShowsAllProducts() {
    // arrange
    ProductRepository productRepo = mock(ProductRepository.class);
    
    when(productRepo.findAll()).thenReturn(ProductTestDataBuilder.createProducts(20));
    ReadProductService service = new ReadProductService(productRepo);
    
    // act 
    List<Product> foundProducts = service.findAll();
    
    // assert
    verify(productRepo, times(1)).findAll();
    assertThat(foundProducts.size()).isEqualTo(20);
  }
  
}
