package com.cristian.simplestore.web.controllers;

import com.cristian.simplestore.security.oauth2.AuthProvider;
import com.cristian.simplestore.web.dto.request.user.LoginRequest;
import com.cristian.simplestore.web.dto.request.user.SignUpRequest;
import com.cristian.simplestore.web.dto.response.AuthResponse;
import com.cristian.simplestore.web.dto.response.user.UserResponseDto;
import com.cristian.simplestore.web.exceptions.BadRequestException;
import com.cristian.simplestore.web.utils.response.ApiResponse;
import com.cristian.simplestore.persistence.entities.User;
import com.cristian.simplestore.persistence.repositories.UserRepository;
import com.cristian.simplestore.security.CurrentUser;
import com.cristian.simplestore.security.TokenProvider;
import com.cristian.simplestore.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.validation.Valid;
import java.net.URI;

@Profile("!test") // TODO: fix this: AuthenticationManager authenticationManager is not wired
                  // correctly in test profile
@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private TokenProvider tokenProvider;

  @PostMapping("/login")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication =
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
            loginRequest.getEmail(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    User user =
        userRepository.findById(((UserPrincipal) authentication.getPrincipal()).getId()).get();

    String token = tokenProvider.createToken(authentication);

    return new ApiResponse().status(HttpStatus.OK).content(new AuthResponse(token, user))
        .addHeader("Authorization", "Bearer " + token).build();
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      throw new BadRequestException("Email address already in use.");
    }

    // Creating user's account
    User user = new User();
    user.setName(signUpRequest.getName());
    user.setEmail(signUpRequest.getEmail());
    user.setPassword(signUpRequest.getPassword());
    user.setProvider(AuthProvider.local);

    user.setPassword(passwordEncoder.encode(user.getPassword()));

    User result = userRepository.save(user);

    URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/me")
        .buildAndExpand(result.getId()).toUri();


    return new ApiResponse().status(HttpStatus.CREATED).content("User registered successfully@")
        .build();

    // return ResponseEntity.created(location)
    // .body(new ApiResponse(true, "User registered successfully@"));
  }
  
  @PostMapping("/logout")
  public ResponseEntity<?> logoutUser(@CurrentUser UserPrincipal userPrincipal) {
   // TODO: ban the token
    return new ApiResponse().status(HttpStatus.OK).content("logged out").build();
  }


}
