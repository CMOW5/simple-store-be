package com.cristian.simplestore.unit.dto;

import static org.assertj.core.api.Assertions.assertThat;
import javax.transaction.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.cristian.simplestore.BaseTest;
import com.cristian.simplestore.persistence.entities.Image;
import com.cristian.simplestore.utils.ImageTestsUtils;
import com.cristian.simplestore.web.dto.response.ImageResponse;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class TestImageResponseDto extends BaseTest {

  @Autowired
  private ImageTestsUtils imageUtils;

  @Test
  public void convertEntityToDto() {
    Image image = imageUtils.saveRandomImageOnDb();
    ImageResponse imageDto = new ImageResponse(image);

    assertThatImageAndDtoDataAreEqual(image, imageDto);
  }

  private void assertThatImageAndDtoDataAreEqual(Image image, ImageResponse imageDto) {
    assertThat(image.getId()).isEqualTo(imageDto.getId());
    assertThat(image.getUrl()).isEqualTo(imageDto.getUrl());
  }
}
