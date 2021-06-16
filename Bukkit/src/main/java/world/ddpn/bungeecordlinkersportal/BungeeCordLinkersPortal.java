package world.ddpn.bungeecordlinkersportal;

import java.io.IOException;

import org.bukkit.plugin.java.JavaPlugin;

import world.ddpn.bungeecordlinkersportal.net.PortalServer;
import world.ddpn.debug.server;

public final class BungeeCordLinkersPortal extends JavaPlugin {

    public PortalServer server;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        try {
            server = new PortalServer(this);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable(){
        try {
            server.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
