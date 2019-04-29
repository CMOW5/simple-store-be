package com.cristian.simplestore.integration.controllers.category.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.cristian.simplestore.utils.RequestBuilder;

@Component
public class UnauthenticatedCategoryRequest extends CategoryRequest {

  @Autowired
  public UnauthenticatedCategoryRequest(RequestBuilder requestBuilder) {
    super(requestBuilder);
  }
}
