package com.cristian.simplestore.domain.image.repository;

import com.cristian.simplestore.domain.image.Image;

public interface ImageRepository {

  Image save(Image image);

  void delete(Image image);
}
