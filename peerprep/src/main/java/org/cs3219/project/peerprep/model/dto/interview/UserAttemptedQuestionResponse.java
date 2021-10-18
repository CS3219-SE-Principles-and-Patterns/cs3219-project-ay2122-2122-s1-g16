package org.cs3219.project.peerprep.model.dto.interview;

import lombok.Builder;
import lombok.Data;
import org.cs3219.project.peerprep.common.api.CommonPage;

@Data
@Builder
public class UserAttemptedQuestionResponse {
    private Long userId;

    private CommonPage<UserAttemptedQuestion> questions;
}
