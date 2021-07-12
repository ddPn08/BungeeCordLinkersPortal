package world.ddpn.bungeecordlinkersportal;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.plugin.java.JavaPlugin;

import world.ddpn.bungeecordlinkersportal.Listeners.RegisterListeners;
import world.ddpn.bungeecordlinkersportal.Objects.CreateSession;
import world.ddpn.bungeecordlinkersportal.Utils.FileUtil;
import world.ddpn.bungeecordlinkersportal.portal.PortalManager;
import world.ddpn.bungeecordlinkersportal.commands.LinkersPortal;

public final class BungeeCordLinkersPortal extends JavaPlugin {
    
    private PortalManager portalManager;
    private List<CreateSession> createSessions = new ArrayList<>();

    @Override
    public void onEnable() {

        saveDefaultConfig();

        File portals = new File(this.getDataFolder(),"Portals.json");
        if(!portals.exists()){
            try {
                FileUtil.writeFile(portals,"[]");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        

        RegisterListeners.register(this);

        getServer().getPluginCommand("LinkersPortal").setExecutor(new LinkersPortal(this));

        portalManager = new PortalManager(this);
    }

    // @Override
    // public void onDisable(){
        
    // }

    // public Boolean isSelecting(){
    //     return this.isSelecting;
    // }

    // public void setSelecting(Boolean bool){
    //     this.isSelecting = bool;
    // }

    public CreateSession getSettion(String username){
        return createSessions.stream().filter((v)->v.getUsername().equals(username)).findFirst().orElse(null);
    }

    public void addCreateSession(String username){
        this.createSessions.add(new CreateSession(username));
    }

    public void deleteSession(CreateSession session){
        this.createSessions.remove(session);
    }

    public PortalManager getPortalManager(){
        return this.portalManager;
    }
}
