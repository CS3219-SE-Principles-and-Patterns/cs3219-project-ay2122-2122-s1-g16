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
        synchronized (MatchMaking.class) {
            MatchMaking.reset();
            for (int j = 0; j < groupSize; j++) {
                for (int i = 0; i < level; i++) {
                    Peer user = peers[i][j];
                    MatchMaking.addPeer(user);
                }
            }

            Thread.sleep(1000);

            SoftAssertions softly = new SoftAssertions();
            long[][] peerIds = new long[level][groupSize];
            for (int i = 0; i < level; i++) {
                for (int j = 0; j < groupSize; j++) {
                    Peer user = peers[i][j];
                    if (peerIds[i][j] != 0) { // peer of user already visited
                        softly.assertThat(user.getPeer().getUserId())
                                .as("Unmatched peer ids.")
                                .isEqualTo(peerIds[i][j]);
                        validateInterviewer(user, user.getPeer());
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

                        validateInterviewer(user, user.getPeer());
                    }
                }
            }
            softly.assertAll();
        }
    }

    @Test
    public void testUnmatch() throws InterruptedException {
        synchronized (MatchMaking.class) {
            MatchMaking.reset();
            for (int j = 0; j < groupSize; j++) {
                for (int i = 0; i < level; i++) {
                    Peer user = peers[i][j];
                    MatchMaking.addPeer(user);
                    MatchMaking.addInactivePeer(user);
                }
            }

            Thread.sleep(1000);

            SoftAssertions softly = new SoftAssertions();
            for (int i = 0; i < level; i++) {
                for (int j = 0; j < groupSize; j++) {
                    Peer user = peers[i][j];
                    softly.assertThat(user.getPeer())
                            .as("No user should be matched to any peer.")
                            .isNull();
                }
            }

            softly.assertAll();
        }
    }

    @Test
    public void testFifo() throws InterruptedException {
        synchronized (MatchMaking.class) {
            MatchMaking.reset();
            for (int j = 0; j < groupSize - 2; j++) {
                Peer user = peers[0][j];
                MatchMaking.addPeer(user);
                if (j % 2 == 0) {
                    MatchMaking.addInactivePeer(user);
                }
            }

            Thread.sleep(1000);

            SoftAssertions softly = new SoftAssertions();
            for (int j = 0; j < groupSize - 2; j++) {
                Peer user = peers[0][j];
                if (j % 4 == 1) {
                    softly.assertThat(user.getPeer().getUserId())
                            .as("User matching not in FIFO manner.")
                            .isEqualTo(user.getUserId() + 2);
                    validateInterviewer(user, user.getPeer());
                } else if (j % 2 == 1) {
                    softly.assertThat(user.getPeer().getUserId())
                            .as("User matching not in FIFO manner.")
                            .isEqualTo(user.getUserId() - 2);
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
