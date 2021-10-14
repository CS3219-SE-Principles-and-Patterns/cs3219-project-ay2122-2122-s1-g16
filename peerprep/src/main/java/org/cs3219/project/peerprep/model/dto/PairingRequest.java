package org.cs3219.project.peerprep.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PairingRequest {
    private Long userId;

    private Integer difficulty;
}
