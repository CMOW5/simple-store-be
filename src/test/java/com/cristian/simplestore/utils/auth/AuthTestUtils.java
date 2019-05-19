package com.cristian.simplestore.utils.auth;

import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.cristian.simplestore.business.services.AuthService;
import com.cristian.simplestore.utils.MultiPartFormBuilder;
import com.cristian.simplestore.web.dto.request.user.LoginRequest;
import com.cristian.simplestore.web.dto.request.user.SignUpRequest;
import com.cristian.simplestore.web.dto.response.LoginResponse;
import com.github.javafaker.Faker;

@Component
public class AuthTestUtils {

  AuthService authService;

  Faker faker = new Faker();

  @Autowired
  public AuthTestUtils(AuthService authService) {
    this.authService = authService;
  }

  public SignUpRequest generateSignUpRequest() {
    SignUpRequest signUpRequest = new SignUpRequest();
    signUpRequest.setName(generateRandomName());
    signUpRequest.setEmail(generateRandomEmail());
    signUpRequest.setPassword(generateRandomPassword());
    return signUpRequest;
  }

  public LoginRequest generateLoginRequest() {
    SignUpRequest signUpRequest = generateSignUpRequest();
    
    // save the user on db
    authService.signup(signUpRequest);
    
    LoginRequest loginRequest = new LoginRequest();
    loginRequest.setEmail(signUpRequest.getEmail());
    loginRequest.setPassword(signUpRequest.getPassword());
    return loginRequest;
  }

  public HashMap<String, Object> generateSignUpRequestForm() {
    SignUpRequest signUpRequest = generateSignUpRequest();
    HashMap<String, Object> form = new HashMap<String, Object>();
    form.put("name", signUpRequest.getName());
    form.put("email", signUpRequest.getEmail());
    form.put("password", signUpRequest.getPassword());
    return form;
  }
  
  public MultiPartFormBuilder generateSignUpRequestForm2() {
    SignUpRequest signUpRequest = generateSignUpRequest();
    MultiPartFormBuilder form = new MultiPartFormBuilder();
    form.add("name", signUpRequest.getName());
    form.add("email", signUpRequest.getEmail());
    form.add("password", signUpRequest.getPassword());
    return form;
  }

  public HashMap<String, Object> generateWrongSignUpRequestForm() {
    HashMap<String, Object> form = new HashMap<String, Object>();
    form.put("name", "abc");
    form.put("email", "wronEmail");
    form.put("password", "123");
    return form;
  }

  public HashMap<String, Object> generateLoginRequestForm() {
    SignUpRequest signUpRequest = generateSignUpRequest();

    // save the user on db
    authService.signup(signUpRequest);

    HashMap<String, Object> form = new HashMap<String, Object>();
    form.put("email", signUpRequest.getEmail());
    form.put("password", signUpRequest.getPassword());

    return form;
  }

  public HashMap<String, Object> generateWrongLoginRequestForm() {
    HashMap<String, Object> form = new HashMap<String, Object>();
    form.put("email", "nonExistentEmail@example.com");
    form.put("password", "123456");
    return form;
  }

  public String generateToken() {
    LoginRequest loginRequest = generateLoginRequest();
    LoginResponse loginResponse = authService.login(loginRequest);
    return loginResponse.getToken();
  }


  public String generateRandomName() {
    return faker.name().fullName();
  }

  public String generateRandomEmail() {
    return faker.internet().emailAddress();
  }

  public String generateRandomPassword() {
    return faker.internet().password();
  }
}
