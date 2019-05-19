package com.cristian.simplestore.integration.controllers.product.request;

import java.io.IOException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import com.cristian.simplestore.integration.controllers.TestRequest;
import com.cristian.simplestore.utils.MultiPartFormBuilder;
import com.cristian.simplestore.utils.request.JsonResponse;
import com.cristian.simplestore.utils.request.RequestEntityBuilder;
import com.cristian.simplestore.utils.request.RequestSender;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public abstract class ProductRequest extends TestRequest {

	public ProductRequest(RequestSender requestSender) {
		super(requestSender);
	}

	public JsonResponse sendFindAllProductsRequest() {
		RequestEntityBuilder requestBuilder = createFindAllProductsRequest();
		ResponseEntity<String> jsonResponse = send(requestBuilder.build());
		return new JsonResponse(jsonResponse);
	}

	protected RequestEntityBuilder createFindAllProductsRequest() {
		String url = "/api/admin/products";
		return new RequestEntityBuilder().url(url).httpMethod(HttpMethod.GET);
	}

	public JsonResponse sendFindProductByIdRequest(Long id)
			throws JsonParseException, JsonMappingException, IOException {
		RequestEntityBuilder requestBuilder = createFindProductByIdRequest(id);
		ResponseEntity<String> jsonResponse = send(requestBuilder.build());
		return new JsonResponse(jsonResponse);
	}

	protected RequestEntityBuilder createFindProductByIdRequest(Long id) {
		String url = "/api/admin/products/" + id;
		return new RequestEntityBuilder().url(url).httpMethod(HttpMethod.GET);
	}

	public JsonResponse sendProductCreateRequest(MultiPartFormBuilder form)
			throws JsonParseException, JsonMappingException, IOException {
		RequestEntityBuilder requestBuilder = createProductCreateRequest(form);
		ResponseEntity<String> jsonResponse = send(requestBuilder.build());
		return new JsonResponse(jsonResponse);
	}

	protected RequestEntityBuilder createProductCreateRequest(MultiPartFormBuilder form) {
		String url = "/api/admin/products";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		MultiValueMap<String, Object> body = form.build();
		return new RequestEntityBuilder().url(url).httpMethod(HttpMethod.POST).headers(headers).body(body);
	}

	public JsonResponse sendProductUpdateRequest(Long productId, MultiPartFormBuilder form)
			throws JsonParseException, JsonMappingException, IOException {
		RequestEntityBuilder requestBuilder = createProductUpdateRequest(productId, form);
		ResponseEntity<String> jsonResponse = send(requestBuilder.build());
		return new JsonResponse(jsonResponse);
	}

	protected RequestEntityBuilder createProductUpdateRequest(Long productId, MultiPartFormBuilder form) {
		String url = "/api/admin/products/" + productId;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		MultiValueMap<String, Object> body = form.build();
		return new RequestEntityBuilder().url(url).httpMethod(HttpMethod.PUT).headers(headers).body(body);
	}

	public JsonResponse sendProductDeleteRequest(Long productId)
			throws JsonParseException, JsonMappingException, IOException {
		RequestEntityBuilder requestBuilder = createProductDeleteRequest(productId);
		ResponseEntity<String> jsonResponse = send(requestBuilder.build());
		return new JsonResponse(jsonResponse);
	}

	protected RequestEntityBuilder createProductDeleteRequest(Long productId) {
		String url = "/api/admin/products/" + productId;
		return new RequestEntityBuilder().url(url).httpMethod(HttpMethod.DELETE);
	}
}
