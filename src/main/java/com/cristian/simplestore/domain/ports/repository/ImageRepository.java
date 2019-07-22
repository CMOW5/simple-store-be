package com.cristian.simplestore.domain.ports.repository;

import com.cristian.simplestore.domain.models.Image;

public interface ImageRepository {

  Image save(Image image);

  void delete(Image image);
}
