package org.cs3219.project.peerprep.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InterviewResponse {
    private Long questionId;

    private String title;

    private String question;

    private Integer difficulty;

    private String solution;

    private Integer role;
}
