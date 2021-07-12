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
import world.ddpn.bungeecordlinkersportal.Objects.CreateSession;
import world.ddpn.bungeecordlinkersportal.Utils.FileUtil;

public class PortalManager {

    private BungeeCordLinkersPortal plugin;
    private Set<Portal> portalList;

    public PortalManager(BungeeCordLinkersPortal plugin){
        this.plugin = plugin;

        Gson gson = new GsonBuilder().create();

        File portalsFIle            = (new File(plugin.getDataFolder() , "Portals.json"));

        portalList = Arrays.stream(gson.fromJson(FileUtil.fileToString(portalsFIle.getPath()),Portal[].class))
                                           .collect(Collectors.toSet());

        for(Portal portal : portalList){
            portal.setPlugin(plugin);
            portal.setRegion();
        }   
    }

    public Boolean exist(String name){
        Portal portal = portalList.stream().filter(v -> v.getName().equals(name)).findFirst().orElse(null);
        if(portal == null)
            return false;

        return true;
    }

    public void Create(String name,CreateSession session){
        Portal portal = new Portal(session.getPos1(), session.getPos2(), name, this.plugin);
        this.portalList.add(portal);

        this.plugin.deleteSession(session);

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
