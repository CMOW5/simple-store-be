package com.cristian.simplestore.web.dto.response;

import java.util.ArrayList;
import java.util.List;
import com.cristian.simplestore.persistence.entities.ImageEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageResponse  {

  private Long id;

  private String url;

  public static ImageResponse of(ImageEntity entity) {
    ImageResponse image = null;
    
    if (entity != null) {
      image = new ImageResponse(entity.getId(), entity.getUrl());
    }
    
    return image;
  }
  
  public static List<ImageResponse> of(List<ImageEntity> entities) {
    List<ImageResponse> images = new ArrayList<>();
    
    for (ImageEntity entity : entities) {
      images.add(of(entity));
    }
    
    return images;
  }
}
