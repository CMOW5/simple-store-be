package com.cristian.simplestore.infrastructure.adapters.repository.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.cristian.simplestore.domain.models.Image;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "images")
@Data
@NoArgsConstructor
public class ImageEntity implements Serializable {
  private static final long serialVersionUID = 3603066326555414036L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String name;
  
  public ImageEntity(String name) {
    this.name = name;
  }

  public static ImageEntity fromDomain(Image image) {
    return new ImageEntity(image.getName());
  }

  public static Image toDomain(ImageEntity entity) {
    return new Image(entity.getName());
  }
}