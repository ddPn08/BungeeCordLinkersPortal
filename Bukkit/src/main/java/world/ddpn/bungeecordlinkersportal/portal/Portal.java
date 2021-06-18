package world.ddpn.bungeecordlinkersportal.portal;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import world.ddpn.bungeecordlinkersportal.BungeeCordLinkersPortal;

public class Portal {

    public transient BungeeCordLinkersPortal plugin;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("Parent")
    @Expose
    private String Parent = null;

    @SerializedName("Target")
    @Expose
    private String Target = null;

    @SerializedName("pos1")
    @Expose
    private location pos1;

    @SerializedName("pos2")
    @Expose
    private location pos2;

    private transient List<Block> blocks = new ArrayList<>();


    class location{
        public location(Integer X, Integer Y, Integer Z, String world){
            this.X = X;
            this.Y = Y;
            this.Z = Z;
            this.world = world;
        }

        @SerializedName("world")
        @Expose
        public String world;

        @SerializedName("X")
        @Expose
        public Integer X;

        @SerializedName("Y")
        @Expose
        public Integer Y;

        @SerializedName("Z")
        @Expose
        public Integer Z;
    }

    public Portal(Block pos1, Block pos2, String name, BungeeCordLinkersPortal plugin){
        this.pos1 = new location(pos1.getX(),pos1.getY(),pos1.getZ(),pos1.getWorld().getName());
        this.pos2 = new location(pos2.getX(), pos2.getY(), pos2.getZ(), pos2.getWorld().getName());
        this.name = name;
        this.plugin = plugin;

        setRegion();
    }

    public void setPlugin(BungeeCordLinkersPortal plugin){
        this.plugin = plugin;
    }

    public void setRegion(){
        blocks = this.getRegion();
    }

    public String getName(){
        return this.name;
    }

    public Location getPos1(){
        return new Location(this.plugin.getServer().getWorld(this.pos1.world), this.pos1.X, this.pos1.Y, this.pos1.Z);
    }

    public Location getPos2() {
        return new Location(this.plugin.getServer().getWorld(this.pos2.world), this.pos2.X, this.pos2.Y, this.pos2.Z);
    }

    public void setTarget(String parent, String server){
        this.Parent = parent;
        this.Target = server;
    }

    public String getTarget(){
        return this.Target;
    }

    public String getParent(){
        return this.Parent;
    }

    public List<Block> getBlocks(){
        return this.blocks;
    }

    public void setBlock(Material material){
        for(Block block : this.blocks){
            block.setType(material,false);
        }
    }


    public Boolean onPortal(Block block){

        if(this.pos1 == null || pos2 == null)
            return false;

        Boolean X = this.pos1.X <= block.getX() && block.getX() <= this.pos2.X
                    || this.pos1.X >= block.getX() && block.getX() >= this.pos2.X;

        Boolean Y = this.pos1.Y <= block.getY() && block.getY() <= this.pos2.Y
                || this.pos1.Y >= block.getY() && block.getY() >= this.pos2.Y;

        Boolean Z = this.pos1.Z <= block.getZ() && block.getZ() <= this.pos2.Z
                || this.pos1.Z >= block.getZ() && block.getZ() >= this.pos2.Z;

        return X && Y && Z;
    }

    public List<Block> getRegion(){
        List<Block> blocks = new ArrayList<>();
        World world = plugin.getServer().getWorld(pos1.world);
        int x1 = pos1.X, y1 = pos1.Y, z1 = pos1.Z;
        int x2 = pos2.X, y2 = pos2.Y, z2 = pos2.Z;
        int lowestX = Math.min(x1, x2);
        int lowestY = Math.min(y1, y2);
        int lowestZ = Math.min(z1, z2);
        int highestX = Math.max(x1, x2);
        int highestY = Math.max(y1, y2);
        int highestZ = Math.max(z1, z2);

        int Xpass = 0;
        int Ypass = 0;
        int Zpass = 0;

        for (int x = lowestX; x <= highestX; x++){
            Xpass ++;
            for (int y = lowestY; y <= highestY; y++){
                Ypass ++;
                for (int z = lowestZ; z <= highestZ; z++){
                    Zpass ++;
                    blocks.add(world.getBlockAt(x, y, z));
                }    
            }
        }

        return blocks;
    }
}
