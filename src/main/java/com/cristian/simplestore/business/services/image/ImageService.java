package com.cristian.simplestore.business.services.image;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.cristian.simplestore.persistence.entities.Image;

public interface ImageService {
  Image findById(Long id);

  List<Image> findAllById(List<Long> imagesIds);

  Image save(MultipartFile file);

  Image save(Image image);

  List<Image> saveAll(List<MultipartFile> imagesFiles);

  void delete(Image image);

  void deleteById(Long id);

  void deleteAllById(Iterable<Long> imagesIdsToDelete);

  void deleteAll(Iterable<Image> imagesToDelete);
}
