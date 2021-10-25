package org.cs3219.project.peerprep.service.Pairing;

import org.cs3219.project.peerprep.model.entity.Peer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;

class MatchMaking {

    private static final int LEVEL = 3;

    private static volatile ArrayList<BlockingDeque<Peer>> queues = loadQueue();

    private static volatile ArrayList<HashSet<Peer>> inactiveSets = loadSet();

    private static ThreadPoolExecutor executor =
            (ThreadPoolExecutor) Executors.newFixedThreadPool(LEVEL);

    private static ArrayList<BlockingDeque<Peer>> loadQueue() {
        ArrayList<BlockingDeque<Peer>> ret = new ArrayList<>();
        for (int i = 0; i < LEVEL; i++) {
            BlockingDeque<Peer> queue = new LinkedBlockingDeque<>();
            ret.add(queue);
        }
        return ret;
    }

    private static ArrayList<HashSet<Peer>> loadSet() {
        ArrayList<HashSet<Peer>> ret = new ArrayList<>();
        for (int i = 0; i < LEVEL; i++) {
            HashSet<Peer> set = new HashSet<>();
            ret.add(set);
        }
        return ret;
    }

    static {
        for (int i = 0; i < LEVEL; i++) {
            final BlockingDeque<Peer> queue = queues.get(i);
            final HashSet<Peer> inactiveUsers = inactiveSets.get(i);
            executor.execute(() -> match(queue, inactiveUsers));
        }
    }

    private static void match(final BlockingDeque<Peer> queue, final HashSet<Peer> inactiveUsers) {
        Peer p1 = null;
        Peer p2 = null;

        // set to true if p1 is inactive when p2 is found in the previous match
        boolean halfMatch = false;

        while (true) {
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
            p1.setPeer(p2);
            p1.setInterviewer(1);
            p2.setPeer(p1);
            p2.setInterviewer(0);
            halfMatch = false;
        }
    }

    static void addPeer(final Peer peer) {
        final int difficulty = peer.getDifficulty();
        queues.get(difficulty).add(peer);
    }

    static void addInactivePeer(final Peer peer) {
        final int difficulty = peer.getDifficulty();
        inactiveSets.get(difficulty).add(peer);
    }
}
