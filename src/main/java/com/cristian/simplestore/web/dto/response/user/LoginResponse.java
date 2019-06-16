package com.cristian.simplestore.web.dto.response.user;

import com.cristian.simplestore.persistence.entities.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginResponse {
    private String token;
    private String tokenType = "Bearer";
    private UserResponse user;

    public LoginResponse(String token, User user) {
        this.token = token;
        this.user = UserResponse.build(user);
    }
}
