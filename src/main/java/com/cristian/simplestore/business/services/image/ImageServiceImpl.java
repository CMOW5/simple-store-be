package com.cristian.simplestore.business.services.image;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.cristian.simplestore.business.services.storage.ImageStorageService;
import com.cristian.simplestore.persistence.entities.ImageEntity;
import com.cristian.simplestore.persistence.repositories.ImageRepository;


@Service
public class ImageServiceImpl implements ImageService {

  private final ImageRepository imageRepository;

  private final ImageStorageService imageStorageService;

  @Autowired
  public ImageServiceImpl(ImageRepository imageRepository,
      ImageStorageService imageStorageService) {
    this.imageRepository = imageRepository;
    this.imageStorageService = imageStorageService;
  }

  public ImageEntity findById(Long id) {
    return imageRepository.findById(id).orElseThrow(
        () -> new EntityNotFoundException("The image with the given id was not found"));
  }

  public List<ImageEntity> findAllById(List<Long> imagesIds) {
    Iterable<ImageEntity> storedImages = imageRepository.findAllById(imagesIds);

    List<ImageEntity> images = new ArrayList<>();
    storedImages.forEach(images::add);

    return images;
  }

  @Transactional
  public ImageEntity save(MultipartFile file) {
    String imageNameWithPath = imageStorageService.store(file, generateImageName(file));
    ImageEntity image = new ImageEntity();
    image.setName(imageNameWithPath);
    return imageRepository.save(image);
  }

  public ImageEntity save(ImageEntity image) {
    return imageRepository.save(image);
  }

  @Transactional
  public List<ImageEntity> saveAll(List<MultipartFile> imagesFiles) {
    List<ImageEntity> savedImages = new ArrayList<>();

    for (MultipartFile imageFile : imagesFiles) {
      ImageEntity image = save(imageFile);
      savedImages.add(image);
    }
    return savedImages;
  }

  public void delete(ImageEntity image) {
    try {
      imageRepository.delete(image);
    } catch (EmptyResultDataAccessException exception) {
      throw new EntityNotFoundException("The image with the given id was not found");
    } catch (IllegalArgumentException exception) {
      // return
    }
  }

  public void deleteById(Long id) {
    try {
      imageRepository.deleteById(id);
    } catch (EmptyResultDataAccessException exception) {
      throw new EntityNotFoundException("The image with the given id was not found");
    }
  }

  public void deleteAllById(Iterable<Long> imagesIdsToDelete) {
    Iterable<ImageEntity> imagesToDelete = imageRepository.findAllById(imagesIdsToDelete);
    imageRepository.deleteAll(imagesToDelete);
  }

  public void deleteAll(Iterable<ImageEntity> imagesToDelete) {
    imageRepository.deleteAll(imagesToDelete);
  }

  private String generateImageName(MultipartFile file) {
    return UUID.randomUUID().toString() + "."
        + FilenameUtils.getExtension(file.getOriginalFilename());
  }
}
