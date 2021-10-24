package org.cs3219.project.peerprep.model.dto.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Getter
@Setter
public class PasswordResetRequest {
    @JsonProperty("email")
    @Email(message = "email must be a valid email address")
    @NotBlank(message = "email cannot be blank")
    private String email;

    @JsonProperty("password")
    @NotBlank(message = "password cannot be blank")
    private String password;

    @JsonProperty("token")
    @NotBlank(message = "password token cannot be blank")
    private String token;
}
