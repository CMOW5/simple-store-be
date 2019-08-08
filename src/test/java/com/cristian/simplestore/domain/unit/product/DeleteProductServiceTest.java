package com.cristian.simplestore.domain.unit.product;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.junit.Test;

import com.cristian.simplestore.domain.product.Product;
import com.cristian.simplestore.domain.product.repository.ProductRepository;
import com.cristian.simplestore.domain.product.service.DeleteProductService;
import com.cristian.simplestore.domain.unit.databuilder.ProductTestDataBuilder;

public class DeleteProductServiceTest {
  
  @Test
  public void testItDeletesAProduct() {
    // arrange
    Product product = new ProductTestDataBuilder().build();
    ProductRepository productRepo = mock(ProductRepository.class);
    
    when(productRepo.find(product)).thenReturn(Optional.of(product));
    DeleteProductService service = new DeleteProductService(productRepo);
    
    // act 
    service.execute(product);
  }
  
}
