package com.cristian.simplestore.integration.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import com.cristian.simplestore.BaseTest;
import com.cristian.simplestore.business.services.ImageService;
import com.cristian.simplestore.persistence.entities.Image;
import com.cristian.simplestore.persistence.repositories.ImageRepository;
import com.cristian.simplestore.utils.ApiRequestUtils;
import com.cristian.simplestore.utils.ImageBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ImageControllerTest extends BaseTest {

  @Autowired
  private ImageService imageService;

  @Autowired
  private ImageBuilder imageBuilder;
  
  @Autowired
  private ApiRequestUtils apiUtils;

  @Autowired
  private ImageRepository imageRepository;

  @Before
  public void setUp() {
    this.imageRepository.deleteAll();
  }

  @After
  public void tearDown() {
    this.imageRepository.deleteAll();
  }

  @Test
  public void testItServesAnImage() throws IOException {
    Image image = saveRandomImageOnDb();

    ResponseEntity<String> response = sendGetImageRequest(image.getName());

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
  }

  @Test
  public void testItDoesNotFindAnImage() throws IOException {
    String name = "some random name.jpg";

    ResponseEntity<String> response = sendGetImageRequest(name);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }

  private Image saveRandomImageOnDb() throws IOException {
    Resource resource = imageBuilder.storeImageOnDisk();

    Image image = new Image();
    image.setName(resource.getFile().getPath());
    return this.imageService.save(image);
  }

  public ResponseEntity<String> sendGetImageRequest(String imageName) {
    String url = "/api/images/" + imageName;
    ResponseEntity<String> response = this.apiUtils.sendRequest(url, HttpMethod.GET, null, null);
    return response;
  }
}
