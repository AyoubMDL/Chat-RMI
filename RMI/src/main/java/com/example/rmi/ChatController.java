package com.example.rmi;

import client.IClient;
import client.Message;
import client.Observer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import server.IHour;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import java.util.List;
import java.util.Objects;

public class ChatController implements Observer {
    private IClient _chatClient;

    @FXML
    private Label _pseudoChat;
    @FXML
    private Label _serverHour;

    @FXML
    private ListView<String> _listMessages;

    @FXML
    private TextField _message;

    @FXML
    private Label _clientHour;

    @FXML
    private ListView<String> _connectedClients;

    @FXML
    public void initialize() throws RemoteException {
        _chatClient = ClientFactory.getClient();
        updateHour();
        _pseudoChat.setText(_chatClient.getPseudo());
        try {
            _chatClient.addListener(this);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        _chatClient.joinChat(_chatClient.getPseudo(), _chatClient.getUrl());
    }

    public void updateHour() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), actionEvent -> {
            IHour date;
            try {
                date = (IHour) Naming.lookup("hour");
                _clientHour.setText(date.getDate());
                _serverHour.setText(date.getDate());
            } catch (NotBoundException | MalformedURLException | RemoteException e) {
                e.printStackTrace();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }


    public void sendButton() throws RemoteException {
        if (Objects.equals(_message.getText(), "")) {
            return;
        }
        Message msg = new Message(_chatClient.getPseudo(), _message.getText());
        _chatClient.sendMessage(msg);
        _message.clear();
    }


    public void logoutButton() throws RemoteException {
        _chatClient.leaveChat(_chatClient.getPseudo());
        _chatClient.removeListener(this);
        _connectedClients.getItems().remove(_chatClient.getPseudo());
        Platform.exit();
        System.exit(0);
    }


    @Override
    public void notifyClients() {
        Platform.runLater(() -> {
            Message msg = null;
            try {
                msg = ClientFactory.getClient().next();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            if (msg != null) {
                _listMessages.getItems().add(msg.getPseudo() + " : " + msg.getMessage());
            }
        });
    }

    @Override
    public void getConnectedClients() {
        Platform.runLater(() -> {
            try {
                List<String> connected = ClientFactory.getClient().nextOne();
                _connectedClients.getItems().clear();
                for(String c : connected) {
                    _connectedClients.getItems().add(c);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }
}
