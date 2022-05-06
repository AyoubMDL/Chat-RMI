package com.example.rmi;

import client.ChatClient;
import client.IClient;
import server.IServer;

import java.rmi.RemoteException;

public class ClientFactory {
    private static IClient participant;

    public static IClient getClient() {
        return participant;
    }

    public static IClient createClient(IServer chatServer, String pseudo, String url) throws RemoteException {
        participant = new ChatClient(chatServer, pseudo, url);
        return participant;
    }
}
