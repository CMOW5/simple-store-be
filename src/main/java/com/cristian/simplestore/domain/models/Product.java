package com.cristian.simplestore.domain.models;

import java.util.List;

public class Product {
  private String name;
  
  private String description;
  
  private double price;
  
  private double priceSale;
  
  private boolean inSale;
  
  private boolean active;
  
  private Category category;
  
  private List<Image> images;
  
  private long stock;
  
  public Product(String name, String description, double price, double priceSale, boolean inSale,
      boolean active, Category category, List<Image> images, long stock) {
    this.name = name;
    this.description = description;
    this.price = price;
    this.priceSale = priceSale;
    this.inSale = inSale;
    this.active = active;
    this.category = category;
    this.images = images;
    this.stock = stock;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public double getPrice() {
    return price;
  }

  public double getPriceSale() {
    return priceSale;
  }

  public boolean isInSale() {
    return inSale;
  }

  public boolean isActive() {
    return active;
  }

  public Category getCategory() {
    return category;
  }

  public List<Image> getImages() {
    return images;
  }

  public long getStock() {
    return stock;
  }
}
