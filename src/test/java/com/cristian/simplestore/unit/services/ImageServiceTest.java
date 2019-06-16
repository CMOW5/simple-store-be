package com.cristian.simplestore.unit.services;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;
import com.cristian.simplestore.BaseTest;
import com.cristian.simplestore.business.services.image.ImageServiceImpl;
import com.cristian.simplestore.persistence.entities.ImageEntity;
import com.cristian.simplestore.utils.image.ImageTestsUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ImageServiceTest extends BaseTest {

  @Autowired
  ImageServiceImpl imageService;

  @Autowired
  ImageTestsUtils imageUtils;


  @Test
  public void testItFindsAnImageById() {
    ImageEntity image = imageUtils.saveRandomImageOnDb();

    ImageEntity savedImage = imageService.findById(image.getId());

    assertThat(savedImage).isNotNull();
  }

  @Test(expected = EntityNotFoundException.class)
  public void testItDoesNotFindsAnImageById() {
    Long nonExistentImageId = 1L;

    imageService.findById(nonExistentImageId);
  }

  @Test
  public void testItFindsAllImagesById() {
    long MAX_IMAGE_SIZE = 3;
    List<ImageEntity> images = imageUtils.saveRandomImagesOnDb(MAX_IMAGE_SIZE);

    List<ImageEntity> expectedImages = imageService.findAllById(getIdsFromImages(images));

    assertThat(expectedImages.size()).isEqualTo(MAX_IMAGE_SIZE);
  }

  @Test
  public void testItsavesAnImageFile() {
    MultipartFile imageFile = imageUtils.generateMockMultipartFile();

    ImageEntity savedImage = imageService.save(imageFile);

    assertThat(savedImage).isNotNull();
  }

  @Test
  public void testItsavesAllsImages() {
    int MAX_IMAGE_FILES = 3;
    List<MultipartFile> imageFiles = imageUtils.generateMockMultiPartFiles(MAX_IMAGE_FILES);

    List<ImageEntity> savedImages = imageService.saveAll(imageFiles);

    assertThat(savedImages.size()).isEqualTo(MAX_IMAGE_FILES);
  }

  @Test(expected = EntityNotFoundException.class)
  public void testItDeletesAnImage() {
    ImageEntity image = imageUtils.saveRandomImageOnDb();

    imageService.delete(image);

    imageService.findById(image.getId());
  }

  @Test(expected = EntityNotFoundException.class)
  public void testItDeletesAnImageById() {
    ImageEntity image = imageUtils.saveRandomImageOnDb();

    imageService.deleteById(image.getId());

    imageService.findById(image.getId());
  }

  @Test(expected = EntityNotFoundException.class)
  public void testItDoesNotDeleteAnImageById() {
    Long nonExistentImageId = 1L;

    imageService.deleteById(nonExistentImageId);
  }

  @Test
  public void testItDeletesAllById() {
    long MAX_IMAGE_SIZE = 3;
    List<ImageEntity> images = imageUtils.saveRandomImagesOnDb(MAX_IMAGE_SIZE);

    imageService.deleteAllById(getIdsFromImages(images));
  }

  @Test
  public void testItDeletesAllImages() {
    long MAX_IMAGE_SIZE = 3;
    List<ImageEntity> images = imageUtils.saveRandomImagesOnDb(MAX_IMAGE_SIZE);

    imageService.deleteAll(images);
  }

  private List<Long> getIdsFromImages(List<ImageEntity> images) {
    List<Long> imagesIds = new ArrayList<>();
    images.forEach((image) -> imagesIds.add(image.getId()));
    return imagesIds;
  }

}
