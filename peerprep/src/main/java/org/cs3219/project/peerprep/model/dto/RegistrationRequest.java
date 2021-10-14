package org.cs3219.project.peerprep.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Getter
@Setter
public class RegistrationRequest {
    @Email(message = "email must be a valid email address")
    private String email;
    @NotBlank(message = "nickname cannot be blank")
    private String nickname;
    private String password;
}
