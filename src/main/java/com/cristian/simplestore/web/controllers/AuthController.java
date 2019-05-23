package com.cristian.simplestore.web.controllers;

import com.cristian.simplestore.web.dto.request.user.LoginRequest;
import com.cristian.simplestore.web.dto.request.user.SignUpRequest;
import com.cristian.simplestore.web.dto.response.LoginResponse;
import com.cristian.simplestore.web.dto.response.user.UserResponse;
import com.cristian.simplestore.web.utils.response.ApiResponse;
import com.cristian.simplestore.business.services.auth.AuthService;
import com.cristian.simplestore.persistence.entities.User;
import com.cristian.simplestore.security.CurrentUser;
import com.cristian.simplestore.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
  
  @Autowired
  private AuthService authService;

  @PostMapping("/login")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    LoginResponse loginResponse = authService.login(loginRequest);
    
    return new ApiResponse().status(HttpStatus.OK).content(loginResponse)
        .addHeader("Authorization", "Bearer " + loginResponse.getToken()).build();
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
    User user = authService.signup(signUpRequest);
    return new ApiResponse().status(HttpStatus.CREATED).content(UserResponse.build(user))
        .build();
  }
  
  @PostMapping("/logout")
  public ResponseEntity<?> logoutUser(@CurrentUser UserPrincipal userPrincipal) {
   // TODO: ban the token
    return new ApiResponse().status(HttpStatus.OK).content("logged out").build();
  }
  
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> handleControllersValidationMethodArgumentNotValidException(
      HttpServletRequest request, MethodArgumentNotValidException exception) {
    return new ApiResponse().errors(exception.getBindingResult().getFieldErrors())
        .status(HttpStatus.BAD_REQUEST).build();
  }

}
