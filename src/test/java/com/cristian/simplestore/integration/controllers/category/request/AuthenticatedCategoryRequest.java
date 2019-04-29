package com.cristian.simplestore.integration.controllers.category.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.cristian.simplestore.utils.MultiPartFormBuilder;
import com.cristian.simplestore.utils.RequestBuilder;

@Component
public class AuthenticatedCategoryRequest extends CategoryRequest {
  
  @Autowired
  public AuthenticatedCategoryRequest(RequestBuilder requestBuilder) {
    super(requestBuilder);
  }
  
  @Override
  protected RequestBuilder buildFindAllCategoriesRequest() {
    RequestBuilder requestBuilder = super.buildFindAllCategoriesRequest();
    return requestBuilder.withJwtAuth();
  }
  
  @Override
  protected RequestBuilder buildFindCategoryByIdRequest(Long id) {
    RequestBuilder requestBuilder = super.buildFindCategoryByIdRequest(id);
    return requestBuilder.withJwtAuth();
  }
  
  @Override
  protected RequestBuilder buildCategoryCreateRequest(MultiPartFormBuilder form) {
    RequestBuilder requestBuilder = super.buildCategoryCreateRequest(form);
    return requestBuilder.withJwtAuth();
  }
   
  @Override
  protected RequestBuilder buildCategoryUpdateRequest(Long categoryId, MultiPartFormBuilder form) {
    RequestBuilder requestBuilder = super.buildCategoryUpdateRequest(categoryId, form);
    return requestBuilder.withJwtAuth();
  }
  
  @Override
  protected RequestBuilder buildCategoryDeleteRequest(Long categoryId) {
    RequestBuilder requestBuilder = super.buildCategoryDeleteRequest(categoryId);
    return requestBuilder.withJwtAuth();
  }

}
