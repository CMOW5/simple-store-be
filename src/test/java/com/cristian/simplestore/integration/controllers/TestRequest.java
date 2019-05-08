package com.cristian.simplestore.integration.controllers;

import org.springframework.http.ResponseEntity;
import com.cristian.simplestore.utils.RequestBuilder;

public abstract class TestRequest {
  
  protected RequestBuilder requestBuilder;

  public TestRequest(RequestBuilder requestBuilder) {
    this.requestBuilder = requestBuilder;
  }
  
  public ResponseEntity<String> send() {
    return requestBuilder.send();
  }
}
