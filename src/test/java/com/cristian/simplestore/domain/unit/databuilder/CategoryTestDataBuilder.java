package com.cristian.simplestore.domain.unit.databuilder;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import com.cristian.simplestore.domain.models.Category;
import com.cristian.simplestore.domain.models.Image;

public class CategoryTestDataBuilder {
	Long id = 999L;
    String name = "default cat name";
    Image image;
    Category parentCategory;
    
    public CategoryTestDataBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public CategoryTestDataBuilder name(String name) {
        this.name = name;
        return this;
    }

    public CategoryTestDataBuilder image(Image image) {
        this.image = image;
        return this;
    }

    public CategoryTestDataBuilder parent(Category category) {
        this.parentCategory = category;
        return this;
    }

    public Category build() {
        return new Category(id, name, image, parentCategory);
    }
    
    public static List<Category> createCategories(int size) {
      return Stream.iterate(size, i -> i++)
          .map((i) -> new CategoryTestDataBuilder().build())
          .limit(size)
          .collect(Collectors.toList());
    }
}
