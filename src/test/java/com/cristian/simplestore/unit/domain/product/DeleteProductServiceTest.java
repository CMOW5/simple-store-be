package com.cristian.simplestore.unit.domain.product;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.junit.Test;

import com.cristian.simplestore.domain.product.Product;
import com.cristian.simplestore.domain.product.repository.ProductRepository;
import com.cristian.simplestore.domain.product.service.DeleteProductService;
import com.cristian.simplestore.unit.domain.product.databuilder.ProductTestDataBuilder;

public class DeleteProductServiceTest {
  
  @Test
  public void testItDeletesAProduct() {
    // arrange
    Product product = new ProductTestDataBuilder().build();
    ProductRepository productRepo = mock(ProductRepository.class);
    
    when(productRepo.find(product)).thenReturn(Optional.of(product));
    DeleteProductService service = new DeleteProductService(productRepo);
    
    // act 
    service.deleteById(product.getId());
    
    // assert
    verify(productRepo, times(1)).deleteById(product.getId());
  }
  
}
