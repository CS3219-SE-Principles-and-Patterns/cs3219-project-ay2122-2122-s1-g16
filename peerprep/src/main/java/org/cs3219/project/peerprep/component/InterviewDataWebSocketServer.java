package org.cs3219.project.peerprep.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.cs3219.project.peerprep.model.dto.interview.PeerMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ServerEndpoint("/interview/editing/{userId}")
@Component
@Slf4j
public class InterviewDataWebSocketServer {

    /**
     * Keep record of current number of connections
     */
    private static final AtomicInteger onlineCount = new AtomicInteger(0);

    /**
     * Store all online connected server-client
     */
    private static final Map<String, InterviewDataWebSocketServer> webSocketServerMap = new ConcurrentHashMap<>();

    private Session session;

    private String userId;

    @Autowired
    private ObjectMapper objectMapper = new ObjectMapper();

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        this.session = session;
        this.userId = userId;
        if (webSocketServerMap.containsKey(userId)) {
            webSocketServerMap.remove(userId);
            webSocketServerMap.put(userId, this);
        } else {
            webSocketServerMap.put(userId, this);
            onlineCount.incrementAndGet();
        }

        try {
            sendMessage("Successfully connected!");
            log.info("[InterviewDataWebSocketServer.onOpen] connected userId:{}, total online users:{}", userId, onlineCount.get());
        } catch (IOException e) {
            log.error("[InterviewDataWebSocketServer.onOpen] userId:{}, error: ", userId, e);
        }
    }

    @OnClose
    public void onClose() {
        if (webSocketServerMap.containsKey(userId)) {
            webSocketServerMap.remove(userId);
            onlineCount.decrementAndGet();
        }
        log.info("[InterviewDataWebSocketServer.onClose] exited userId:{}, total online users:{}", userId, onlineCount.get());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("[InterviewDataWebSocketServer.onMessage.MessageSource] userId:{}, message:{}", userId, message);
        try {
            if (StringUtils.isNotBlank(message)) {
                PeerMessage peerMessage = objectMapper.readValue(message, PeerMessage.class);
                String peerId = peerMessage.getPeerId().toString();
                if (StringUtils.isNotBlank(peerId) && webSocketServerMap.containsKey(peerId)) {
                    webSocketServerMap.get(peerId).sendMessage(peerMessage.getData());
                } else {
                    log.error("[InterviewDataWebSocketServer.onMessage.PeerNotFound] peerId:{}", peerId);
                }
            }
        } catch (IOException e) {
            log.error("[InterviewDataWebSocketServer.OnMessage] error: ", e);
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("[InterviewDataWebSocketServer.onError] userId:{}, error: ", userId, error);
    }

    public void sendMessage(String message) throws IOException {
        this.session.getAsyncRemote().sendText(message);
    }
}
