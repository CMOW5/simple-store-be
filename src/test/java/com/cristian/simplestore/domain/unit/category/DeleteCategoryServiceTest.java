package com.cristian.simplestore.domain.unit.category;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.junit.Test;

import com.cristian.simplestore.domain.category.Category;
import com.cristian.simplestore.domain.category.repository.CategoryRepository;
import com.cristian.simplestore.domain.category.services.DeleteCategoryService;
import com.cristian.simplestore.domain.unit.databuilder.CategoryTestDataBuilder;

public class DeleteCategoryServiceTest {
  
  @Test
  public void testItDeletesACategory() {
    // arrange
    Category category = new CategoryTestDataBuilder().build();
    CategoryRepository categoryRepo = mock(CategoryRepository.class);
    
    when(categoryRepo.find(category)).thenReturn(Optional.of(category));
    DeleteCategoryService service = new DeleteCategoryService(categoryRepo);
    
    // act 
    service.execute(category.getId());
  }
  
}
