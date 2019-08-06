package com.cristian.simplestore.infrastructure.web.dto.user;

import com.cristian.simplestore.infrastructure.adapters.repository.entities.UserEntity;

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