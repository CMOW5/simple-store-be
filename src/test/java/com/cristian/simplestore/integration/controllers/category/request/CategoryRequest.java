package com.cristian.simplestore.integration.controllers.category.request;

import java.io.IOException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import com.cristian.simplestore.utils.MultiPartFormBuilder;
import com.cristian.simplestore.utils.RequestBuilder;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Component
public abstract class CategoryRequest {
  
  private static String ADMIN_CATEGORIES_BASE_URL = "/api/admin/categories";
  
  private RequestBuilder requestBuilder;

  public CategoryRequest(RequestBuilder requestBuilder) {
    this.requestBuilder = requestBuilder;
  }

  public ResponseEntity<String> sendFindAllCategoriesRequest()
      throws JsonParseException, JsonMappingException, IOException {
    RequestBuilder request = buildFindAllCategoriesRequest();
    ResponseEntity<String> jsonResponse = request.send();
    return jsonResponse;
  }

  protected RequestBuilder buildFindAllCategoriesRequest() {
    String url = ADMIN_CATEGORIES_BASE_URL;
    return requestBuilder.url(url).httpMethod(HttpMethod.GET);
  }

  public ResponseEntity<String> sendFindCategoryByIdRequest(Long id)
      throws JsonParseException, JsonMappingException, IOException {
    RequestBuilder request = buildFindCategoryByIdRequest(id);
    ResponseEntity<String> jsonResponse = request.send();
    return jsonResponse;
  }

  protected RequestBuilder buildFindCategoryByIdRequest(Long id) {
    String url = ADMIN_CATEGORIES_BASE_URL + "/" + id;
    return requestBuilder.url(url).httpMethod(HttpMethod.GET);
  }

  public ResponseEntity<String> sendCategoryCreateRequest(MultiPartFormBuilder form)
      throws JsonParseException, JsonMappingException, IOException {
    RequestBuilder request = buildCategoryCreateRequest(form);
    ResponseEntity<String> jsonResponse = request.send();
    return jsonResponse;
  }

  protected RequestBuilder buildCategoryCreateRequest(MultiPartFormBuilder form) {
    String url = ADMIN_CATEGORIES_BASE_URL;
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
    MultiValueMap<String, Object> body = form.build();
    return requestBuilder.url(url).httpMethod(HttpMethod.POST).headers(headers).body(body);
  }

  public ResponseEntity<String> sendCategoryUpdateRequest(Long categoryId,
      MultiPartFormBuilder form) throws JsonParseException, JsonMappingException, IOException {
    RequestBuilder request = buildCategoryUpdateRequest(categoryId, form);
    ResponseEntity<String> jsonResponse = request.send();
    return jsonResponse;
  }
  
  protected RequestBuilder buildCategoryUpdateRequest(Long categoryId, MultiPartFormBuilder form) {
    String url = ADMIN_CATEGORIES_BASE_URL + "/" + categoryId;
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
    MultiValueMap<String, Object> body = form.build();
    return requestBuilder.url(url).httpMethod(HttpMethod.PUT).headers(headers).body(body);
  }

  public ResponseEntity<String> sendCategoryDeleteRequest(Long categoryId)
      throws JsonParseException, JsonMappingException, IOException {
    RequestBuilder request = buildCategoryDeleteRequest(categoryId);
    ResponseEntity<String> jsonResponse = request.send();
    return jsonResponse;
  }
  
  protected RequestBuilder buildCategoryDeleteRequest(Long categoryId) {
    String url = ADMIN_CATEGORIES_BASE_URL + "/" + categoryId;
    return requestBuilder.url(url).httpMethod(HttpMethod.DELETE);
  }
}
