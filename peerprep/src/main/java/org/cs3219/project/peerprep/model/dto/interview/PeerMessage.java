package org.cs3219.project.peerprep.model.dto.interview;

import lombok.Data;

@Data
public class PeerMessage {

    private Long peerId;

    private String data;

    private Boolean switchRole;
}
