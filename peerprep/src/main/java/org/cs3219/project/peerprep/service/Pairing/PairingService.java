package org.cs3219.project.peerprep.service.Pairing;

import org.cs3219.project.peerprep.model.dto.PairingRequest;
import org.cs3219.project.peerprep.model.dto.PairingResponse;

public interface PairingService {
    PairingResponse getPairingResult(final PairingRequest pairingRequest) throws InterruptedException;
    void getExitPairingResult(final PairingRequest pairingRequest) throws InterruptedException;
}
