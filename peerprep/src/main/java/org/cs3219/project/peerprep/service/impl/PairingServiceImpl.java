package org.cs3219.project.peerprep.service.impl;

import org.cs3219.project.peerprep.model.dto.PairingRequest;
import org.cs3219.project.peerprep.model.dto.PairingResponse;
import org.cs3219.project.peerprep.model.entity.Peer;
import org.cs3219.project.peerprep.service.Pairing.MatchMaking;
import org.cs3219.project.peerprep.service.PairingService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PairingServiceImpl implements PairingService {

    private MatchMaking matchMaking = MatchMaking.getSingleton();

    @Async
    @Override
    public PairingResponse getPairingResult(final PairingRequest pairingRequest) throws InterruptedException {
        final Peer user = new Peer(pairingRequest.getUserId(), pairingRequest.getDifficulty(), LocalDateTime.now());
        matchMaking.addPeer(user);

        while (true) {
            if (user.getPeer() != null) {
                return PairingResponse.builder()
                        .userId(user.getUserId())
                        .peerId(user.getPeer().getUserId())
                        .difficulty(user.getDifficulty())
                        .interviewer(user.getInterviewer())
                        .build();
            }
            Thread.sleep(1000);
        }
    }

    @Override
    public void getExitPairingResult(final PairingRequest pairingRequest) {
        final Peer user = new Peer(pairingRequest.getUserId(), pairingRequest.getDifficulty(), LocalDateTime.now());
        matchMaking.addInactivePeer(user);
    }
}
