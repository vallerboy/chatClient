package pl.oskarpolak.chat.models;

import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.WebSocketContainer;
import java.net.URI;

@ClientEndpoint
public class SocketConnector {
    private static SocketConnector ourInstance = new SocketConnector();
    public static SocketConnector getInstance() {
        return ourInstance;
    }

    private WebSocketContainer container;

    private SocketConnector() {
        container = ContainerProvider.getWebSocketContainer();
    }

    public void connect() {
        URI uri = URI.create("ws://localhost:8080/chat");

    }
}
