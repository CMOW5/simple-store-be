package com.cristian.simplestore.domain.unit.category;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.Test;

import com.cristian.simplestore.domain.category.Category;
import com.cristian.simplestore.domain.category.repository.CategoryRepository;
import com.cristian.simplestore.domain.category.services.CreateCategoryService;
import com.cristian.simplestore.domain.unit.databuilder.CategoryTestDataBuilder;

public class CreateCategoryServiceTest {
  
  @Test
  public void testItCreatesACategory() {
    // arrange
    Category category = new CategoryTestDataBuilder().build();
    CategoryRepository categoryRepo = mock(CategoryRepository.class);
    
    when(categoryRepo.save(category)).thenReturn(category);
    CreateCategoryService service = new CreateCategoryService(categoryRepo);
    
    // act 
    Category createdCategory = service.execute(category);
    
    // assert
    assertThat(createdCategory.getName()).isEqualTo(category.getName());
  }
  
}
