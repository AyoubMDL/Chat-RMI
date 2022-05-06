module com.example.rmi {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.rmi;
    requires java.logging;


    opens com.example.rmi to javafx.fxml;
    exports com.example.rmi;
    exports server;
    exports client;
}