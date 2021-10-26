package org.cs3219.project.peerprep.service.Pairing;

import org.cs3219.project.peerprep.model.entity.Peer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;

public class MatchMaking {

    private final int LEVEL = 3;

    private volatile ArrayList<BlockingDeque<Peer>> queues = loadQueue();

    private volatile ArrayList<HashSet<Peer>> inactiveSets = loadSet();

    private Object[] queueLocks = loadLocks();

    private Object[] setLocks = loadLocks();

    private ThreadPoolExecutor executor =
            (ThreadPoolExecutor) Executors.newFixedThreadPool(LEVEL);

    private volatile static MatchMaking singleton;

    private MatchMaking () {}

    public static MatchMaking getSingleton() {
        if (singleton == null) {
            synchronized (MatchMaking.class) {
                if (singleton == null) {
                    singleton = new MatchMaking();
                }
            }
        }
        return singleton;
    }

    private ArrayList<BlockingDeque<Peer>> loadQueue() {
        ArrayList<BlockingDeque<Peer>> ret = new ArrayList<>();
        for (int i = 0; i < LEVEL; i++) {
            BlockingDeque<Peer> queue = new LinkedBlockingDeque<>();
            ret.add(queue);
        }
        return ret;
    }

    private ArrayList<HashSet<Peer>> loadSet() {
        ArrayList<HashSet<Peer>> ret = new ArrayList<>();
        for (int i = 0; i < LEVEL; i++) {
            HashSet<Peer> set = new HashSet<>();
            ret.add(set);
        }
        return ret;
    }

    private Object[] loadLocks() {
        Object[] locks = new Object[LEVEL];
        for (int i = 0; i < LEVEL; i++) {
            locks[i] = new Object();
        }
        return locks;
    }

    static {
        getSingleton().execute();
    }

    public void execute() {
        for (int i = 0; i < getSingleton().LEVEL; i++) {
            int difficulty = i;
            getSingleton().executor.execute(() -> match(difficulty));
        }
    }

    private static void match(final int difficulty) {
        Peer p1 = null;
        Peer p2 = null;

        // set to true if p1 is inactive when p2 is found in the previous match
        boolean halfMatch = false;

        while (true) {
            final BlockingDeque<Peer> queue = getSingleton().queues.get(difficulty);
            final HashSet<Peer> inactiveUsers = getSingleton().inactiveSets.get(difficulty);

            // get the first active user
            if (halfMatch) {
                p1 = p2;
            } else {
                while (queue.isEmpty()) {}
                while (inactiveUsers.remove(p1 = queue.poll()) || p1 == null) {}
            }

            // get the second active user
            while (queue.isEmpty()) {}
            while (inactiveUsers.remove(p2 = queue.poll()) || p2 == null) {}

            // recheck if the first user is active
            if (inactiveUsers.remove(p1)) {
                halfMatch = true;
                continue;
            }

            // match p1 and p2
            p1.setInterviewer(1);
            p2.setInterviewer(0);
            p1.setPeer(p2);
            p2.setPeer(p1);
            halfMatch = false;
        }
    }

    public void addPeer(final Peer peer) {
        final int difficulty = peer.getDifficulty();
        synchronized (queueLocks[difficulty]) {
            queues.get(difficulty).add(peer);
        }
    }

    public void addInactivePeer(final Peer peer) {
        final int difficulty = peer.getDifficulty();
        synchronized (setLocks[difficulty]) {
            inactiveSets.get(difficulty).add(peer);
        }
    }
}
