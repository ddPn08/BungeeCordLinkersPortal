package world.ddpn.bungeecordlinkersportal;

import net.md_5.bungee.api.plugin.Plugin;
import world.ddpn.bungeecordlinkersportal.net.PortalServer;

public final class BungeeCordLinkersPortal extends Plugin {

    private PortalServer server;
    private Boolean enabled;

    @Override
    public void onEnable() {
        enabled = true;
        server = new PortalServer(this);
    }

    @Override
    public void onDisable() {
        enabled = false;
    }

    public Boolean isEnabled(){
        return enabled;
    }
}
