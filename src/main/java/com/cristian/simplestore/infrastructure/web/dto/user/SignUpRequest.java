package com.cristian.simplestore.infrastructure.web.dto.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignUpRequest {
    @NotBlank
    @Size(min = 6, max = 100)
    private String name;

    @NotBlank
    @Email
    @Size(min = 6, max = 100)
    private String email;

    @NotBlank
    @Size(min = 6, max = 100)
    private String password;
}