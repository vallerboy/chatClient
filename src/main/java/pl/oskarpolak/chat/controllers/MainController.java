package pl.oskarpolak.chat.controllers;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import pl.oskarpolak.chat.models.SocketConnector;
import pl.oskarpolak.chat.models.SocketObserver;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable, SocketObserver{


    @FXML
    Button buttonSend;

    @FXML
    TextArea textMessages;

    @FXML
    TextField textWriteMessage;

    private SocketConnector socketConnector = SocketConnector.getInstance();

    public void initialize(URL location, ResourceBundle resources) {
        clickEnterOnWriteMessage();

        socketConnector.connect();
        socketConnector.registerObserver(this);
    }

    private void clickEnterOnWriteMessage() {
        textWriteMessage.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.ENTER){
                    socketConnector.sendMessage(textWriteMessage.getText());
                    textWriteMessage.clear();
                }
            }
        });
    }

    public void onMessage(String s) {
        textMessages.appendText(s);
    }
}
