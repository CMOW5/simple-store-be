package com.cristian.simplestore.integration.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import java.io.IOException;
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
import com.cristian.simplestore.business.services.ImageService;
import com.cristian.simplestore.persistence.entities.Image;
import com.cristian.simplestore.utils.ImageFileFactory;
import com.cristian.simplestore.utils.RequestBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ImageControllerTest extends BaseIntegrationTest {

  @Autowired
  private ImageService imageService;

  @Autowired
  private ImageFileFactory imageBuilder;
  
  @Autowired
  private RequestBuilder requestBuilder;

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
    ResponseEntity<String> response = requestBuilder.url(url).httpMethod(HttpMethod.GET).send();
    return response;
  }
}
