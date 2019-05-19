package com.cristian.simplestore.integration.controllers.auth;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.cristian.simplestore.integration.controllers.TestRequest;
import com.cristian.simplestore.utils.request.JsonResponse;
import com.cristian.simplestore.utils.request.RequestEntityBuilder;
import com.cristian.simplestore.utils.request.RequestSender;

@Component
public class AuthRequest extends TestRequest {

	public static final String AUTH_BASE_URL = "/api/auth";
	public static final String SIGNUP_URL = AUTH_BASE_URL + "/signup";
	public static final String LOGIN_URL = AUTH_BASE_URL + "/login";
	public static final String LOGOUT_URL = AUTH_BASE_URL + "/logout";
	
	@Autowired
	public AuthRequest(RequestSender requestSender) {
		super(requestSender);
	}

	public JsonResponse sendSignUpRequest(HashMap<String, Object> form) {
		RequestEntityBuilder requestBuilder = createSignUpRequest(form);
		ResponseEntity<String> response = send(requestBuilder.build());		
		return new JsonResponse(response);
	}
	
	protected RequestEntityBuilder createSignUpRequest(HashMap<String, Object> form) {
		String url = SIGNUP_URL;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return new RequestEntityBuilder().url(url).httpMethod(HttpMethod.POST).headers(headers).body(form);
	}

	public JsonResponse sendLoginRequest(HashMap<String, Object> form) {
		RequestEntityBuilder requestBuilder = createLoginRequest(form);
		ResponseEntity<String> response = send(requestBuilder.build());
		return new JsonResponse(response);
	}
	
	protected RequestEntityBuilder createLoginRequest(HashMap<String, Object> form) {
		String url = LOGIN_URL;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);		
		return new RequestEntityBuilder().url(url).httpMethod(HttpMethod.POST).headers(headers)
				.body(form);
	}

	public JsonResponse sendLogoutRequest(String token) {
		RequestEntityBuilder requestBuilder = createLogoutRequest(token);
		ResponseEntity<String> response = send(requestBuilder.build());
		return new JsonResponse(response);
	}
	
	protected RequestEntityBuilder createLogoutRequest(String token) {
		String url = LOGOUT_URL;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", "Bearer " + token);
		return new RequestEntityBuilder().url(url).httpMethod(HttpMethod.POST).headers(headers);
	}

}
