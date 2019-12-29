package com.cristian.simplestore.utils.request;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;

public class RequestEntityBuilder {
  
  private String url;
  private HttpMethod method;
  private HttpHeaders headers;
  private Map<?, ?> body;
  private Map<String, String> requestParams = new HashMap<String, String>();
   
  public RequestEntityBuilder() {}
  
  public RequestEntityBuilder url(String url) {
    this.url = url;
    return this;
  }

  public RequestEntityBuilder httpMethod(HttpMethod method) {
    this.method = method;
    return this;
  }

  public RequestEntityBuilder headers(HttpHeaders headers) {
    this.headers = HttpHeaders.writableHttpHeaders(headers);
    return this;
  }

  public RequestEntityBuilder body(Map<?, ?> body) {
    this.body = body;
    return this;
  }
  
  public RequestEntityBuilder addRequestParam(String attribute, String value) {
    requestParams.put(attribute, value);
    return this;
  }

  public RequestEntity<?> build() {
    URI uri = null;
    try {
      uri = new URI(buildUrl());
    } catch (URISyntaxException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return new RequestEntity<>(body, headers, method, uri);
  }
  
  private String buildUrl() {
    if (requestParams.isEmpty()) return url;
    url += "?";
    requestParams.forEach((String attribute, String value) -> {
      url += "&" + attribute + "=" + value;
    }); 
    return url;
  }
}