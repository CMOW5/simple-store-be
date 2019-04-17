package com.cristian.simplestore.business.services;

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
import com.cristian.simplestore.persistence.entities.Image;
import com.cristian.simplestore.persistence.repositories.ImageRepository;


@Service
public class ImageServiceImpl implements ImageService {

  @Autowired
  private ImageRepository imageRepository;

  @Autowired
  private ImageStorageService imageStorageService;

  public Image findById(Long id) {
    return imageRepository.findById(id).orElseThrow(
        () -> new EntityNotFoundException("The image with the given id was not found"));
  }

  public List<Image> findAllById(List<Long> imagesIds) {
    Iterable<Image> storedImages = imageRepository.findAllById(imagesIds);

    List<Image> images = new ArrayList<>();
    storedImages.forEach(images::add);

    return images;
  }

  @Transactional
  public Image save(MultipartFile file) {
    String imageNameWithPath = imageStorageService.store(file, generateImageName(file));
    Image image = new Image();
    image.setName(imageNameWithPath);
    return imageRepository.save(image);
  }

  public Image save(Image image) {
    return imageRepository.save(image);
  }

  @Transactional
  public List<Image> saveAll(List<MultipartFile> imagesFiles) {
    List<Image> savedImages = new ArrayList<>();

    for (MultipartFile imageFile : imagesFiles) {
      Image image = save(imageFile);
      savedImages.add(image);
    }
    return savedImages;
  }

  public void delete(Image image) {
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
    Iterable<Image> imagesToDelete = imageRepository.findAllById(imagesIdsToDelete);
    imageRepository.deleteAll(imagesToDelete);
  }

  public void deleteAll(Iterable<Image> imagesToDelete) {
    imageRepository.deleteAll(imagesToDelete);
  }

  private String generateImageName(MultipartFile file) {
    return UUID.randomUUID().toString() + "."
        + FilenameUtils.getExtension(file.getOriginalFilename());
  }
}
