package com.project.advertstand.WebSocketPush;

import lombok.extern.slf4j.Slf4j;

import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.push.Push;

import javax.faces.push.PushContext;

import javax.inject.Inject;
import javax.inject.Named;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


@Named
@Slf4j
@Startup
@ApplicationScoped
@ServerEndpoint("/websocket")
public class WebSocketPush implements Serializable {

    private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());

    @OnOpen
    public void onOpen(Session peer) {
        sessions.add(peer);
    }

    @OnClose
    public void onClose(Session peer) {
        sessions.remove(peer);
    }

    public void sendMessageToSocket(String message) {
        for (Session session : sessions) {
            if (session.isOpen()) {
                try {
                    session.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                sessions.remove(session);
            }
        }

    }
}
