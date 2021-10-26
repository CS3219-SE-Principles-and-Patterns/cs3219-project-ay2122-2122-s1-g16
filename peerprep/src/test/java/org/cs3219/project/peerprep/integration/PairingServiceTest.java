package org.cs3219.project.peerprep.integration;

import org.assertj.core.api.SoftAssertions;
import org.cs3219.project.peerprep.model.dto.PairingRequest;
import org.cs3219.project.peerprep.model.dto.PairingResponse;
import org.cs3219.project.peerprep.service.Pairing.MatchMaking;
import org.cs3219.project.peerprep.service.PairingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

@Disabled
@SpringBootTest
public class PairingServiceTest {

    private int level = 3;

    private int groupSize = 2;

    private PairingRequest[] requests = new PairingRequest[level * groupSize];

    @Autowired
    private PairingService pairingService;

    @BeforeEach
    public void init() {
        for (int i = 0; i < level * groupSize; i++) {
            Long userId = (long) (i);
            PairingRequest request = PairingRequest.builder()
                    .userId(userId)
                    .difficulty(i % level)
                    .build();
            requests[i] = request;
        }
    }

    @Test
    public void testPairing() throws ExecutionException, InterruptedException {
        synchronized (MatchMaking.class) {
            // MatchMaking.reset();
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

            for (int i = 0; i < futures.size(); i++) {
                Future<PairingResponse> future = futures.get(i);
                PairingResponse response = future.get();
                long userId = response.getUserId();
                long peerId = response.getPeerId();
                int difficulty = response.getDifficulty();
                PairingRequest request = requests[i];

                softly.assertThat(userId)
                        .as("Unmatched userId in request and response")
                        .isEqualTo(request.getUserId());
                softly.assertThat(difficulty)
                        .as("Unmatched difficulty in request and response")
                        .isEqualTo(request.getDifficulty());
                softly.assertThat(requests[(int) (peerId)].getDifficulty())
                        .as("Unmatched difficulty for user and peer.")
                        .isEqualTo(difficulty);

                PairingResponse peerResponse = futures.get((int) peerId).get();
                softly.assertThat(peerResponse.getPeerId())
                        .as("User matched to multiple peers.")
                        .isEqualTo(userId);
                validateInterviewer(response, peerResponse);
            }
            softly.assertAll();
        }
    }

    private void validateInterviewer(PairingResponse r1, PairingResponse r2) {
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(r1.getInterviewer() + r2.getInterviewer())
                .as("Interviewer and interviewee not assigned correctly.")
                .isEqualTo(1);
        softly.assertAll();
    }
}
