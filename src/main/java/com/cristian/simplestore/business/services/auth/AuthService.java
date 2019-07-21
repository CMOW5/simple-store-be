package com.cristian.simplestore.business.services.auth;

import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.cristian.simplestore.persistence.entities.User;
import com.cristian.simplestore.persistence.repositories.UserRepository;
import com.cristian.simplestore.security.TokenProvider;
import com.cristian.simplestore.security.UserPrincipal;
import com.cristian.simplestore.security.oauth2.AuthProvider;
import com.cristian.simplestore.web.dto.request.user.LoginRequest;
import com.cristian.simplestore.web.dto.request.user.SignUpRequest;
import com.cristian.simplestore.web.dto.response.LoginResponse;
import com.cristian.simplestore.web.exceptions.BadRequestException;

// @Service
public class AuthService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private TokenProvider tokenProvider;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public User signup(SignUpRequest signUpRequest) {
    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      throw new BadRequestException("Email address already in use.");
    }

    User user = new User();
    user.setName(signUpRequest.getName());
    user.setEmail(signUpRequest.getEmail());
    user.setPassword(signUpRequest.getPassword());
    user.setProvider(AuthProvider.local);

    user.setPassword(passwordEncoder.encode(user.getPassword()));

    return userRepository.save(user);
  }

  public LoginResponse login(LoginRequest loginRequest) {
    Authentication authentication =
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
            loginRequest.getEmail(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    User user = userRepository.findById(((UserPrincipal) authentication.getPrincipal()).getId())
        .orElseThrow(() -> new EntityNotFoundException("the user was not found"));

    String token = tokenProvider.createToken(authentication);

    return new LoginResponse(token, user);
  }
}
