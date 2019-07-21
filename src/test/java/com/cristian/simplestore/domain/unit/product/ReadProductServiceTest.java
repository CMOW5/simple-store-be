package com.cristian.simplestore.domain.unit.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.List;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import com.cristian.simplestore.domain.models.Product;
import com.cristian.simplestore.domain.ports.repository.ProductRepository;
import com.cristian.simplestore.domain.services.product.ReadProductService;
import com.cristian.simplestore.domain.unit.databuilder.ProductTestDataBuilder;

@RunWith(SpringRunner.class)
public class ReadProductServiceTest {
  
  @Test
  public void testItShowsProduct() {
    // arrange
    Product product = new ProductTestDataBuilder().build();
    ProductRepository productRepo = mock(ProductRepository.class);
    
    when(productRepo.find(product)).thenReturn(Optional.of(product));
    ReadProductService service = new ReadProductService(productRepo);
    
    // act 
    Optional<Product> foundProduct = service.execute(product);
    
    // assert
    assertTrue(foundProduct.isPresent());
  }
  
  @Test
  public void testItShowsCategories() {
    // arrange
    ProductRepository productRepo = mock(ProductRepository.class);
    
    when(productRepo.findAll()).thenReturn(ProductTestDataBuilder.createProducts(20));
    ReadProductService service = new ReadProductService(productRepo);
    
    // act 
    List<Product> foundProducts = service.execute();
    
    // assert
    assertThat(foundProducts.size()).isEqualTo(20);
  }
  
}
