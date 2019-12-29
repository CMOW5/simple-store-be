package com.cristian.simplestore.unit.domain.category;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.junit.Test;

import com.cristian.simplestore.domain.category.Category;
import com.cristian.simplestore.domain.category.repository.CategoryRepository;
import com.cristian.simplestore.domain.category.services.DeleteCategoryService;
import com.cristian.simplestore.unit.domain.category.databuilder.CategoryTestDataBuilder;

public class DeleteCategoryServiceTest {
  
  @Test
  public void testItDeletesACategory() {
    // arrange
    Category category = new CategoryTestDataBuilder().build();
    CategoryRepository categoryRepo = mock(CategoryRepository.class);
    
    DeleteCategoryService service = new DeleteCategoryService(categoryRepo);
    
    // act 
    service.deleteById(category.getId());
    
    verify(categoryRepo, times(1)).deleteById(category.getId());
  }
  
}
