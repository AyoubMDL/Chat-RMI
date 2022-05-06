package com.example.rmi;

import client.IClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import server.IServer;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.util.Objects;

public class LoginController {
    @FXML
    private TextField _pseudo;
    @FXML
    private TextField _url;


    public void login(ActionEvent event) {
        if (_pseudo.getText() == null || _pseudo.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No pseudo");
            alert.setHeaderText("Please give your pseudo !");
            alert.showAndWait();
            return;
        }

        if (_url.getText() == null || _url.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No url");
            alert.setHeaderText("Please give a url !");
            alert.showAndWait();
            return;
        }


        try {
            IServer _chatServer = (IServer) Naming.lookup("chat");
            IClient _chatClient = ClientFactory.createClient(_chatServer, _pseudo.getText(), _url.getText());
            Naming.rebind(_url.getText(), _chatClient);
            Parent chatSpace = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("chat.fxml")));
            Scene chatScene = new Scene(chatSpace);
            Stage chatStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            chatStage.setScene(chatScene);
        } catch (NotBoundException | IOException e) {
            e.printStackTrace();
        }
    }
}
