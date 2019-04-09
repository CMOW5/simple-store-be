package com.cristian.simplestore.web.dto.response.user;

import com.cristian.simplestore.persistence.entities.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserResponse {
  
  private Long id;

  private String name;

  private String email;

  private String imageUrl;
  
  public UserResponse(User user) {
    this.id = user.getId();
    this.name = user.getName();
    this.email = user.getEmail();
    this.imageUrl = user.getImageUrl();
  }
  
  public static UserResponse build(User user) {
    if (user != null) {
      return new UserResponse(user);
    } else {
      return null;
    }
  }
}
