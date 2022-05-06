package client;

import java.io.Serializable;

public class Message implements Serializable {
    private final String msg ;
    private final String pseudo ;

    public Message(String pseudo,String msg)
    {
        this.pseudo = pseudo ; this.msg = msg ;
    }

    public String getMessage () { return msg ; }
    public String getPseudo () { return pseudo ; }
}
