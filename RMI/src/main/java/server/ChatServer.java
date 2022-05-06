package server;


import client.IClient;
import client.Message;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;


public class ChatServer extends UnicastRemoteObject implements IServer {
    private final HashMap<String, String> _clients;
    private final List<String> _pseudos = new ArrayList<>();


    protected ChatServer() throws RemoteException {
        _clients = new HashMap<>();
    }

    @Override
    public void connect(String pseudo, String url) throws RemoteException {
        this.broadcastMessage(new Message("[Server]", pseudo + " logged on"));
        _pseudos.add(pseudo);
        _clients.put(pseudo, url);
        browseClients(pseudo);
    }


    @Override
    public void disconnect(String pseudo) throws RemoteException {
        this.broadcastMessage(new Message("[Server]", pseudo + " logged out"));
        _pseudos.remove(pseudo);
        browseClients(pseudo);
        _clients.remove(pseudo);
    }

    private void browseClients(String pseudo) throws RemoteException {
        for (Map.Entry<String, String> stringStringEntry : _clients.entrySet()) {
            try {
                IClient chatClient = (IClient) Naming.lookup(((Map.Entry<?, ?>) stringStringEntry).getValue().toString());
                chatClient.processConnectedClients(pseudo);
            } catch (NotBoundException | MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void broadcastMessage(Message msg) throws RemoteException {
        for (Map.Entry<String, String> stringStringEntry : _clients.entrySet()) {
            try {
                IClient chatClient = (IClient) Naming.lookup(((Map.Entry<?, ?>) stringStringEntry).getValue().toString());
                chatClient.diffuseMessage(msg);
            } catch (NotBoundException | MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<String> getPseudos() throws RemoteException {
        return _pseudos;
    }


}
