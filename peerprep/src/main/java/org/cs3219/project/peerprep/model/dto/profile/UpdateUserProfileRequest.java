package org.cs3219.project.peerprep.model.dto.profile;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserProfileRequest {
    @JsonProperty("id")
    @Positive(message = "id must be a positive integer")
    private int id;

    @JsonProperty("username")
    @NotBlank(message = "username cannot be blank")
    private String nickname;
}
