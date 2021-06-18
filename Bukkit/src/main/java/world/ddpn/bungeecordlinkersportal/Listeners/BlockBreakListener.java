package world.ddpn.bungeecordlinkersportal.Listeners;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import world.ddpn.bungeecordlinkersportal.BungeeCordLinkersPortal;
import world.ddpn.bungeecordlinkersportal.Utils.MessageUtil;
import world.ddpn.bungeecordlinkersportal.portal.PortalManager;

public class BlockBreakListener implements Listener{
    
    private BungeeCordLinkersPortal plugin;
    
    public BlockBreakListener(BungeeCordLinkersPortal plugin){
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockBreak(BlockBreakEvent event){
        if (!plugin.isSelecting())
            return;
        event.setCancelled(true);
        PortalManager manager = plugin.getPortalManager();

        Block block = event.getBlock();
        manager.setPos1(block);
        event.getPlayer().sendMessage(MessageUtil.info("pos1を X:" + String.valueOf(block.getX())  
            + ", Y:" +  String.valueOf(block.getY()) 
            + " ,Z:" +  String.valueOf(block.getZ()) 
            + "に設定しました。"));
    }
}
