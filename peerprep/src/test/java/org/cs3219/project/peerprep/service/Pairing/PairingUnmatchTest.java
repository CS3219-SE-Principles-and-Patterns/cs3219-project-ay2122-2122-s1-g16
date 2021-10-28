package org.cs3219.project.peerprep.service.Pairing;

import org.assertj.core.api.SoftAssertions;
import org.cs3219.project.peerprep.model.entity.Peer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PairingUnmatchTest {

    private final int level = 3;

    private final int groupSize = 10;

    private Peer[] peers = new Peer[level * groupSize];

    private static MatchMaking singleton = MatchMaking.getNewForTest();

    static {
        singleton.execute();
    }

    @BeforeEach
    public void init() {
        for (int i = 0; i < level * groupSize; i++) {
            Long userId = (long) (i);
            Peer user = new Peer(userId, i % level);
            peers[i] = user;
        }
    }

    @Test
    public void testUnmatch() throws InterruptedException {
        synchronized (MatchMaking.class) {
            for (int i = 0; i < level * groupSize; i++) {
                Peer user = peers[i];
                singleton.addPeer(user);
                singleton.addInactivePeer(user);
            }

            Thread.sleep(1000);

            SoftAssertions softly = new SoftAssertions();
            for (int i = 0; i < level * groupSize; i++) {
                Peer user = peers[i];
                softly.assertThat(user.getPeer())
                        .as("No user should be matched to any peer.")
                        .isNull();
            }

            softly.assertAll();
        }
    }

}
