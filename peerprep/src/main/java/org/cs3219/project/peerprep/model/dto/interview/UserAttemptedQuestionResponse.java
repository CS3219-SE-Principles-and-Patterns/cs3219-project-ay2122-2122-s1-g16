package org.cs3219.project.peerprep.model.dto.interview;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserAttemptedQuestionResponse {
    private Long userId;

    private List<UserAttemptedQuestion> questions;
}
