package world.ddpn.bungeecordlinkersportal.portal;

import java.io.*;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.bukkit.Material;
import org.bukkit.block.Block;

import world.ddpn.bungeecordlinkersportal.BungeeCordLinkersPortal;
import world.ddpn.bungeecordlinkersportal.Utils.FileUtil;

public class PortalManager {

    private BungeeCordLinkersPortal plugin;
    private Set<Portal> portalList;

    private Block pos1;
    private Block pos2;

    public PortalManager(BungeeCordLinkersPortal plugin){
        this.plugin = plugin;
        
        this.pos1 = null;
        this.pos2 = null;

        Gson gson = new GsonBuilder().create();

        File portalsFIle            = (new File(plugin.getDataFolder() , "Portals.json"));

        this.plugin.getLogger().info(FileUtil.fileToString(portalsFIle.getPath()));

        portalList = Arrays.stream(gson.fromJson(FileUtil.fileToString(portalsFIle.getPath()),Portal[].class))
                                           .collect(Collectors.toSet());

        for(Portal portal : portalList){
            portal.setPlugin(plugin);
            portal.setRegion();
        }   
    }

    public void setPos1 (Block pos1){
        this.pos1 = pos1;
    }

    public void setPos2(Block pos2) {
        this.pos2 = pos2;
    }

    public Boolean exist(String name){
        Portal portal = portalList.stream().filter(v -> v.getName().equals(name)).findFirst().orElse(null);
        if(portal == null)
            return false;

        return true;
    }

    public void Create(String name){
        Portal portal = new Portal(pos1, pos2, name, this.plugin);
        this.portalList.add(portal);

        this.pos1 = null;
        this.pos2 = null;

        plugin.setSelecting(false);

        try {
            this.updateFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Delete(String name){
        Portal portal = portalList.stream().filter(v -> v.getName().equals(name)).findFirst().orElse(null);

        if(portal == null)
            return;

        portalList.remove(portal);

        try {
            this.updateFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void fill(String name, Material material){
        Portal portal = portalList.stream().filter(v -> v.getName().equals(name)).findFirst().orElse(null);

        if(portal == null)
            return;

        portal.setBlock(material);
    }

    public void setTarget(String name, String parent, String target){
        Portal portal = portalList.stream().filter(v -> v.getName().equals(name)).findFirst().orElse(null);

        if (portal == null)
            return;

        portal.setTarget(parent, target);

        try {
            this.updateFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cancel(){
        this.pos1 = null;
        this.pos2 = null;

        plugin.setSelecting(false);
    }

    public Set<Portal> getPortalList(){
        return this.portalList;
    }

    private void updateFile() throws IOException{
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        File file = new File(plugin.getDataFolder(),"Portals.json");
        FileUtil.writeFile(file, gson.toJson(this.portalList));
    }

    public Boolean atPortal(Block block){
        for(Portal portal : portalList){
            if(portal.onPortal(block)){
                return true;
            }
        }
        return false;
    }
    
    public Portal getAtPortal(Block block){
        for (Portal portal : portalList) {
            if (portal.onPortal(block)) {
                return portal;
            }
        }
        return null;
    }
}
