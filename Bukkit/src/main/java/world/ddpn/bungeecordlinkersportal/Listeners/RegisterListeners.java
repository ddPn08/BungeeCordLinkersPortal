package world.ddpn.bungeecordlinkersportal.Listeners;

import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

import world.ddpn.bungeecordlinkersportal.BungeeCordLinkersPortal;

public class RegisterListeners {
    
    public static void register(BungeeCordLinkersPortal plugin){
        Listener[] listeners = new Listener[]{
            new BlockBreakListener(plugin),
            new PlayerInteractListener(plugin),
            new PlayerMoveListener(plugin)
        };

        PluginManager manager = plugin.getServer().getPluginManager();

        for(Listener listener : listeners)
            manager.registerEvents(listener, plugin);
    }
}
