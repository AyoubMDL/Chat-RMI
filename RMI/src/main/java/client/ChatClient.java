package client;


import server.IServer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;


public class ChatClient extends UnicastRemoteObject implements IClient {
    private final IServer _chatServer;
    private final String _pseudo;
    private final String _url;
    private final Queue<Message> _messages;
    private final ArrayList<Observer> _listeners;


    public ChatClient(IServer chatServer, String pseudo, String url) throws RemoteException {
        _chatServer = chatServer;
        _pseudo = pseudo;
        _url = url;
        _messages = new LinkedList<>();
        _listeners = new ArrayList<>();
    }


    @Override
    public void diffuseMessage(Message m) throws RemoteException {
        _messages.add(m);
        notifyListeners();
    }

    @Override
    public void removeListener(Observer listener) throws RemoteException {
        _listeners.remove(listener);
    }


    @Override
    public void sendMessage(Message msg) throws RemoteException {
        _chatServer.broadcastMessage(msg);
    }

    @Override
    public String getPseudo() throws RemoteException {
        return _pseudo;
    }

    @Override
    public String getUrl() throws RemoteException {
        return _url;
    }


    @Override
    public void addListener(Observer listener) throws RemoteException {
        if (listener != null) {
            _listeners.add(listener);
        }
    }


    private void notifyListeners() {
        for (Observer listener : _listeners) {
            listener.notifyClients();
        }
    }

    @Override
    public Message next() throws RemoteException {
        return _messages.poll();
    }

    @Override
    public void leaveChat(String pseudo) throws RemoteException {
        _chatServer.disconnect(pseudo);
    }


    @Override
    public List<String> nextOne() throws RemoteException {
        return _chatServer.getPseudos();
    }

    @Override
    public void joinChat(String pseudo, String url) throws RemoteException {
        _chatServer.connect(pseudo, url);
    }

    private void updateConnectedClients() throws RemoteException {
        for (Observer listener : _listeners) {
            listener.getConnectedClients();
        }
    }

    @Override
    public void processConnectedClients(String pseudo) throws RemoteException {
        this.updateConnectedClients();
    }


}
