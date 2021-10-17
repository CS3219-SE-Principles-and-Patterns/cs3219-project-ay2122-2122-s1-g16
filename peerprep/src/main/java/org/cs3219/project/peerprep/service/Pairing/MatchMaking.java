package org.cs3219.project.peerprep.service.Pairing;

import org.cs3219.project.peerprep.model.entity.Peer;

import java.util.ArrayList;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;

public class MatchMaking {

    private static final int LEVEL = 3;

    private static volatile ArrayList<BlockingDeque<Peer>> queues = loadQueue();

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

    static {
        for (int i = 0; i < LEVEL; i++) {
            final int difficulty = i;
            executor.execute(() -> {
                while (true) {
                    match(difficulty);
                }
            });
        }
    }

    private static void match(int difficulty) {
        BlockingDeque<Peer> queue = queues.get(difficulty);
        if (queue.size() >= 2) {
            Peer p1 = queue.poll();
            Peer p2 = queue.poll();
            p1.setPeer(p2);
            p2.setPeer(p1);
        }
    }

    public static void addPeer(Peer peer) {
        int difficulty = peer.getDifficulty();
        queues.get(difficulty).add(peer);
    }
}
