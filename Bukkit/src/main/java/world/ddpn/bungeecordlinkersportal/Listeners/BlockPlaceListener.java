package world.ddpn.bungeecordlinkersportal.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import world.ddpn.bungeecordlinkersportal.BungeeCordLinkersPortal;

public class BlockPlaceListener implements Listener {
    
    private BungeeCordLinkersPortal plugin;

    public BlockPlaceListener(BungeeCordLinkersPortal plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){
        if(!plugin.isSelecting())
            return;

        event.setCancelled(true);
    }
}
