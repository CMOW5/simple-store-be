package com.cristian.simplestore.business.services.image;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.cristian.simplestore.persistence.entities.ImageEntity;

public interface ImageService {
  ImageEntity findById(Long id);

  List<ImageEntity> findAllById(List<Long> imagesIds);

  ImageEntity save(MultipartFile file);

  ImageEntity save(ImageEntity image);

  List<ImageEntity> saveAll(List<MultipartFile> imagesFiles);

  void delete(ImageEntity image);

  void deleteById(Long id);

  void deleteAllById(Iterable<Long> imagesIdsToDelete);

  void deleteAll(Iterable<ImageEntity> imagesToDelete);
}
