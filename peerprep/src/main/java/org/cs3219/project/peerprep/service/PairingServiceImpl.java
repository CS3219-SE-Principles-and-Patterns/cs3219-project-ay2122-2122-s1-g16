package org.cs3219.project.peerprep.service;

import org.cs3219.project.peerprep.component.MatchMaking;
import org.cs3219.project.peerprep.component.Peer;
import org.cs3219.project.peerprep.model.dto.PairingRequest;
import org.cs3219.project.peerprep.model.dto.PairingResponse;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.ExecutionException;

public class PairingServiceImpl implements PairingService {

    @Async
    @Override
    public PairingResponse getPairingResult(PairingRequest pairingRequest) throws ExecutionException, InterruptedException {
        Peer user = new Peer(pairingRequest.getUserId(), pairingRequest.getDifficulty());
        MatchMaking.addPeer(user);

        while (true) {
            if (user.getPeer() != null) {
                return PairingResponse.builder()
                        .peerId(user.getPeer().getUserId())
                        .build();
            }
            Thread.sleep(1000);
        }
    }
}
