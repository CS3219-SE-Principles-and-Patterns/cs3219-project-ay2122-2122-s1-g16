package org.cs3219.project.peerprep.model.dto.interview;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class UserAttemptDetail {
    private Long userId;

    private Long questionId;

    private String title;

    private String question;

    private Integer difficulty;

    private String solution;

    private String attemptedAnswer;

    private Timestamp attemptAt;
}
