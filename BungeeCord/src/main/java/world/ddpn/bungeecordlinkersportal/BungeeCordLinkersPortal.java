package world.ddpn.bungeecordlinkersportal;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import net.md_5.bungee.api.plugin.Plugin;
import world.ddpn.bungeecordlinkersportal.net.PortalServer;

public final class BungeeCordLinkersPortal extends Plugin{

    private PortalServer server;

    @Override
    public void onEnable() {
        this.getProxy().registerChannel("BungeeCordLinkersPortal");

        if (!getDataFolder().exists())
            getDataFolder().mkdir();

        File file = new File(getDataFolder(), "config.yml");

        if (!file.exists()) {
            try (InputStream in = getResourceAsStream("config.yml")) {
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            server = new PortalServer(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        server.enable();
    }
}
