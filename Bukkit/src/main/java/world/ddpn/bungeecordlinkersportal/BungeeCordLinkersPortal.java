package world.ddpn.bungeecordlinkersportal;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import world.ddpn.bungeecordlinkersportal.Listeners.RegisterListeners;
import world.ddpn.bungeecordlinkersportal.Utils.FileUtil;
import world.ddpn.bungeecordlinkersportal.portal.PortalManager;
import world.ddpn.bungeecordlinkersportal.commands.LinkersPortal;

public final class BungeeCordLinkersPortal extends JavaPlugin {

    private Boolean isSelecting = false;
    private PortalManager portalManager;

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

    @Override
    public void onDisable(){
        
    }

    public Boolean isSelecting(){
        return this.isSelecting;
    }

    public void setSelecting(Boolean bool){
        this.isSelecting = bool;
    }

    public PortalManager getPortalManager(){
        return this.portalManager;
    }
}
