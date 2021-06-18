package world.ddpn.bungeecordlinkersportal.Listeners;

import com.google.gson.Gson;

import org.bukkit.Effect;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import world.ddpn.bungeecordlinkersportal.BungeeCordLinkersPortal;
import world.ddpn.bungeecordlinkersportal.Objects.ConnectionData;
import world.ddpn.bungeecordlinkersportal.Objects.TargetData;
import world.ddpn.bungeecordlinkersportal.Utils.net.SendSocket;
import world.ddpn.bungeecordlinkersportal.portal.Portal;
import world.ddpn.bungeecordlinkersportal.portal.PortalManager;

public class PlayerMoveListener implements Listener{
    
    private BungeeCordLinkersPortal plugin;

    public PlayerMoveListener(BungeeCordLinkersPortal plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockFromTo(PlayerMoveEvent event){
        if (event.getFrom().getBlockX() != event.getTo().getBlockX() || event.getFrom().getBlockY() != event.getTo().getBlockY()
                || event.getFrom().getBlockZ() != event.getTo().getBlockZ())
            return;


        PortalManager manager = plugin.getPortalManager();

        if(!manager.atPortal(event.getTo().getBlock()))
            return;


        Player player = event.getPlayer();
        player.setVelocity(player.getLocation().getDirection().setY(player.getLocation().getDirection().getY() / 2).multiply(-1));
        player.spawnParticle(
            Particle.CLOUD,
            player.getLocation(),
            250,
            1.5,
            1.5,
            1.5,
            0.05);
        player.playSound(player.getLocation(), Sound.ENTITY_GHAST_SHOOT, 1, 1);
        
        Gson gson = new Gson();

        Portal portal = manager.getAtPortal(event.getTo().getBlock());

        if(portal.getTarget() == null || portal.getParent() == null)
            return;

        TargetData targetData = new TargetData(event.getPlayer().getName(), portal.getTarget());
        ConnectionData connectionData = new ConnectionData("sendPlayer", gson.toJson(targetData));

        
        FileConfiguration config = plugin.getConfig();

        ConfigurationSection parentData = config.getConfigurationSection("parents."+portal.getParent());
        

        SendSocket.Send(parentData.getString("host"), parentData.getInt("port"),
                gson.toJson(connectionData),plugin);

        
    }
}
