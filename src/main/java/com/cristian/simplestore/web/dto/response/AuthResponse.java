package com.cristian.simplestore.web.dto.response;

import com.cristian.simplestore.persistence.entities.User;
import com.cristian.simplestore.web.dto.response.user.UserResponseDto;

public class AuthResponse {
    private String token;
    private String tokenType = "Bearer";
    private UserResponseDto user;

    public AuthResponse(String token, User user) {
        this.token = token;
        this.user = UserResponseDto.build(user);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String accessToken) {
        this.token = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public UserResponseDto getUser() {
      return user;
    }

    public void setUser(UserResponseDto user) {
      this.user = user;
    }
}
