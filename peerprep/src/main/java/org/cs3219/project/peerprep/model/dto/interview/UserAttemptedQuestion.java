package org.cs3219.project.peerprep.model.dto.interview;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class UserAttemptedQuestion {
    private Long questionId;

    private String title;

    private Integer difficulty;

    private Timestamp attemptedAt;
}
