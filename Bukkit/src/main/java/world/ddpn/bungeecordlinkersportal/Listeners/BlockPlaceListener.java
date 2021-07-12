package world.ddpn.bungeecordlinkersportal.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import world.ddpn.bungeecordlinkersportal.BungeeCordLinkersPortal;
import world.ddpn.bungeecordlinkersportal.Objects.CreateSession;

public class BlockPlaceListener implements Listener {
    
    private BungeeCordLinkersPortal plugin;

    public BlockPlaceListener(BungeeCordLinkersPortal plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){
        CreateSession session = this.plugin.getSettion(event.getPlayer().getName());
        if (session == null)
            return;

        event.setCancelled(true);
    }
}
