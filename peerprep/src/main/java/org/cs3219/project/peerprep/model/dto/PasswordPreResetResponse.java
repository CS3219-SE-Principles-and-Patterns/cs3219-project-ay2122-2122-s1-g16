package org.cs3219.project.peerprep.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class PasswordPreResetResponse {
    private String email;
    private String token;
}
