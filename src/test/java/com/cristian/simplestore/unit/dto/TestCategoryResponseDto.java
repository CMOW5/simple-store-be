package com.cristian.simplestore.unit.dto;

import static org.assertj.core.api.Assertions.assertThat;
import javax.transaction.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.cristian.simplestore.BaseTest;
import com.cristian.simplestore.persistence.entities.Category;
import com.cristian.simplestore.utils.CategoryTestsUtils;
import com.cristian.simplestore.web.dto.CategoryResponseDto;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class TestCategoryResponseDto extends BaseTest {

  @Autowired
  private CategoryTestsUtils categoryUtils;

  @Test
  public void convertEntityToDto() {
    Category category = categoryUtils.saveRandomCategoryOnDbWithParent();
    CategoryResponseDto categoryDto = new CategoryResponseDto(category);

    assertThatCategoryAndDtoDataAreEqual(category, categoryDto);
  }

  private void assertThatCategoryAndDtoDataAreEqual(Category category,
      CategoryResponseDto categoryDto) {
    assertThat(category.getId()).isEqualTo(categoryDto.getId());
    assertThat(category.getName()).isEqualTo(categoryDto.getName());
    assertThat(category.getParentCategory().getId())
        .isEqualTo(categoryDto.getParentCategory().get("id"));
    assertThat(category.getParentCategory().getName())
        .isEqualTo(categoryDto.getParentCategory().get("name"));
    assertThat(category.getImage().getId()).isEqualTo(categoryDto.getImage().getId());
  }


}
