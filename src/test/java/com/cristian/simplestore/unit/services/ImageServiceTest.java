package com.cristian.simplestore.unit.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import com.cristian.simplestore.BaseTest;
import com.cristian.simplestore.business.services.ImageService;
import com.cristian.simplestore.persistence.entities.Image;
import com.cristian.simplestore.persistence.respositories.ImageRepository;
import com.cristian.simplestore.utils.ImageBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ImageServiceTest extends BaseTest {
	
	@Autowired
	ImageService imageService;
	
	@Autowired
	ImageRepository imageRepository;
	
	@Autowired
	ImageBuilder imageBuilder;
	
	@Before
	public void setUp() {
		this.cleanUpDb();
	}
	
	@After
	public void tearDown() {
		this.cleanUpDb();
	}
	
	private void cleanUpDb() {
		this.imageRepository.deleteAll();
	}
	
	@Test
	public void testItFindsAnImageById() {	
		Image image = this.saveRandomImageOnDb();
		
		Image expectedImage = this.imageService.findById(image.getId());
		
		assertThat(expectedImage).isNotNull();
	}
	
	@Test(expected = EntityNotFoundException.class)
	public void testItDoesNotFindsAnImageById() {	
		Long nonExistentImageId = 1L;
		
		this.imageService.findById(nonExistentImageId);
	}
	
	@Test
	public void testItFindsAllImagesById() {	
		long MAX_IMAGE_SIZE = 3;
		List<Image> images = this.saveRandomImagesOnDb(MAX_IMAGE_SIZE);
		List<Long> ids = new ArrayList<>();
		images.forEach((image) -> ids.add(image.getId()));
		
		List<Image> expectedImages = this.imageService.findAllById(ids);
		
		assertThat(expectedImages.size()).isEqualTo(MAX_IMAGE_SIZE);
	}
	
	@Test
	public void testItsavesAnImageFile() {	
		MultipartFile imageFile = this.imageBuilder.createMultipartImage();
		
		Image expectedImage = this.imageService.save(imageFile);
		
		assertThat(expectedImage).isNotNull();
	}
	
	@Test
	public void testItsavesAllsImages() {	
		int MAX_IMAGE_FILES = 3;
		List<MultipartFile> imageFiles = this.generateRandomImageFiles(MAX_IMAGE_FILES);
		
		List<Image> expectedImages = this.imageService.saveAll(imageFiles);
		
		assertThat(expectedImages.size()).isEqualTo(MAX_IMAGE_FILES);
	}
	
	@Test(expected = EntityNotFoundException.class)
	public void testItDeletesAnImage() {	
		Image image = this.saveRandomImageOnDb();
		
		this.imageService.delete(image);
		
		this.imageService.findById(image.getId());
	}
	
	@Test(expected = EntityNotFoundException.class)
	public void testItDeletesAnImageById() {	
		Image image = this.saveRandomImageOnDb();
		
		this.imageService.deleteById(image.getId());
		
		this.imageService.findById(image.getId());
	}
	
	@Test(expected = EntityNotFoundException.class)
	public void testItDoesNotDeleteAnImageById() {	
		Long nonExistentImageId = 1L;
		
		this.imageService.deleteById(nonExistentImageId);
	}
	
	@Test
	public void testItDeletesAllById() {	
		long MAX_IMAGE_SIZE = 3;
		List<Image> images = this.saveRandomImagesOnDb(MAX_IMAGE_SIZE);
		List<Long> ids = new ArrayList<>();
		images.forEach((image) -> ids.add(image.getId()));
		
		this.imageService.deleteAllById(ids);
	}
	
	@Test
	public void testItDeletesAllImages() {	
		long MAX_IMAGE_SIZE = 3;
		List<Image> images = this.saveRandomImagesOnDb(MAX_IMAGE_SIZE);
		
		this.imageService.deleteAll(images);
	}
	
	private Image saveRandomImageOnDb() {
		MultipartFile imageFile = this.imageBuilder.createMultipartImage();
		return this.imageService.save(imageFile);
	}
	
	private List<MultipartFile> generateRandomImageFiles(long numberOfImages) {
		List<MultipartFile> files = new ArrayList<>();
		
		for (int i = 0; i < numberOfImages; i++) {
			files.add(this.imageBuilder.createMultipartImage());
		}
		
		return files;
	}
	
	private List<Image> saveRandomImagesOnDb(long numberOfImages) {
		List<Image> savedImages = new ArrayList<>();
		
		for (int i = 0; i < numberOfImages; i++) {
			savedImages.add(this.saveRandomImageOnDb());
		}
		
		return savedImages;
	}
	
}
