package com.cristian.simplestore.utils;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import com.cristian.simplestore.domain.models.Image;
import com.cristian.simplestore.domain.services.image.CreateImageService;
import com.cristian.simplestore.infrastructure.controllers.databuilder.ImageFileFactory;

@Component
public class ImageTestUtils {
 
  @Autowired
  CreateImageService createImageService;
 
  public Image saveRandomImageOnDb() {
    MultipartFile imageFile = ImageFileFactory.createMockMultiPartFile();
    return this.createImageService.save(imageFile);
  }

  public List<Image> saveRandomImagesOnDb(long numberOfImages) {
    List<Image> savedImages = new ArrayList<>();

    for (int i = 0; i < numberOfImages; i++) {
      savedImages.add(this.saveRandomImageOnDb());
    }

    return savedImages;
  }

  public MultipartFile generateMockMultipartFile() {
    return ImageFileFactory.createMockMultiPartFile();
  }

  public List<MultipartFile> generateMockMultiPartFiles(long numberOfImages) {
    List<MultipartFile> files = new ArrayList<>();

    for (int i = 0; i < numberOfImages; i++) {
      files.add(generateMockMultipartFile());
    }

    return files;
  }
}