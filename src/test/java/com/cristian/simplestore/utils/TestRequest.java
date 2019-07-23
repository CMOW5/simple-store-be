package com.cristian.simplestore.utils;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

public abstract class TestRequest {
  
  protected RequestSender requestSender;

  public TestRequest(RequestSender requestSender) {
    this.requestSender = requestSender;
  }
  
  public ResponseEntity<String> send(RequestEntity<?> request) {
    return requestSender.sendRequest(request);
  }
}
