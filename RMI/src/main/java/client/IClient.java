package client;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IClient extends Remote {
    /**
     * @param m received Message from the server
     */
    void diffuseMessage(Message m) throws RemoteException;

    void removeListener(Observer listener) throws RemoteException;

    void sendMessage(Message msg) throws RemoteException;

    String getPseudo() throws RemoteException;

    String getUrl() throws RemoteException;

    Message next() throws RemoteException;

    void addListener(Observer listener) throws RemoteException;

    void leaveChat(String pseudo) throws RemoteException;

    List<String> nextOne() throws RemoteException;

    void joinChat(String pseudo, String url) throws RemoteException;

    void processConnectedClients(String pseudo) throws RemoteException;


}
