package world.ddpn.bungeecordlinkersportal.Objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemInfo {
    @SerializedName("type")
    @Expose
    private int type;

    @SerializedName("meta")
    @Expose
    private int meta;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("text_type")
    @Expose
    private String text_type;

    public int getType(){
        return this.type;
    }
    
    public int getMeta(){
        return this.meta;
    }

    public String getName(){
        return this.name;
    }

    public String getTextType(){
        return this.text_type;
    }
}
