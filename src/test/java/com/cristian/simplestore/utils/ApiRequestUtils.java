package com.cristian.simplestore.utils;

import java.io.IOException;
import java.util.Map;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ApiRequestUtils {
	
	private TestRestTemplate restTemplate;
	
	public ApiRequestUtils(TestRestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	public ResponseEntity<String> sendRequest(String url, HttpMethod method, HttpHeaders headers, MultiValueMap<String, Object> body) {
		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
		ResponseEntity<String> response = this.restTemplate.exchange(url, method, requestEntity, String.class);
		return response;
	}
	
	public <T> T getContentFromJsonRespose(String jsonResponse, Class<T> classType) throws JsonParseException, JsonMappingException, IOException {	
	    ObjectMapper mapper = new ObjectMapper();
	    Map<?, ?> mapResponse = mapper.readValue(jsonResponse, Map.class);
	    
	    T content = mapper.convertValue(mapResponse.get("content"), classType);
	    return content;
	}
	
	public Map<?, ?> mapJsonRespose(String jsonResponse) throws JsonParseException, JsonMappingException, IOException {	
	    ObjectMapper mapper = new ObjectMapper();
	    Map<?, ?> mapResponse = mapper.readValue(jsonResponse, Map.class);
	    Map<?, ?> content = mapper.convertValue(mapResponse.get("content"), Map.class);
	    return content;
	}
}
