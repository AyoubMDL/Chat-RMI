package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Hour extends UnicastRemoteObject implements IHour {
    protected Hour() throws RemoteException {
        super();
    }
    @Override
    public String getDate() throws RemoteException {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

        return format.format(new Date());
    }
}
