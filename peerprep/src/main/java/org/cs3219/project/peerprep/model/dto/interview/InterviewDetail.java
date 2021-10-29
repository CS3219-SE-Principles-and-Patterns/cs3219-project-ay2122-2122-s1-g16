package org.cs3219.project.peerprep.model.dto.interview;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InterviewDetail {
    private Long questionId;

    private String title;

    private String question;

    private Integer difficulty;

    private String solution;
}
