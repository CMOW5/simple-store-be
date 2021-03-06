package com.cristian.simplestore.infrastructure.web.controllers.user.dto;

import com.cristian.simplestore.infrastructure.database.user.UserEntity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserResponse {
  
  private Long id;

  private String name;

  private String email;

  private String imageUrl;
  
  public UserResponse(UserEntity user) {
    this.id = user.getId();
    this.name = user.getName();
    this.email = user.getEmail();
    this.imageUrl = user.getImageUrl();
  }
  
  public static UserResponse build(UserEntity user) {
    if (user != null) {
      return new UserResponse(user);
    } else {
      return null;
    }
  }
}
