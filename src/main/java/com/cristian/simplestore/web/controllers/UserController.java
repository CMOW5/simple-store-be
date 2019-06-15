package com.cristian.simplestore.web.controllers;

import com.cristian.simplestore.persistence.entities.User;
import com.cristian.simplestore.persistence.repositories.UserRepository;
import com.cristian.simplestore.security.CurrentUser;
import com.cristian.simplestore.security.UserPrincipal;
import com.cristian.simplestore.web.dto.response.user.UserResponse;
import com.cristian.simplestore.web.utils.response.ApiResponse;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

  private final UserRepository userRepository;
  
  @Autowired
  public UserController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @GetMapping("/me")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<?> getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
    User user = userRepository.findById(userPrincipal.getId())
        .orElseThrow(() -> new EntityNotFoundException(
            "Couldnt find the User with id " + userPrincipal.getId()));

    return new ApiResponse().status(HttpStatus.OK).content(UserResponse.build(user)).build();
  }
}
