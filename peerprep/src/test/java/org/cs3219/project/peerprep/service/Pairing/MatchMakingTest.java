package org.cs3219.project.peerprep.service.Pairing;

import org.assertj.core.api.SoftAssertions;
import org.cs3219.project.peerprep.model.entity.Peer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MatchMakingTest {

    private final static int level = 3;

    private final static int groupSize = 10;

    private Peer[][] peers = new Peer[level][groupSize];

    @BeforeEach
    public void init() {
        for (int i = 0; i < level; i++) {
            for (int j = 0; j < groupSize; j++) {
                Long userId = (long) (i * groupSize + j + 1);
                Peer user = new Peer(userId, i);
                peers[i][j] = user;
            }
        }
    }

    @Test
    public void testMatch() throws InterruptedException {
        for (int j = 0; j < groupSize; j++) {
            for (int i = 0; i < level; i++) {
                Peer user = peers[i][j];
                MatchMaking.addPeer(user);
            }
        }
        Thread.sleep(1000);

        SoftAssertions softly = new SoftAssertions();
        long[][] peerIds = new long[level][groupSize];
        for (int i = 0 ; i < level; i++) {
            for (int j = 0; j < groupSize; j++) {
                Peer user = peers[i][j];
                if (peerIds[i][j] != 0) { // peer of user already visited
                    softly.assertThat(user.getPeer().getUserId())
                            .as("Unmatched peer ids.")
                            .isEqualTo(peerIds[i][j]);
                } else { // peer of user unvisited
                    // assert peer with same difficulty
                    softly.assertThat(user.getPeer().getDifficulty())
                            .as("User " + user.getUserId() + " is matched to more than one peer.")
                            .isEqualTo(user.getDifficulty());

                    // mark user's peerId as peer, and peer's peerId as user
                    Long peerId = user.getPeer().getUserId();
                    int peerPos = (int) (peerId - i * groupSize - 1);
                    softly.assertThat(peerIds[i][peerPos])
                            .as("User " + peerId + " is matched to more than one peer.")
                            .isEqualTo(0);
                    peerIds[i][j] = peerId;
                    peerIds[i][peerPos] = user.getUserId();
                }
            }
        }
        softly.assertAll();
    }
}
