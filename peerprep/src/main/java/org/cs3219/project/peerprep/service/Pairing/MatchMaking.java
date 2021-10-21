package org.cs3219.project.peerprep.service.Pairing;

import org.cs3219.project.peerprep.model.entity.Peer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;

public class MatchMaking {

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
            final int difficulty = i;
            final BlockingDeque<Peer> queue = queues.get(difficulty);
            final HashSet<Peer> inactiveUsers = inactiveSets.get(difficulty);
            executor.execute(() -> match(queue, inactiveUsers));
        }
    }

    private static void match(final BlockingDeque<Peer> queue, final HashSet<Peer> inactiveUsers) {
        while (true) {
            while (inactiveUsers.contains(queue.peek())) {
                inactiveUsers.remove(queue.poll());
            }
            while (queue.isEmpty()) {}
            final Peer p1 = queue.poll();
            while (inactiveUsers.contains(queue.peek())) {
                inactiveUsers.remove(queue.poll());
            }
            while (queue.isEmpty()) {}
            final Peer p2 = queue.poll();
            p1.setPeer(p2);
            p2.setPeer(p1);
        }
    }

    public static void addPeer(final Peer peer) {
        final int difficulty = peer.getDifficulty();
        queues.get(difficulty).add(peer);
    }

    public static void addInactivePeer(final Peer peer) {
        final int difficulty = peer.getDifficulty();
        inactiveSets.get(difficulty).add(peer);
    }
}
