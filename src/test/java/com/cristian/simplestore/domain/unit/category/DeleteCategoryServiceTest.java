package com.cristian.simplestore.domain.unit.category;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import com.cristian.simplestore.domain.models.Category;
import com.cristian.simplestore.domain.ports.repository.CategoryRepository;
import com.cristian.simplestore.domain.services.category.DeleteCategoryService;
import com.cristian.simplestore.domain.unit.databuilder.CategoryTestDataBuilder;

@RunWith(SpringRunner.class)
public class DeleteCategoryServiceTest {
  
  @Test
  public void testItDeletesACategory() {
    // arrange
    Category category = new CategoryTestDataBuilder().build();
    CategoryRepository categoryRepo = mock(CategoryRepository.class);
    
    when(categoryRepo.find(category)).thenReturn(Optional.of(category));
    DeleteCategoryService service = new DeleteCategoryService(categoryRepo);
    
    // act 
    service.execute(category);
  }
  
}
