package com.cristian.simplestore.domain.unit.category;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import com.cristian.simplestore.domain.models.Category;
import com.cristian.simplestore.domain.ports.repository.CategoryRepository;
import com.cristian.simplestore.domain.services.category.UpdateCategoryService;
import com.cristian.simplestore.domain.unit.databuilder.CategoryTestDataBuilder;

@RunWith(SpringRunner.class)
public class UpdateCategoryServiceTest {
  
  @Test
  public void testItUpdatesACategory() {
    // arrange
    Category storedCategory = new CategoryTestDataBuilder().build();
    Category toUpdateCategory = new CategoryTestDataBuilder().name("new name").build();
    CategoryRepository categoryRepo = mock(CategoryRepository.class);
    
    when(categoryRepo.find(toUpdateCategory)).thenReturn(Optional.of(storedCategory));
    when(categoryRepo.update((Category)notNull())).thenReturn(toUpdateCategory);
    UpdateCategoryService service = new UpdateCategoryService(categoryRepo);
    
    // act 
    Category updatedCategory = service.execute(toUpdateCategory);
    
    // assert
    assertThat(updatedCategory.getName()).isEqualTo(toUpdateCategory.getName());
  }
  
}
