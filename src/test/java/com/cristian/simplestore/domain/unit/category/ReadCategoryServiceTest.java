package com.cristian.simplestore.domain.unit.category;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.List;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import com.cristian.simplestore.domain.models.Category;
import com.cristian.simplestore.domain.ports.repository.CategoryRepository;
import com.cristian.simplestore.domain.services.category.ReadCategoryService;
import com.cristian.simplestore.domain.unit.databuilder.CategoryTestDataBuilder;

@RunWith(SpringRunner.class)
public class ReadCategoryServiceTest {
  
  @Test
  public void testItShowsCategory() {
    // arrange
    Category category = new CategoryTestDataBuilder().build();
    CategoryRepository categoryRepo = mock(CategoryRepository.class);
    
    when(categoryRepo.find(category)).thenReturn(Optional.of(category));
    ReadCategoryService service = new ReadCategoryService(categoryRepo);
    
    // act 
    Optional<Category> foundCategory = service.execute(category);
    
    // assert
    assertTrue(foundCategory.isPresent());
  }
  
  @Test
  public void testItShowsCategories() {
    // arrange
    CategoryRepository categoryRepo = mock(CategoryRepository.class);
    
    when(categoryRepo.findAll()).thenReturn(CategoryTestDataBuilder.createCategories(20));
    ReadCategoryService service = new ReadCategoryService(categoryRepo);
    
    // act 
    List<Category> foundCategories = service.execute();
    
    // assert
    assertThat(foundCategories.size()).isEqualTo(20);
  }
  
}
