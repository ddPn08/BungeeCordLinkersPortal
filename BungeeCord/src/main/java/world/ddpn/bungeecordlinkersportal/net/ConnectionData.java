package world.ddpn.bungeecordlinkersportal.net;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConnectionData implements Serializable{

    @SerializedName("method")
    @Expose
    private String method;

    @SerializedName("data")
    @Expose
    private String data;
    
    public String getMethod(){
        return this.method;
    }

    public String getData(){
        return this.data;
    }
}
