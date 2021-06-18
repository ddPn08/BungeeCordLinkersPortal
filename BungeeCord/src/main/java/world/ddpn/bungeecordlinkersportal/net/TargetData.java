package world.ddpn.bungeecordlinkersportal.net;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TargetData {

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("Target")
    @Expose
    private String Target;

    public String getName(){
        return this.username;
    }
    
    public String getTarget(){
        return this.Target;
    }
}
