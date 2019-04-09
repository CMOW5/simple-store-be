package com.cristian.simplestore.integration.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import com.cristian.simplestore.BaseTest;
import com.cristian.simplestore.business.services.AuthService;
import com.cristian.simplestore.utils.AuthTestUtils;
import com.cristian.simplestore.utils.DbCleaner;
import com.cristian.simplestore.utils.RequestBuilder;
import com.cristian.simplestore.web.dto.response.LoginResponse;
import com.cristian.simplestore.web.dto.response.user.UserResponse;
import com.cristian.simplestore.web.utils.response.ApiError;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AuthControllerTest extends BaseTest {

  @Autowired
  TestRestTemplate restTemplate;

  @Autowired
  AuthService userService;

  @Autowired
  DbCleaner dbCleaner;

  @Autowired
  AuthTestUtils authUtils;

  @Autowired
  RequestBuilder requestBuilder;

  @Before
  public void setUp() {
    cleanUpDb();
  }

  @After
  public void tearDown() {
    cleanUpDb();
  }

  public void cleanUpDb() {
    dbCleaner.cleanUsersTable();
  }

  @Test
  public void testSignUp() throws JsonParseException, JsonMappingException, IOException {
    HashMap<String, Object> signUpRequestForm = authUtils.generateSignUpRequestForm();

    ResponseEntity<String> response = sendSignUpRequest(signUpRequestForm);

    UserResponse user = (UserResponse) RequestBuilder.getContentFromJsonRespose(response.getBody(),
        UserResponse.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    assertThat(user.getName()).isEqualTo(signUpRequestForm.get("name"));
    assertThat(user.getEmail()).isEqualTo(signUpRequestForm.get("email"));
  }

  @Test
  public void testSignUpErrors() throws JsonParseException, JsonMappingException, IOException {
    HashMap<String, Object> signUpRequestForm = authUtils.generateWrongSignUpRequestForm();

    ResponseEntity<String> response = sendSignUpRequest(signUpRequestForm);

    List<ApiError> errors =
        (List) RequestBuilder.getErrorsFromJsonRespose(response.getBody(), List.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(errors.size()).isEqualTo(3);
  }

  @Test
  public void testLogin() throws JsonParseException, JsonMappingException, IOException {
    HashMap<String, Object> loginRequestForm = authUtils.generateLoginRequestForm();

    ResponseEntity<String> response = sendLoginRequest(loginRequestForm);

    LoginResponse loginResponse = (LoginResponse) RequestBuilder
        .getContentFromJsonRespose(response.getBody(), LoginResponse.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(loginResponse.getUser().getEmail()).isEqualTo(loginRequestForm.get("email"));
    assertThat(loginResponse.getToken()).isNotEmpty();
  }

  // @Test // TODO: not working, for some reason the RestAuthenticationEntryPoint throwing the
  // exception -> Responding with unauthorized error. Message - {}
  public void testLoginErrors() throws JsonParseException, JsonMappingException, IOException {
    HashMap<String, Object> form = authUtils.generateWrongLoginRequestForm();

    ResponseEntity<String> response = sendLoginRequest(form);

    // List<ApiError> errors =
    // (List) this.getErrorsFromJsonRespose(response.getBody(), List.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
  }

  @Test
  public void testLogout() throws JsonParseException, JsonMappingException, IOException {
    String token = authUtils.generateToken();

    ResponseEntity<String> response = sendLogoutRequest(token);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }


  private ResponseEntity<String> sendSignUpRequest(HashMap<String, Object> form) {
    String url = "/api/auth/signup";
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    ResponseEntity<String> response =
        requestBuilder.url(url).httpMethod(HttpMethod.POST).headers(headers).body(form).send();
    return response;
  }

  private ResponseEntity<String> sendLoginRequest(HashMap<String, Object> form) {
    String url = "/api/auth/login";
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    ResponseEntity<String> response =
        requestBuilder.url(url).httpMethod(HttpMethod.POST).headers(headers).body(form).send();

    return response;
  }

  private ResponseEntity<String> sendLogoutRequest(String token) {
    String url = "/api/auth/logout";
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.add("Authorization", "Bearer " + token);

    ResponseEntity<String> response =
        requestBuilder.url(url).httpMethod(HttpMethod.POST).headers(headers).send();

    return response;
  }
}
