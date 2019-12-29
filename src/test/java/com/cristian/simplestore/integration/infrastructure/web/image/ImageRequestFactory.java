package com.cristian.simplestore.integration.infrastructure.web.image;

import org.springframework.http.HttpMethod;

import com.cristian.simplestore.utils.request.RequestEntityBuilder;


public class ImageRequestFactory {

  public static final String IMAGE_BASE_URL = "/api/images";

  public static RequestEntityBuilder createGetImageRequest(String imageName) {
    String url = IMAGE_BASE_URL + "/" + imageName;
    return new RequestEntityBuilder().url(url).httpMethod(HttpMethod.GET);
  }

}