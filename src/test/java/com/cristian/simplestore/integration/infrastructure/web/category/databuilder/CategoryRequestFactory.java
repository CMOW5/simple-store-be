package com.cristian.simplestore.integration.infrastructure.web.category.databuilder;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;

import com.cristian.simplestore.utils.request.MultiPartFormBuilder;
import com.cristian.simplestore.utils.request.RequestEntityBuilder;

public class CategoryRequestFactory {
  
  private static String ADMIN_CATEGORIES_BASE_URL = "/api/admin/categories";
   
  public static RequestEntityBuilder createFindAllCategoriesRequest() {
    String url = ADMIN_CATEGORIES_BASE_URL;
    return new RequestEntityBuilder().url(url).httpMethod(HttpMethod.GET);
  }

  public static RequestEntityBuilder createFindCategoryByIdRequest(Long id) {
    String url = ADMIN_CATEGORIES_BASE_URL + "/" + id;
    return new RequestEntityBuilder().url(url).httpMethod(HttpMethod.GET);
  }
  
  public static RequestEntityBuilder createCategoryCreateRequest(MultiPartFormBuilder form) {
    String url = ADMIN_CATEGORIES_BASE_URL;
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
    MultiValueMap<String, Object> body = form.build();
    return new RequestEntityBuilder().url(url).httpMethod(HttpMethod.POST).headers(headers).body(body);
  }
  
  public static RequestEntityBuilder createCategoryUpdateRequest(Long categoryId, MultiPartFormBuilder form) {
    String url = ADMIN_CATEGORIES_BASE_URL + "/" + categoryId;
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
    MultiValueMap<String, Object> body = form.build();
    return new RequestEntityBuilder().url(url).httpMethod(HttpMethod.PUT).headers(headers).body(body);
  }

  public static RequestEntityBuilder createCategoryDeleteRequest(Long categoryId) {
    String url = ADMIN_CATEGORIES_BASE_URL + "/" + categoryId;
    return new RequestEntityBuilder().url(url).httpMethod(HttpMethod.DELETE);
  }
}
