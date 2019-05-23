package com.cristian.simplestore.utils.image;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import com.cristian.simplestore.business.services.image.ImageServiceImpl;
import com.cristian.simplestore.persistence.entities.Image;

@Component
public class ImageTestsUtils {

  @Autowired
  ImageServiceImpl imageService;

  @Autowired
  ImageFileFactory imageBuilder;

  public Image saveRandomImageOnDb() {
    MultipartFile imageFile = this.imageBuilder.createMockMultipartImage();
    return this.imageService.save(imageFile);
  }

  public List<Image> saveRandomImagesOnDb(long numberOfImages) {
    List<Image> savedImages = new ArrayList<>();

    for (int i = 0; i < numberOfImages; i++) {
      savedImages.add(this.saveRandomImageOnDb());
    }

    return savedImages;
  }

  public MultipartFile generateMockMultipartFile() {
    return imageBuilder.createMockMultipartImage();
  }

  public List<MultipartFile> generateMockMultiPartFiles(long numberOfImages) {
    List<MultipartFile> files = new ArrayList<>();

    for (int i = 0; i < numberOfImages; i++) {
      files.add(generateMockMultipartFile());
    }

    return files;
  }

  public Resource storeImageOnDisk() {
    return imageBuilder.storeImageOnDisk();
  }

  public Resource storeImageOnDisk(String filename) {
    return imageBuilder.storeImageOnDisk(filename);
  }

  public List<Resource> storeImagesOnDisk(int size) {
    return imageBuilder.storeImagesOnDisk(size);
  }
}
