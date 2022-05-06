package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IHour extends Remote {
    String getDate() throws RemoteException;
}