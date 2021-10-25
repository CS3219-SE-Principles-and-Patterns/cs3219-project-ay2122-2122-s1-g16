package org.cs3219.project.peerprep.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PairingResponse {

    @JsonProperty("id")
    private Long userId;

    @JsonProperty("peer_id")
    private Long peerId;

    @JsonProperty("difficulty")
    private int difficulty;

    @JsonProperty("interviewer")
    private int interviewer;
}
