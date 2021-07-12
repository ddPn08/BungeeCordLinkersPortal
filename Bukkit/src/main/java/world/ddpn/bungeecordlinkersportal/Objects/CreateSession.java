package world.ddpn.bungeecordlinkersportal.Objects;

import org.bukkit.block.Block;

import world.ddpn.bungeecordlinkersportal.BungeeCordLinkersPortal;

public class CreateSession {

    private String username;
    private Boolean selecting;
    private Block block1;
    private Block block2;

    public CreateSession(String username){
        this.username = username;
        this.selecting = true;
        this.block1 = null;
        this.block2 = null;
    }

    public String getUsername(){
        return this.username;
    }

    public void setPos1(Block block){
        this.block1 = block;
    }
    
    public void setPos2(Block block){
        this.block2 = block;
    }

    public Block getPos1(){
        return this.block1;
    }

    public Block getPos2(){
        return this.block2;
    }

    public Boolean enough(){
        return this.block1 != null && this.block2 != null;
    }
    
}
