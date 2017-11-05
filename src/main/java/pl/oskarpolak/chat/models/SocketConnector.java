package pl.oskarpolak.chat.models;

import javafx.application.Platform;
import pl.oskarpolak.chat.controllers.MainController;

import javax.websocket.*;
import java.io.IOException;
import java.net.Socket;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@ClientEndpoint
public class SocketConnector {
    private static SocketConnector ourInstance = new SocketConnector();
    public static SocketConnector getInstance() {
        return ourInstance;
    }

    private WebSocketContainer container;
    private Session session;
    private List<SocketObserver> socketObservers;

    private SocketConnector() {
        container = ContainerProvider.getWebSocketContainer();
        socketObservers = new ArrayList<>();
    }

    public void connect() {
        URI uri = URI.create("ws://localhost:8080/chat");
        try {
            container.connectToServer(this, uri);
        } catch (DeploymentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Połączono!");
    }

    @OnOpen
    public void onOpen(Session session){
        this.session = session;
        System.out.println("POLACZONO");
    }

    public void sendMessage(String message){
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnMessage
    public void onMessage(Session session, String message){
        socketObservers.forEach(s -> {
            Platform.runLater(() -> s.onMessage(message));
        });
    }


    public void registerObserver(SocketObserver observer) {
        socketObservers.add(observer);
    }
}
