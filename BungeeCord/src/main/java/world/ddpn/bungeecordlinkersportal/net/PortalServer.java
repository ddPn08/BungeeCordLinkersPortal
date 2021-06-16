package world.ddpn.bungeecordlinkersportal.net;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

import net.md_5.bungee.api.chat.TextComponent;
import world.ddpn.bungeecordlinkersportal.BungeeCordLinkersPortal;

public class PortalServer {
    
    private BungeeCordLinkersPortal plugin;
    private Runnable serverTask;

    public PortalServer(BungeeCordLinkersPortal plugin){
        this.plugin = plugin;

        serverTask = new Runnable(){
        @Override
        public void run(){
            while(plugin.isEnabled()){
                try {
                    ServerSocket ss = new ServerSocket(25566);
                    Socket socket = ss.accept();

                    byte[] data = new byte[1024];
                    InputStream input = socket.getInputStream();
                    int readSize = input.read(data);
                    data = Arrays.copyOf(data, readSize);

                    plugin.getLogger().info(new String(data, "UTF-8"));

                    input.close();
                    ss.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
            }
            main();
        }};

        main();
    }

    public void main(){
        plugin.getProxy().getScheduler().runAsync(plugin,serverTask);
    }
}
