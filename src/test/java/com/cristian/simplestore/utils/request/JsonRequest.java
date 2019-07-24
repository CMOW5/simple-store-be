package com.cristian.simplestore.utils.request;

import org.springframework.http.RequestEntity;

public interface JsonRequest {
  public JsonResponse send(RequestEntity<?> request);
}