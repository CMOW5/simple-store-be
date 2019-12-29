package com.cristian.simplestore.unit.domain.category.databuilder;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.cristian.simplestore.domain.category.Category;
import com.cristian.simplestore.domain.image.Image;

public class CategoryTestDataBuilder {
	Long id = 999L;
    String name = "default cat name";
    Image image;
    Category parent;
    Category category;
    
    public CategoryTestDataBuilder() {
    	this.category = new Category(id, name, image, parent);
    }
    
    public CategoryTestDataBuilder id(Long id) {
        this.id = id;
        this.category = new Category(id, name, image, parent);
        return this;
    }

    public CategoryTestDataBuilder name(String name) {
        this.name = name;
        this.category = new Category(id, name, image, parent);
        return this;
    }

    public CategoryTestDataBuilder image(Image image) {
        this.image = image;
        this.category = new Category(id, name, image, parent);
        return this;
    }

    public CategoryTestDataBuilder parent(Category parent) {
        this.parent = parent;
        this.category.setParent(parent);
        return this;
    }

    public Category build() {
        return category;
    }
    
    public static List<Category> createCategories(int size) {
      return Stream.iterate(size, i -> i++)
          .map((i) -> new CategoryTestDataBuilder().build())
          .limit(size)
          .collect(Collectors.toList());
    }
}