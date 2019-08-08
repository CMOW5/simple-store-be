package com.cristian.simplestore.domain.unit.databuilder;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.cristian.simplestore.domain.category.Category;
import com.cristian.simplestore.domain.image.Image;
import com.cristian.simplestore.domain.product.Product;

public class ProductTestDataBuilder {
  private String name;
  private String description;
  private double price;
  private double priceSale;
  private boolean inSale;
  private boolean active;
  private Category category;
  private List<Image> images;
  private long stock;

  public ProductTestDataBuilder name(String name) {
    this.name = name;
    return this;
  }

  public ProductTestDataBuilder description(String description) {
    this.description = description;
    return this;
  }

  public ProductTestDataBuilder price(double price) {
    this.price = price;
    return this;
  }

  public ProductTestDataBuilder priceSale(double priceSale) {
    this.priceSale = priceSale;
    return this;
  }

  public ProductTestDataBuilder inSale(boolean inSale) {
    this.inSale = inSale;
    return this;
  }

  public ProductTestDataBuilder active(boolean active) {
    this.active = active;
    return this;
  }

  public ProductTestDataBuilder category(Category category) {
    this.category = category;
    return this;
  }

  public ProductTestDataBuilder images(List<Image> images) {
    this.images = images;
    return this;
  }

  public ProductTestDataBuilder stock(long stock) {
    this.stock = stock;
    return this;
  }

  public Product build() {
    return new Product(name, description, price, priceSale, inSale, active, category, images,
        stock);
  }

  public static List<Product> createProducts(int size) {
    return Stream.iterate(size, i -> i++)
        .map((i) -> new ProductTestDataBuilder().build())
        .limit(size)
        .collect(Collectors.toList());
  }
}
