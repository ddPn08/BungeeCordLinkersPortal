package world.ddpn.bungeecordlinkersportal.net;

import java.io.Serializable;

public class ConnectionData implements Serializable{

    private String method;

    private String data;
    
    public String getMethod(){
        return this.method;
    }

    public String getData(){
        return this.data;
    }
}
