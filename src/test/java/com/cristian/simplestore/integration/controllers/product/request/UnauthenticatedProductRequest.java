package com.cristian.simplestore.integration.controllers.product.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.cristian.simplestore.utils.RequestBuilder;

@Component
public class UnauthenticatedProductRequest extends ProductRequest {
  
  @Autowired
  public UnauthenticatedProductRequest(RequestBuilder requestBuilder) {
    super(requestBuilder);
  }
}
