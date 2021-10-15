package org.cs3219.project.peerprep.service.Pairing;

import org.cs3219.project.peerprep.model.dto.PairingRequest;
import org.cs3219.project.peerprep.model.dto.PairingResponse;

public interface PairingService {
    PairingResponse getPairingResult(PairingRequest pairingRequest) throws InterruptedException;
}
