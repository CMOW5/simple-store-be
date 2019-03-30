package com.cristian.simplestore.unit.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cristian.simplestore.BaseTest;
import com.cristian.simplestore.persistence.entities.Image;
import com.cristian.simplestore.utils.DbCleaner;
import com.cristian.simplestore.utils.ImageTestsUtils;
import com.cristian.simplestore.web.dto.ImageResponseDto;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestImageResponseDto extends BaseTest {
	
	@Autowired 
	private ImageTestsUtils imageUtils;
	
	@Autowired
	private DbCleaner dbCleaner;
	
	@Before
	public void setUp() {
		cleanUpDb();
	}
	
	@After
	public void tearDown() {
		cleanUpDb();
	}
	
	private void cleanUpDb() {
		dbCleaner.cleanImagesTable();
	}
	
	@Test
	public void convertEntityToDto() {
		Image image = imageUtils.saveRandomImageOnDb();
		ImageResponseDto imageDto = new ImageResponseDto(image);
		
		assertThatImageAndDtoDataAreEqual(image, imageDto);
	}

	private void assertThatImageAndDtoDataAreEqual(Image image, ImageResponseDto imageDto) {
		assertThat(image.getId()).isEqualTo(imageDto.getId());
		assertThat(image.getUrl()).isEqualTo(imageDto.getUrl());
	}
}