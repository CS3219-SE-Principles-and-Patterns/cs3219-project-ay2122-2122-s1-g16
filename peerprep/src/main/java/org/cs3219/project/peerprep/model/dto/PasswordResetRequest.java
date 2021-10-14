package org.cs3219.project.peerprep.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Getter
@Setter
public class PasswordResetRequest {
    @Email(message = "email must be a valid email address")
    private String email;
    @NotBlank(message = "password cannot be blank")
    private String password;
    @NotBlank(message = "password token cannot be blank")
    private String token;
}
