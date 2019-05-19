package com.cristian.simplestore.integration.controllers.product.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.cristian.simplestore.utils.request.RequestSender;

@Component
public class UnauthenticatedProductRequest extends ProductRequest {
  
  @Autowired
  public UnauthenticatedProductRequest(RequestSender requestSender) {
    super(requestSender);
  }
}
