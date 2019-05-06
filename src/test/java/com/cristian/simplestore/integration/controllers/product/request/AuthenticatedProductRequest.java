package com.cristian.simplestore.integration.controllers.product.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.cristian.simplestore.utils.MultiPartFormBuilder;
import com.cristian.simplestore.utils.RequestBuilder;

@Component
public class AuthenticatedProductRequest extends ProductRequest {
  
  @Autowired
  public AuthenticatedProductRequest(RequestBuilder requestBuilder) {
    super(requestBuilder);
  }
  
  @Override
  protected RequestBuilder buildFindAllProductsRequest() {
    RequestBuilder requestBuilder = super.buildFindAllProductsRequest();
    return requestBuilder.withJwtAuth();
  }
  
  @Override
  protected RequestBuilder buildFindProductByIdRequest(Long id) {
    RequestBuilder requestBuilder = super.buildFindProductByIdRequest(id);
    return requestBuilder.withJwtAuth();
  }
  
  @Override
  protected RequestBuilder buildProductCreateRequest(MultiPartFormBuilder form) {
    RequestBuilder requestBuilder = super.buildProductCreateRequest(form);
    return requestBuilder.withJwtAuth();
  }
   
  @Override
  protected RequestBuilder buildProductUpdateRequest(Long categoryId, MultiPartFormBuilder form) {
    RequestBuilder requestBuilder = super.buildProductUpdateRequest(categoryId, form);
    return requestBuilder.withJwtAuth();
  }
  
  @Override
  protected RequestBuilder buildProductDeleteRequest(Long categoryId) {
    RequestBuilder requestBuilder = super.buildProductDeleteRequest(categoryId);
    return requestBuilder.withJwtAuth();
  }
}
