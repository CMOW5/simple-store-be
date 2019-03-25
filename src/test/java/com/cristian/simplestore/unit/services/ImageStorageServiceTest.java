package com.cristian.simplestore.unit.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import com.cristian.simplestore.BaseTest;
import com.cristian.simplestore.business.services.storage.ImageStorageService;
import com.cristian.simplestore.business.services.storage.StorageConfig;
import com.cristian.simplestore.business.services.storage.exceptions.StorageFileNotFoundException;
import com.cristian.simplestore.utils.ImageBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ImageStorageServiceTest extends BaseTest {
	
	@Autowired
	ImageStorageService imageStorageService;
	
	@Autowired
	StorageConfig storageConfig;
	
	@Autowired 
	ImageBuilder imageBuilder;
	
	@Before
	public void setUp() {
		deleteAllFiles();
	}
	
	@After
	public void tearDown() {
		deleteAllFiles();
	}
	
	private void deleteAllFiles() {
		this.imageStorageService.deleteAll();
	}

	@Test 
	public void testItStoresAImage() throws IOException {
		MultipartFile imageFile = imageBuilder.createMultipartImage();
		String imageName = imageBuilder.generateRandomImageName();
		
		String expectedPath = imageStorageService.store(imageFile, imageName);
		
		assertThat(expectedPath).isEqualTo(storageConfig.getLocation() + "/" + imageName);
		assertThatImageWasStored(imageName);
	}
	
	@Test 
	public void testItLoadsAImage() throws IOException {
		String filename = "loadedfilename.jpg";
		Resource savedResource =  imageBuilder.createImage(filename);
		
		Resource expectedResource = imageStorageService.loadAsResource(filename);

		assertTrue(expectedResource.exists());
		assertThat(expectedResource.contentLength()).isEqualTo(savedResource.contentLength());
	}
	
	@Test(expected = StorageFileNotFoundException.class) 
	public void testItDoesntLoadsAImage() throws IOException {
		String filename = "someNonExistentImage.jpg";
		imageBuilder.createImage();
		
		imageStorageService.loadAsResource(filename);
	}

	private void assertThatImageWasStored(String filename) {
		Resource file = imageStorageService.loadAsResource(filename);
		assertTrue(file.isFile());
	}
}
