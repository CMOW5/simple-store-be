package com.cristian.simplestore.domain.models;

public class Image {
  
  private Long id;
  
  private String name;
 
  public Image(String name) { 
    this.name = name;
  }
  
  public Image(Long id, String name) { 
    this.id = id;
    this.name = name;
  }

  public Long getId() {
    return id;
  }
  
  public String getName() {
    return name;
  }
}
