package world.ddpn.bungeecordlinkersportal.Objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConnectionData{

    public ConnectionData(String method, String data){
        this.method = method;
        this.data = data;
    }

    @SerializedName("method")
    @Expose
    private String method;

    @SerializedName("data")
    @Expose
    private String data;

    public String getMethod() {
        return this.method;
    }

    public String getData() {
        return this.data;
    }
}
