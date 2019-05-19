package com.cristian.simplestore.utils.request;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonResponse {
	
	ResponseEntity<String> responseEntity;
	
	public JsonResponse(ResponseEntity<String> responseEntity) {
		this.responseEntity = responseEntity;
	}
	
	public ResponseEntity<String> getResponseEntity() {
		return responseEntity;
	}
	
	public HttpStatus getStatusCode() {
		return responseEntity.getStatusCode();
	}
	
	public String getBody() {
		return responseEntity.getBody();
	}
	
	public <T> T getValueFromJsonRespose(String key, Class<T> classType)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		Map<?, ?> mappedResponse = mapJsonRespose(responseEntity.getBody());
		T content = mapper.convertValue(mappedResponse.get(key), classType);
		return content;
	}
	
	private Map<?, ?> mapJsonRespose(String jsonResponse)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		Map<?, ?> mappedResponse = mapper.readValue(jsonResponse, Map.class);
		return mappedResponse;
	}

	public <T> T getContentFromJsonRespose(Class<T> classType)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		Map<?, ?> mappedResponse = mapJsonRespose(responseEntity.getBody());
		T content = mapper.convertValue(mappedResponse.get("content"), classType);
		return content;
	}

	public <T> T getPaginatorFromJsonRespose(Class<T> classType)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		Map<?, ?> mappedResponse = mapJsonRespose(responseEntity.getBody());
		T content = mapper.convertValue(mappedResponse.get("paginator"), classType);
		return content;
	}

	public <T> T getErrorsFromJsonRespose(Class<T> classType)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		Map<?, ?> mappedResponse = mapJsonRespose(responseEntity.getBody());
		T errors = mapper.convertValue(mappedResponse.get("errors"), classType);
		return errors;
	}
}
