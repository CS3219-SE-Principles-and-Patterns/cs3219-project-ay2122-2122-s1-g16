package org.cs3219.project.peerprep.model.dto.interview;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InterviewDetailsResponse {

    @JsonProperty("user_set")
    private InterviewDetail userSet;

    @JsonProperty("peer_set")
    private InterviewDetail peerSet;
}
