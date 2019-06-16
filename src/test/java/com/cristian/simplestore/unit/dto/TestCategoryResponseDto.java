package com.cristian.simplestore.unit.dto;

import static org.assertj.core.api.Assertions.assertThat;
import javax.transaction.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.cristian.simplestore.BaseTest;
import com.cristian.simplestore.persistence.entities.CategoryEntity;
import com.cristian.simplestore.utils.category.CategoryGenerator;
import com.cristian.simplestore.web.dto.response.CategoryResponse;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class TestCategoryResponseDto extends BaseTest {

  @Autowired
  private CategoryGenerator categoryGenerator;

  @Test
  public void convertEntityToDto() {
	CategoryEntity category = categoryGenerator.new Builder().randomName().randomImage().randomParent().save(); 
    CategoryResponse categoryDto = CategoryResponse.of(category);

    assertThatCategoryAndDtoDataAreEqual(category, categoryDto);
  }

  private void assertThatCategoryAndDtoDataAreEqual(CategoryEntity category,
      CategoryResponse categoryDto) {
    assertThat(category.getId()).isEqualTo(categoryDto.getId());
    assertThat(category.getName()).isEqualTo(categoryDto.getName());
    assertThat(category.getParentCategory().getId())
        .isEqualTo(categoryDto.getParentCategory().getId());
    assertThat(category.getParentCategory().getName())
        .isEqualTo(categoryDto.getParentCategory().getName());
    assertThat(category.getImage().getId()).isEqualTo(categoryDto.getImage().getId());
  }


}
