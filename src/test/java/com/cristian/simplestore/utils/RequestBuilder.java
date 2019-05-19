package com.cristian.simplestore.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.cristian.simplestore.utils.auth.AuthTestUtils;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@Deprecated
public class RequestBuilder {

  private String url;
  private HttpMethod method;
  private HttpHeaders headers;
  private Map<?, ?> body;
  private Map<String, String> requestParams = new HashMap<String, String>();

  // this is not required because when the
  // unit.services tests are loaded this
  // dependency cannot be resolved because
  // this needs a @SpringBootTest(webEnvironment)
  // context which is not provided on unit tests
  @Autowired(required = false)
  private TestRestTemplate restTemplate;
  
  @Autowired
  AuthTestUtils authTestUtils;

  public RequestBuilder() {}
  
  public RequestBuilder url(String url) {
    this.url = url;
    return this;
  }

  public RequestBuilder httpMethod(HttpMethod method) {
    this.method = method;
    return this;
  }

  public RequestBuilder headers(HttpHeaders headers) {
    this.headers = headers;
    return this;
  }

  public RequestBuilder body(Map<?, ?> body) {
    this.body = body;
    return this;
  }
  
  public RequestBuilder addRequestParam(String attribute, String value) {
    requestParams.put(attribute, value);
    return this;
  }

  public RequestBuilder withJwtAuth() {
    if (headers == null) {
      headers = new HttpHeaders();
    }
    String token = authTestUtils.generateToken();
    headers.add("Authorization", "Bearer " + token);
    return this;
  }

  public HttpEntity<?> build() {
    buildUrl();
    return new HttpEntity<>(body, headers);
  }
  
  private void buildUrl() {
    if (requestParams.isEmpty()) return;
    url += "?";
    requestParams.forEach((String attribute, String value) -> {
      url += "&" + attribute + "=" + value;
    }); 
  }

  public ResponseEntity<String> send() {
    HttpEntity<?> requestEntity = build();
    ResponseEntity<String> response =
        restTemplate.exchange(url, method, requestEntity, String.class);
    resetFields();
    return response;
  }
  
  public static Map<?, ?> mapJsonRespose(String jsonResponse) throws JsonParseException, JsonMappingException, IOException {
    ObjectMapper mapper = new ObjectMapper();
    Map<?, ?> mappedResponse = mapper.readValue(jsonResponse, Map.class);
    return mappedResponse;
  }
  
  public static <T> T getValueFromJsonRespose(String jsonResponse, String key, Class<T> classType)
      throws JsonParseException, JsonMappingException, IOException {
    ObjectMapper mapper = new ObjectMapper();
    Map<?, ?> mappedResponse = mapJsonRespose(jsonResponse);
    T content = mapper.convertValue(mappedResponse.get(key), classType);
    return content;
  }
   
  public static <T> T getContentFromJsonRespose(String jsonResponse, Class<T> classType)
      throws JsonParseException, JsonMappingException, IOException {
    ObjectMapper mapper = new ObjectMapper();
    Map<?, ?> mappedResponse = mapJsonRespose(jsonResponse);
    T content = mapper.convertValue(mappedResponse.get("content"), classType);
    return content;
  }
  
  public static <T> T getPaginatorFromJsonRespose(String jsonResponse, Class<T> classType)
      throws JsonParseException, JsonMappingException, IOException {
    ObjectMapper mapper = new ObjectMapper();
    Map<?, ?> mappedResponse = mapJsonRespose(jsonResponse);
    T content = mapper.convertValue(mappedResponse.get("paginator"), classType);
    return content;
  }
  
  public static <T> T getErrorsFromJsonRespose(String jsonResponse, Class<T> classType)
      throws JsonParseException, JsonMappingException, IOException {
    ObjectMapper mapper = new ObjectMapper();
    Map<?, ?> mappedResponse = mapJsonRespose(jsonResponse);
    T errors = mapper.convertValue(mappedResponse.get("errors"), classType);
    return errors;
  }
  
  private void resetFields() {
    url = null;
    method = null;
    headers = null;
    body = null;
    requestParams = new HashMap<String, String>();
  }
}
