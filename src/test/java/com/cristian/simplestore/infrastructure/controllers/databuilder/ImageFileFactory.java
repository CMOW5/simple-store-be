package com.cristian.simplestore.infrastructure.controllers.databuilder;

import org.springframework.mock.web.MockMultipartFile;

public class ImageFileFactory {

  private static String DEFAULT_PARAM_NAME = "image";
  private static String DEFAULT_IMAGE_NAME = "imagename";
  
  public static MockMultipartFile createMockMultiPartFile() {
    return createMockMultiPartFile(DEFAULT_PARAM_NAME, DEFAULT_IMAGE_NAME);
  }
  
  public static MockMultipartFile createMockMultiPartFile(String filename) {
    return createMockMultiPartFile(DEFAULT_PARAM_NAME, filename);

  }
  
  public static MockMultipartFile createMockMultiPartFile(String name, String filename) {
    return new MockMultipartFile(name, filename, "text/plain", "test data".getBytes());
  }

}
