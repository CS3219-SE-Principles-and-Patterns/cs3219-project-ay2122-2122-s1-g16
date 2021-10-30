package org.cs3219.project.peerprep.model.dto.interview;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InterviewDetailsRequest {
    private Long userId;

    private Long peerId;

    private Integer difficulty;
}
