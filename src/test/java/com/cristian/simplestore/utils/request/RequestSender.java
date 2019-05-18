package com.cristian.simplestore.utils.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class RequestSender {

  // this is not required because when the
  // unit.services tests are loaded this
  // dependency cannot be resolved because
  // this needs a @SpringBootTest(webEnvironment)
  // context which is not provided on unit tests
  @Autowired(required = false)
  private TestRestTemplate restTemplate;

  public RequestSender() {}

  public ResponseEntity<String> sendRequest(RequestEntity<?> requestEntity) {
    return (ResponseEntity<String>) sendRequest(requestEntity, String.class);
  }

  public <T> ResponseEntity<?> sendRequest(RequestEntity<?> requestEntity, Class<T> responseType) {
    return restTemplate.exchange(requestEntity.getUrl(), requestEntity.getMethod(), requestEntity,
        responseType);
  }
}
