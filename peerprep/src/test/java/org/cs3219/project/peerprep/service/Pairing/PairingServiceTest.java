package org.cs3219.project.peerprep.service.Pairing;

import org.assertj.core.api.SoftAssertions;
import org.cs3219.project.peerprep.model.dto.PairingRequest;
import org.cs3219.project.peerprep.model.dto.PairingResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

@SpringBootTest
public class PairingServiceTest {

    private static int level = 3;

    private static int groupSize = 2;

    private PairingRequest[] requests = new PairingRequest[level * groupSize];

    @Autowired
    private PairingService pairingService;

    @BeforeEach
    public void init() {
        for (int i = 0; i < level * groupSize; i++) {
            Long userId = (long) (i + 1);
            PairingRequest request = PairingRequest.builder()
                    .userId(userId)
                    .difficulty(i / groupSize)
                    .build();
            requests[i] = request;
        }
    }

    @Test
    public void testPairing() throws ExecutionException, InterruptedException {
        ThreadPoolExecutor executor =
                (ThreadPoolExecutor) Executors.newFixedThreadPool(6);
        SoftAssertions softly = new SoftAssertions();
        List<Future<PairingResponse>> futures = new ArrayList<>();

        for (int i = 0; i < level * groupSize; i++) {
            PairingRequest request = requests[i];
            Future<PairingResponse> future = executor.submit(() -> {
                try {
                    return pairingService.getPairingResult(request);
                } catch (InterruptedException e) {
                    softly.fail("Pairing service interrupted.");
                    return null;
                }
            });
            futures.add(future);
        }

        long[] peerIds = new long[level * groupSize];

        for (Future<PairingResponse> future : futures) {
            PairingResponse response = future.get();
            long userId = response.getUserId();
            long peerId = response.getPeerId();
            int difficulty = response.getDifficulty();
            int requestPos = (int) (userId - 1);
            PairingRequest request = requests[requestPos];

            softly.assertThat(userId)
                    .as("Unmatched userId in request and response")
                    .isEqualTo(request.getUserId());
            softly.assertThat(difficulty)
                    .as("Unmatched difficulty in request and response")
                    .isEqualTo(request.getDifficulty());
            softly.assertThat(requests[(int) (peerId - 1)].getDifficulty())
                    .as("Unmatched difficulty for user and peer.")
                    .isEqualTo(difficulty);

            if (peerIds[requestPos] == 0) {
                peerIds[requestPos] = peerId;
            } else {
                softly.assertThat(peerIds[requestPos])
                        .as("User matched to multiple peers.")
                        .isEqualTo(peerId);
            }
        }
    }
}
