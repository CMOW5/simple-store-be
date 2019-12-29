package com.cristian.simplestore.unit.domain.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.*;
import org.junit.Test;

import com.cristian.simplestore.domain.product.Product;
import com.cristian.simplestore.domain.product.repository.ProductRepository;
import com.cristian.simplestore.domain.product.service.UpdateProductService;
import com.cristian.simplestore.unit.domain.product.databuilder.ProductTestDataBuilder;

public class UpdateProductServiceTest {
  
  @Test
  public void testItUpdatesACategory() {
    // arrange
    Product storedProduct = new ProductTestDataBuilder().build();
    Product toUpdateProduct = new ProductTestDataBuilder().name("new name").build();
    ProductRepository productRepo = mock(ProductRepository.class);
    
    when(productRepo.find(toUpdateProduct)).thenReturn(Optional.of(storedProduct));
    when(productRepo.save((Product)notNull())).thenReturn(toUpdateProduct);
    UpdateProductService service = new UpdateProductService(productRepo);
    
    // act 
    Product updatedProduct = service.update(toUpdateProduct);
    
    // assert
    verify(productRepo, times(1)).save(toUpdateProduct);
    assertThat(updatedProduct.getName()).isEqualTo(toUpdateProduct.getName());
  }  
}
