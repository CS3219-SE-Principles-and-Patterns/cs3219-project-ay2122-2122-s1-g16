package org.cs3219.project.peerprep.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SaveAnswerRequest {
    private Long userId;

    private Long questionId;

    private String answer;
}
