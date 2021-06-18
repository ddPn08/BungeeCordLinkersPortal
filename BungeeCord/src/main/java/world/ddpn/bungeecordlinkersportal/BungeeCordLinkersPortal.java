package world.ddpn.bungeecordlinkersportal;

import java.io.IOException;

import net.md_5.bungee.api.plugin.Plugin;
import world.ddpn.bungeecordlinkersportal.net.PortalServer;

public final class BungeeCordLinkersPortal extends Plugin {

    private PortalServer server;

    @Override
    public void onEnable() {
        try {
            server = new PortalServer(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        server.enable();
    }

    @Override
    public void onDisable() {
        server.disable();
    }
}
