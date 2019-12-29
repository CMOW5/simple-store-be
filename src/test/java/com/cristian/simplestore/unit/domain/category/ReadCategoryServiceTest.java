package com.cristian.simplestore.unit.domain.category;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.List;
import java.util.Optional;
import org.junit.Test;

import com.cristian.simplestore.domain.category.Category;
import com.cristian.simplestore.domain.category.repository.CategoryRepository;
import com.cristian.simplestore.domain.category.services.ReadCategoryService;
import com.cristian.simplestore.unit.domain.category.databuilder.CategoryTestDataBuilder;


public class ReadCategoryServiceTest {
  
  @Test
  public void testItShowsCategory() {
    // arrange
    Category category = new CategoryTestDataBuilder().id(1L).build();
    CategoryRepository categoryRepo = mock(CategoryRepository.class);
    
    when(categoryRepo.findById(category.getId())).thenReturn(Optional.of(category));
    ReadCategoryService service = new ReadCategoryService(categoryRepo);
    
    // act 
    Optional<Category> foundCategory = service.findById(category.getId());
    
    // assert
	verify(categoryRepo, times(1)).findById(category.getId());
    assertTrue(foundCategory.isPresent());
  }
  
  @Test
  public void testItShowsCategories() {
    // arrange
    CategoryRepository categoryRepo = mock(CategoryRepository.class);
    
    when(categoryRepo.findAll()).thenReturn(CategoryTestDataBuilder.createCategories(20));
    ReadCategoryService service = new ReadCategoryService(categoryRepo);
    
    // act 
    List<Category> foundCategories = service.findAll();
    
    // assert
    verify(categoryRepo, times(1)).findAll();
    assertThat(foundCategories.size()).isEqualTo(20);
  }
  
}
