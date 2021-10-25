package org.cs3219.project.peerprep.service.Pairing;

import org.cs3219.project.peerprep.model.dto.PairingRequest;
import org.cs3219.project.peerprep.model.dto.PairingResponse;
import org.cs3219.project.peerprep.model.entity.Peer;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class PairingServiceImpl implements PairingService {

    @Async
    @Override
    public PairingResponse getPairingResult(final PairingRequest pairingRequest) throws InterruptedException {
        final Peer user = new Peer(pairingRequest.getUserId(), pairingRequest.getDifficulty());
        MatchMaking.addPeer(user);

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
        final Peer user = new Peer(pairingRequest.getUserId(), pairingRequest.getDifficulty());
        MatchMaking.addInactivePeer(user);
    }
}
