package org.cs3219.project.peerprep.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class RegistrationResponse {
    private int id;
    private String email;
    private String nickname;
    private String token;
}
