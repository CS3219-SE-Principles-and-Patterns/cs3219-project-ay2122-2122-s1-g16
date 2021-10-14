package org.cs3219.project.peerprep.component;

public class Peer {

    private final Long userId;

    private final int difficulty;

    private Peer peer;

    public Peer(Long userId, int difficulty) {
        this.userId = userId;
        this.difficulty = difficulty;
    }

    public Long getUserId() {
        return userId;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setPeer(Peer peer) {
        this.peer = peer;
    }

    public Peer getPeer() {
        return this.peer;
    }
}
