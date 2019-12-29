package com.cristian.simplestore.unit.domain.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.Test;

import com.cristian.simplestore.domain.product.Product;
import com.cristian.simplestore.domain.product.repository.ProductRepository;
import com.cristian.simplestore.domain.product.service.CreateProductService;
import com.cristian.simplestore.unit.domain.product.databuilder.ProductTestDataBuilder;

public class CreateProductServiceTest {
  
  @Test
  public void testItCreatesAProduct() {
    Product product = new ProductTestDataBuilder().build();
    ProductRepository productRepo = mock(ProductRepository.class);
    when(productRepo.save(product)).thenReturn(product);
    
    CreateProductService service = new CreateProductService(productRepo);
    
    // act
    Product createdProduct = service.create(product);
    
    // assert
    verify(productRepo, times(1)).save(product);
    assertThat(createdProduct.getName()).isEqualTo(product.getName());
  }
}
