package world.ddpn.bungeecordlinkersportal.Listeners;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import world.ddpn.bungeecordlinkersportal.BungeeCordLinkersPortal;
import world.ddpn.bungeecordlinkersportal.Utils.MessageUtil;
import world.ddpn.bungeecordlinkersportal.portal.PortalManager;

public class PlayerInteractListener implements Listener{

    private BungeeCordLinkersPortal plugin;

    public PlayerInteractListener(BungeeCordLinkersPortal plugin){
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        if (!plugin.isSelecting() || !event.getHand().name().contains("OFF_HAND") )
            return;

        event.setCancelled(true);
        PortalManager manager = plugin.getPortalManager();

        Block block = event.getClickedBlock();
        manager.setPos2(block);
        event.getPlayer().sendMessage(MessageUtil.info("pos2を X:" + String.valueOf(block.getX()) + ", Y:"
                + String.valueOf(block.getY()) + " ,Z:" + String.valueOf(block.getZ()) + "に設定しました。"));
    }

}
