package com.cristian.simplestore.infrastructure.web.controllers.user.dto;

import com.cristian.simplestore.infrastructure.database.user.UserEntity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginResponse {
    private String token;
    private String tokenType = "Bearer";
    private UserResponse user;

    public LoginResponse(String token, UserEntity user) {
        this.token = token;
        this.user = UserResponse.build(user);
    }
}