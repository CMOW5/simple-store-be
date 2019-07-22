package com.cristian.simplestore.domain.services.image;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;
import com.cristian.simplestore.domain.models.Image;
import com.cristian.simplestore.domain.ports.repository.ImageRepository;
import com.cristian.simplestore.domain.services.storage.StorageService;

public class CreateImageService {

  private final ImageRepository imageRepository;
  private final StorageService imageStorageService;

  public CreateImageService(ImageRepository imageRepository, StorageService storageService) {
    this.imageRepository = imageRepository;
    imageStorageService = storageService;
  }

  public Image execute(MultipartFile file) {
    String imageNameWithPath = imageStorageService.store(file, generateImageName(file));
    Image image = new Image(imageNameWithPath);
    return imageRepository.save(image);
  }

  private String generateImageName(MultipartFile file) {
    return UUID.randomUUID().toString() + "."
        + FilenameUtils.getExtension(file.getOriginalFilename());
  }

  public List<Image> create(List<MultipartFile> files) {
    return files.stream().map((file) -> execute(file)).collect(Collectors.toList());
  }
}
