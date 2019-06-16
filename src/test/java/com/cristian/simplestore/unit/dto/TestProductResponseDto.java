package com.cristian.simplestore.unit.dto;

import static org.assertj.core.api.Assertions.assertThat;
import javax.transaction.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.cristian.simplestore.BaseTest;
import com.cristian.simplestore.persistence.entities.ProductEntity;
import com.cristian.simplestore.utils.product.ProductGenerator;
import com.cristian.simplestore.web.dto.response.ProductResponse;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class TestProductResponseDto extends BaseTest {

  @Autowired
  private ProductGenerator productGenerator;

  @Test
  public void convertEntityToDto() {
    ProductEntity productEntity = productGenerator.saveRandomProductOnDB();
    ProductResponse productDto = ProductResponse.from(productEntity);

    assertThatImageAndDtoDataAreEqual(productEntity, productDto);
  }

  private void assertThatImageAndDtoDataAreEqual(ProductEntity productEntity, ProductResponse productDto) {
    assertThat(productEntity.getId()).isEqualTo(productDto.getId());
    assertThat(productEntity.getName()).isEqualTo(productDto.getName());
    assertThat(productEntity.getDescription()).isEqualTo(productDto.getDescription());
    assertThat(productEntity.getPrice()).isEqualTo(productDto.getPrice());
    assertThat(productEntity.getPriceSale()).isEqualTo(productDto.getPriceSale());
    assertThat(productEntity.isInSale()).isEqualTo(productDto.isInSale());
    assertThat(productEntity.isActive()).isEqualTo(productDto.isActive());
    assertThat(productEntity.getStock()).isEqualTo(productDto.getStock());
    assertThat(productEntity.getCategory().getId()).isEqualTo(productDto.getCategory().getId());
    assertThat(productEntity.getImages().size()).isEqualTo(productDto.getImages().size());
  }
}
