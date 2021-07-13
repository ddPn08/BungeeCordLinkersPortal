package world.ddpn.bungeecordlinkersportal.Listener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import world.ddpn.bungeecordlinkersportal.BungeeCordLinkersPortal;

public class PluginMessageListener implements Listener{

    private BungeeCordLinkersPortal plugin;
    public PluginMessageListener(BungeeCordLinkersPortal plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onPluginMessage(PluginMessageEvent event){
        if(!event.getTag().equalsIgnoreCase("BungeeCordLinkersPortal"))
            return;

        ByteArrayDataInput in = ByteStreams.newDataInput( event.getData() );
        String subChannel = in.readUTF();

        if(subChannel.equalsIgnoreCase("test")){
            this.plugin.getLogger().info(in.readUTF());
        }
    }
}
