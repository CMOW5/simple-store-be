package com.cristian.simplestore.utils;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Component
public class CategoryRequest extends TestRequest {

	private static String ADMIN_CATEGORIES_BASE_URL = "/api/hex/admin/categories";

	@Autowired
	public CategoryRequest(RequestSender requestSender) {
		super(requestSender);
	}

	public JsonResponse sendFindAllCategoriesRequest() throws JsonParseException, JsonMappingException, IOException {
		// TODO: template method here
		RequestEntityBuilder requestBuilder = createFindAllCategoriesRequest();
		ResponseEntity<String> jsonResponse = send(requestBuilder.build());
		return new JsonResponse(jsonResponse);
	}

	public RequestEntityBuilder createFindAllCategoriesRequest() {
		String url = ADMIN_CATEGORIES_BASE_URL;
		return new RequestEntityBuilder().url(url).httpMethod(HttpMethod.GET);
	}

	public JsonResponse sendFindCategoryByIdRequest(Long id)
			throws JsonParseException, JsonMappingException, IOException {
		RequestEntityBuilder requestBuilder = createFindCategoryByIdRequest(id);
		ResponseEntity<String> jsonResponse = send(requestBuilder.build());
		return new JsonResponse(jsonResponse);

	}

	protected RequestEntityBuilder createFindCategoryByIdRequest(Long id) {
		String url = ADMIN_CATEGORIES_BASE_URL + "/" + id;
		return new RequestEntityBuilder().url(url).httpMethod(HttpMethod.GET);
	}

//	public JsonResponse sendCategoryCreateRequest(MultiPartFormBuilder form)
//			throws JsonParseException, JsonMappingException, IOException {
//		RequestEntityBuilder requestBuilder = createCategoryCreateRequest(form);
//		ResponseEntity<String> jsonResponse = send(requestBuilder.build());
//		return new JsonResponse(jsonResponse);
//	}
//
//	protected RequestEntityBuilder createCategoryCreateRequest(MultiPartFormBuilder form) {
//		String url = ADMIN_CATEGORIES_BASE_URL;
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//		MultiValueMap<String, Object> body = form.build();
//		return new RequestEntityBuilder().url(url).httpMethod(HttpMethod.POST).headers(headers).body(body);
//	}
//
//	public JsonResponse sendCategoryUpdateRequest(Long categoryId, MultiPartFormBuilder form)
//			throws JsonParseException, JsonMappingException, IOException {
//		RequestEntityBuilder requestBuilder = createCategoryUpdateRequest(categoryId, form);
//		ResponseEntity<String> jsonResponse = send(requestBuilder.build());
//		return new JsonResponse(jsonResponse);
//	}
//
//	protected RequestEntityBuilder createCategoryUpdateRequest(Long categoryId, MultiPartFormBuilder form) {
//		String url = ADMIN_CATEGORIES_BASE_URL + "/" + categoryId;
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//		MultiValueMap<String, Object> body = form.build();
//		return new RequestEntityBuilder().url(url).httpMethod(HttpMethod.PUT).headers(headers).body(body);
//	}

	public JsonResponse sendCategoryDeleteRequest(Long categoryId)
			throws JsonParseException, JsonMappingException, IOException {
		RequestEntityBuilder requestBuilder = createCategoryDeleteRequest(categoryId);
		ResponseEntity<String> jsonResponse = send(requestBuilder.build());
		return new JsonResponse(jsonResponse);
	}

	protected RequestEntityBuilder createCategoryDeleteRequest(Long categoryId) {
		String url = ADMIN_CATEGORIES_BASE_URL + "/" + categoryId;
		return new RequestEntityBuilder().url(url).httpMethod(HttpMethod.DELETE);
	}
}