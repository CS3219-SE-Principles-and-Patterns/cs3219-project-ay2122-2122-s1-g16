package org.cs3219.project.peerprep.model.entity;

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

    @Override
    public int hashCode() {
        return this.userId.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        final Peer other = (Peer) obj;
        if (this.userId == null) {
            if (other.userId != null) {
                return false;
            }
        } else if (!this.userId.equals(other.userId)) {
            return false;
        }
        // both userIds are null or equal
        return this.difficulty == other.difficulty;
    }
}
