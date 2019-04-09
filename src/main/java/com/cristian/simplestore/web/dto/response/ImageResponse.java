package com.cristian.simplestore.web.dto.response;

import com.cristian.simplestore.persistence.entities.Image;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ImageResponse {
  private Long id;
  private String url;

  public ImageResponse(Image image) {
    id = image.getId();
    url = image.getUrl();
  }

  public static ImageResponse build(Image image) {
    if (image != null) {
      return new ImageResponse(image);
    } else {
      return null;
    }
  }
}
