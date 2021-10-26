package org.cs3219.project.peerprep.service.Pairing;

import org.assertj.core.api.SoftAssertions;
import org.cs3219.project.peerprep.model.entity.Peer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MatchMakingTest {

    private final int level = 3;

    private final int groupSize = 10;

    private Peer[] peers = new Peer[level * groupSize];

    private final static MatchMaking singleton = MatchMaking.getSingleton();

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
    public void testMatch() throws InterruptedException {
        synchronized (singleton) {
            for (int i = 0; i < level * groupSize; i++) {
                Peer user = peers[i];
                singleton.addPeer(user);
            }

            Thread.sleep(1000);

            SoftAssertions softly = new SoftAssertions();
            for (int i = 0; i < level * groupSize; i++) {
                Peer user = peers[i];
                Peer peer = peers[Math.toIntExact(user.getPeer().getUserId())];

                // mutual matching
                softly.assertThat(user.getUserId())
                        .as("Unmatched peer ids.")
                        .isEqualTo(peer.getPeer().getUserId());

                // same difficulty
                softly.assertThat(peer.getDifficulty())
                        .as("Unmatched difficulty.")
                        .isEqualTo(user.getDifficulty());

                validateInterviewer(user, user.getPeer());
            }
            softly.assertAll();
        }
    }

    @Test
    public void testUnmatch() throws InterruptedException {
        synchronized (singleton) {
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

    @Test
    public void testMixed() throws InterruptedException {
        synchronized (singleton) {
            for (int j = 0; j < 4; j++) {
                Peer user = peers[level * j];
                // match 3 to 9, and dequeue 0 and 6
                singleton.addPeer(user);
                if (j % 2 == 0) {
                    singleton.addInactivePeer(user);
                }
                Thread.sleep(1000);
            }

            Thread.sleep(1000);

            SoftAssertions softly = new SoftAssertions();
            for (int j = 0; j < 4; j++) {
                Peer user = peers[level * j];
                if (j % 2 == 1) {
                    softly.assertThat(user.getPeer().getPeer().getUserId())
                            .as("User matching not in FIFO manner.")
                            .isEqualTo(user.getUserId());
                    validateInterviewer(user, user.getPeer());
                } else {
                    softly.assertThat(user.getPeer()).isNull();
                }
            }

            softly.assertAll();
        }
    }

    private void validateInterviewer(Peer p1, Peer p2) {
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(p1.getInterviewer() + p2.getInterviewer())
                .as("Interviewer and interviewee not assigned correctly.")
                .isEqualTo(1);
        softly.assertAll();
    }
}
