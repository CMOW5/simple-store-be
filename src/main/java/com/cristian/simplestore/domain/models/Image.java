package com.cristian.simplestore.domain.models;

import java.util.Objects;

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
  
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Image image = (Image) o;
		return Objects.equals(name, image.name) && Objects.equals(id, image.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, id);
	}
}
