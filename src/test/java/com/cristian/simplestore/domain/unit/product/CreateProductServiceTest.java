package com.cristian.simplestore.domain.unit.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.Test;
import com.cristian.simplestore.domain.models.Product;
import com.cristian.simplestore.domain.ports.repository.ProductRepository;
import com.cristian.simplestore.domain.services.product.CreateProductService;
import com.cristian.simplestore.domain.unit.databuilder.ProductTestDataBuilder;

public class CreateProductServiceTest {
  
  @Test
  public void testItCreatesAProduct() {
    Product product = new ProductTestDataBuilder().build();
    ProductRepository productRepo = mock(ProductRepository.class);
    when(productRepo.save(product)).thenReturn(product);
    
    CreateProductService service = new CreateProductService(productRepo);
    
    // act
    Product createdProduct = service.execute(product);
    
    // assert
    assertThat(createdProduct.getName()).isEqualTo(product.getName());
  }
}
