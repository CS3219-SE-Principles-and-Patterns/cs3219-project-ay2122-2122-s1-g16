package org.cs3219.project.peerprep.model.dto.interview;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserAttemptDetail {
    private Long questionId;

    private String title;

    private String question;

    private Integer difficulty;

    private String solution;

    private List<UserAttempt> attempts;
}
