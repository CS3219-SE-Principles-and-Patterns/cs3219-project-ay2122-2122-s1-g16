package org.cs3219.project.peerprep.service;

import org.cs3219.project.peerprep.model.dto.PairingRequest;
import org.cs3219.project.peerprep.model.dto.PairingResponse;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component("PairingService")
public interface PairingService {
    PairingResponse getPairingResult(PairingRequest pairingRequest) throws ExecutionException, InterruptedException;
}
