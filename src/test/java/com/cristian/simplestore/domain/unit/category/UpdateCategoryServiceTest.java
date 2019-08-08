package com.cristian.simplestore.domain.unit.category;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.*;
import org.junit.Test;

import com.cristian.simplestore.domain.category.Category;
import com.cristian.simplestore.domain.category.repository.CategoryRepository;
import com.cristian.simplestore.domain.category.services.UpdateCategoryService;
import com.cristian.simplestore.domain.unit.databuilder.CategoryTestDataBuilder;

public class UpdateCategoryServiceTest {

	@Test
	public void testItUpdatesACategory() {
		// arrange
		Category storedCategory = new CategoryTestDataBuilder().build();
		Category newCategoryData = new CategoryTestDataBuilder().name("new name").id(storedCategory.getId()).build();
		CategoryRepository categoryRepository = mock(CategoryRepository.class);
		
		when(categoryRepository.findById(storedCategory.getId())).thenReturn(Optional.of(storedCategory));
		when(categoryRepository.save((Category) notNull())).thenReturn(newCategoryData);
		UpdateCategoryService service = new UpdateCategoryService(categoryRepository);

		// act
		Category updatedCategory = service.execute(storedCategory.getId(), newCategoryData.getName(), null, null);

		// assert
		assertThat(updatedCategory.getName()).isEqualTo(newCategoryData.getName());
	}
}
