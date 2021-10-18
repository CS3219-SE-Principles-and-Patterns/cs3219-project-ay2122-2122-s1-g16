package org.cs3219.project.peerprep.model.dto.interview;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class UserAttempt {
    private String attemptedAnswer;

    private Timestamp attemptedAt;
}
