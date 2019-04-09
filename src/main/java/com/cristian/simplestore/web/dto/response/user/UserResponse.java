package com.cristian.simplestore.web.dto.response.user;

import com.cristian.simplestore.persistence.entities.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserResponseDto {
  
  private Long id;

  private String name;

  private String email;

  private String imageUrl;
  
  public UserResponseDto(User user) {
    this.id = user.getId();
    this.name = user.getName();
    this.email = user.getEmail();
    this.imageUrl = user.getImageUrl();
  }
  
  public static UserResponseDto build(User user) {
    if (user != null) {
      return new UserResponseDto(user);
    } else {
      return null;
    }
  }
}
