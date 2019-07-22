package com.cristian.simplestore.domain.unit.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.*;
import org.junit.Test;
import com.cristian.simplestore.domain.models.Product;
import com.cristian.simplestore.domain.ports.repository.ProductRepository;
import com.cristian.simplestore.domain.services.product.UpdateProductService;
import com.cristian.simplestore.domain.unit.databuilder.ProductTestDataBuilder;

public class UpdateProductServiceTest {
  
  @Test
  public void testItUpdatesACategory() {
    // arrange
    Product storedProduct = new ProductTestDataBuilder().build();
    Product toUpdateProduct = new ProductTestDataBuilder().name("new name").build();
    ProductRepository productRepo = mock(ProductRepository.class);
    
    when(productRepo.find(toUpdateProduct)).thenReturn(Optional.of(storedProduct));
    when(productRepo.update((Product)notNull())).thenReturn(toUpdateProduct);
    UpdateProductService service = new UpdateProductService(productRepo);
    
    // act 
    Product updatedProduct = service.execute(toUpdateProduct);
    
    // assert
    assertThat(updatedProduct.getName()).isEqualTo(toUpdateProduct.getName());
  }
  
}
