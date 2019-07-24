package com.cristian.simplestore.utils.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class JsonRequestSender implements JsonRequest {

  @Autowired
  RequestSender requestSender;

  @Override
  public JsonResponse send(RequestEntity<?> request) {
    ResponseEntity<String> response = requestSender.sendRequest(request);
    return new JsonResponse(response);
  }

}