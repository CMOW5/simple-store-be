package com.cristian.simplestore.domain.unit.product;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import com.cristian.simplestore.domain.models.Product;
import com.cristian.simplestore.domain.ports.repository.ProductRepository;
import com.cristian.simplestore.domain.services.product.DeleteProductService;
import com.cristian.simplestore.domain.unit.databuilder.ProductTestDataBuilder;

@RunWith(SpringRunner.class)
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
