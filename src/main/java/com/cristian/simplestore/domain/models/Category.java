package com.cristian.simplestore.domain.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Category {

  private Long id;

  private String name;

  private Category parent;

  private List<Product> products = new ArrayList<>();

  private Image image;

  public Category(Long id, String name, Image image, Category parent) {
    this(name, image, parent);
    this.id = id;
  }

  public Category(String name, Image image, Category parent) {
    this.name = name;
    this.image = image;
    this.parent = parent;
  }

  public void addProduct(Product product) {
    products.add(product);
    product.setCategory(this);
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Image getImage() {
    return image;
  }

  public Category getParent() {
    return parent;
  }

  /**
   * verify if the given category is a sub category of the current category
   * 
   * @param category
   * @return true if the given category is a sub category of the current object
   */
  public boolean hasSubcategory(Category category) {
    if (category == null)
      return false;

    Category currentCategory = category;

    while (currentCategory.getParent() != null) {
      if (currentCategory.getParent().getId() == id) {
        return true;
      }
      currentCategory = currentCategory.getParent();
    }
    return false;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Category category = (Category) o;
    return Objects.equals(name, category.name) && Objects.equals(id, category.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, id);
  }
}
