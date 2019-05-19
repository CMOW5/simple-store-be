package com.cristian.simplestore.integration.controllers.image;

import static org.assertj.core.api.Assertions.assertThat;
import java.io.IOException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import com.cristian.simplestore.business.services.ImageService;
import com.cristian.simplestore.integration.controllers.BaseIntegrationTest;
import com.cristian.simplestore.persistence.entities.Image;
import com.cristian.simplestore.utils.ImageFileFactory;
import com.cristian.simplestore.utils.request.JsonResponse;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ImageControllerTest extends BaseIntegrationTest {

  @Autowired
  private ImageService imageService;

  @Autowired
  private ImageFileFactory imageFileFactory;
  
  @Autowired
  private ImageRequest imageRequest;

  @Test
  public void testItServesAnImage() throws IOException {
    Image image = saveRandomImageOnDb();

    JsonResponse response = imageRequest.sendGetImageRequest(image.getName());

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
  }

  @Test
  public void testItDoesNotFindAnImage() throws IOException {
    String name = "some_random_name.jpg";

    JsonResponse response = imageRequest.sendGetImageRequest(name);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }

  private Image saveRandomImageOnDb() throws IOException {
    Resource resource = imageFileFactory.storeImageOnDisk();

    Image image = new Image();
    image.setName(resource.getFile().getPath());
    return this.imageService.save(image);
  }
}
