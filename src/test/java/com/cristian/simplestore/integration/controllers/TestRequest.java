package com.cristian.simplestore.integration.controllers;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import com.cristian.simplestore.utils.request.RequestSender;

public abstract class TestRequest {
  
  protected RequestSender requestSender;

  public TestRequest(RequestSender requestSender) {
    this.requestSender = requestSender;
  }
  
  public ResponseEntity<String> send(RequestEntity<?> request) {
    return requestSender.sendRequest(request);
  }
}
