package com.cristian.simplestore.integration.controllers.product.request;

import java.io.IOException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import com.cristian.simplestore.utils.MultiPartFormBuilder;
import com.cristian.simplestore.utils.RequestBuilder;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public abstract class ProductRequest {

  private RequestBuilder requestBuilder;

  public ProductRequest(RequestBuilder requestBuilder) {
    this.requestBuilder = requestBuilder;
  }

  public ResponseEntity<String> sendFindAllProductsRequest() {
    RequestBuilder request = buildFindAllProductsRequest();
    ResponseEntity<String> response = request.send();
    return response;
  }

  protected RequestBuilder buildFindAllProductsRequest() {
    String url = "/api/admin/products";
    return requestBuilder.url(url).httpMethod(HttpMethod.GET);
  }

  public ResponseEntity<String> sendFindProductByIdRequest(Long id)
      throws JsonParseException, JsonMappingException, IOException {
    RequestBuilder request = buildFindProductByIdRequest(id);
    ResponseEntity<String> response = request.send();
    return response;
  }

  protected RequestBuilder buildFindProductByIdRequest(Long id) {
    String url = "/api/admin/products/" + id;
    return requestBuilder.url(url).httpMethod(HttpMethod.GET);
  }

  public ResponseEntity<String> sendProductCreateRequest(MultiPartFormBuilder form)
      throws JsonParseException, JsonMappingException, IOException {
    RequestBuilder request = buildProductCreateRequest(form);
    ResponseEntity<String> response = request.send();
    return response;
  }

  protected RequestBuilder buildProductCreateRequest(MultiPartFormBuilder form) {
    String url = "/api/admin/products";
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
    MultiValueMap<String, Object> body = form.build();
    return requestBuilder.url(url).httpMethod(HttpMethod.POST).headers(headers).body(body);
  }

  public ResponseEntity<String> sendProductUpdateRequest(Long productId, MultiPartFormBuilder form)
      throws JsonParseException, JsonMappingException, IOException {
    RequestBuilder request = buildProductUpdateRequest(productId, form);
    ResponseEntity<String> response = request.send();
    return response;
  }

  protected RequestBuilder buildProductUpdateRequest(Long productId, MultiPartFormBuilder form) {
    String url = "/api/admin/products/" + productId;
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
    MultiValueMap<String, Object> body = form.build();
    return requestBuilder.url(url).httpMethod(HttpMethod.PUT).headers(headers).body(body);
  }

  public ResponseEntity<String> sendProductDeleteRequest(Long productId)
      throws JsonParseException, JsonMappingException, IOException {
    RequestBuilder request = buildProductDeleteRequest(productId);
    ResponseEntity<String> response = request.send();
    return response;
  }
  
  protected RequestBuilder buildProductDeleteRequest(Long productId) {
    String url = "/api/admin/products/" + productId;
    return requestBuilder.url(url).httpMethod(HttpMethod.DELETE);
  }
}
