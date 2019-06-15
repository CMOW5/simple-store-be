package com.cristian.simplestore.integration.controllers.auth;

import static org.assertj.core.api.Assertions.assertThat;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import com.cristian.simplestore.integration.controllers.BaseIntegrationTest;
import com.cristian.simplestore.utils.auth.AuthTestUtils;
import com.cristian.simplestore.utils.request.JsonResponse;
import com.cristian.simplestore.web.dto.response.LoginResponse;
import com.cristian.simplestore.web.dto.response.user.UserResponse;
import com.cristian.simplestore.web.utils.response.ApiError;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AuthControllerTest extends BaseIntegrationTest {

	@Autowired
	TestRestTemplate restTemplate;

	@Autowired
	AuthTestUtils authUtils;

	@Autowired
	AuthRequest authRequest;

	@Test
	public void testSignUp() throws JsonParseException, JsonMappingException, IOException {
		HashMap<String, Object> signUpRequestForm = authUtils.generateSignUpRequestForm();

		JsonResponse response = authRequest.sendSignUpRequest(signUpRequestForm);

		UserResponse user = (UserResponse) response.getContent(UserResponse.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(user.getName()).isEqualTo(signUpRequestForm.get("name"));
		assertThat(user.getEmail()).isEqualTo(signUpRequestForm.get("email"));
	}

	@Test
	public void testSignUpErrors() throws JsonParseException, JsonMappingException, IOException {
		HashMap<String, Object> signUpRequestForm = authUtils.generateWrongSignUpRequestForm();

		JsonResponse response = authRequest.sendSignUpRequest(signUpRequestForm);

		List<ApiError> errors = (List) response.getErrors(List.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		assertThat(errors.size()).isEqualTo(3);
	}

	@Test
	public void testLogin() throws JsonParseException, JsonMappingException, IOException {
		HashMap<String, Object> loginRequestForm = authUtils.generateLoginRequestForm();

		JsonResponse response = authRequest.sendLoginRequest(loginRequestForm);

		LoginResponse loginResponse = (LoginResponse) response.getContent(LoginResponse.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(loginResponse.getUser().getEmail()).isEqualTo(loginRequestForm.get("email"));
		assertThat(loginResponse.getToken()).isNotEmpty();
	}

	@Test
	public void testLoginErrors() throws JsonParseException, JsonMappingException, IOException {
		HashMap<String, Object> form = authUtils.generateWrongLoginRequestForm();

		JsonResponse response = authRequest.sendLoginRequest(form);

		// List<ApiError> errors =
		// (List) this.getErrorsFromJsonRespose(response.getBody(), List.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
	}

	@Test
	public void testLogout() throws JsonParseException, JsonMappingException, IOException {
		String token = authUtils.generateToken();

		JsonResponse response = authRequest.sendLogoutRequest(token);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
}
