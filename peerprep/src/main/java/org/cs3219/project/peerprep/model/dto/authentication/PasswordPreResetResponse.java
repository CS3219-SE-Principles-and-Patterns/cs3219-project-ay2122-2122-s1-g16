package org.cs3219.project.peerprep.model.dto.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class PasswordPreResetResponse {
    @JsonProperty("email")
    private String email;

    @JsonProperty("token")
    private String token;
}
