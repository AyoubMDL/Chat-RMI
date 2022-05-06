package server;

import client.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IServer extends Remote {
    /**
     * Connexion to the server
     *
     * @param pseudo chosen pseudo
     * @param url    url to the distant object client
     */
    void connect(String pseudo, String url) throws RemoteException;

    /**
     * @param pseudo pseudo disconnecting
     */
    void disconnect(String pseudo) throws RemoteException;

    /**
     * @param msg Message sent to all active users
     */
    void broadcastMessage(Message msg) throws RemoteException;

    List<String> getPseudos() throws RemoteException;

}
