package server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    public static void main (String[] args) {
        try{
            LocateRegistry.createRegistry(1099);
        } catch (RemoteException e) {
            System.err.println(e.getMessage());
        }
        if(System.getSecurityManager() == null) {
            String policy = "/home/ayoub/IdeaProjects/tp1_techno/RMI/src/main/java/security.java.policy";
            System.setProperty("java.security.policy", policy);
            System.setSecurityManager(new SecurityManager());
        }
        try {
            ChatServer chat = new ChatServer() ;
            Hour hour = new Hour();
            Naming.rebind ("chat", chat) ;
            Naming.rebind("hour", hour);
            System.out.println ("Server chat is ready") ;
        } catch (RemoteException | MalformedURLException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
