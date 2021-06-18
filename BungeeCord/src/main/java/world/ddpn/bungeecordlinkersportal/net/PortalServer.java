package world.ddpn.bungeecordlinkersportal.net;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

import com.google.gson.Gson;

import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import world.ddpn.bungeecordlinkersportal.BungeeCordLinkersPortal;

public class PortalServer {
    
    private BungeeCordLinkersPortal plugin;
    private ServerSocket ss;
    private Boolean enabled = false;
    private Configuration config;

    public PortalServer(BungeeCordLinkersPortal plugin) throws IOException{
        this.plugin = plugin;
        config = null;
        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class)
                    .load(new File(plugin.getDataFolder(), "config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (config == null)
            return;
        
        ss = new ServerSocket(config.getInt("port"));
    }

    public void enable() {
        enabled = true;
        plugin.getProxy().getScheduler().runAsync(plugin, new Runnable() {
            @Override
            public void run() {
                try {
                    while (enabled) {
                        Socket socket = ss.accept();

                        byte[] data = new byte[1024];
                        InputStream input = socket.getInputStream();
                        int readSize = input.read(data);
                        data = Arrays.copyOf(data, readSize);

                        Gson gson = new Gson();

                        methodRouter(gson.fromJson(new String(data, "UTF-8"), ConnectionData.class), socket);

                        input.close();
                        socket.close();
                    }
                    ss.close();
                } catch (IOException e) {
                    enabled = false;
                    plugin.getLogger().warning("ソケットサーバーでエラーが発生しました。");
                }
            }
        });
    }

    public void disable() {
        enabled = false;
    }

    public void methodRouter(ConnectionData data, Socket socket){

        PrintWriter writer = null;

        try {
            writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (writer == null)
            return;

        Gson gson = new Gson();


        switch (data.getMethod()) {
            case "sendPlayer": {
                TargetData targetData = gson.fromJson(data.getData(), TargetData.class);
                ProxiedPlayer player = plugin.getProxy().getPlayer(targetData.getName());

                if (player == null){
                    break;
                }
                
                ServerInfo server = plugin.getProxy().getServerInfo(targetData.getTarget());

                if(player.getServer().getInfo().equals(server)){
                    writer.println("{\"connected\":\"true\"}");
                    break;
                }

                player.connect(plugin.getProxy().getServerInfo(targetData.getTarget()));

                break;
            }

            case "getServers":{
                writer.println("{\"data\":" +gson.toJson(plugin.getProxy().getServers().keySet()) + "}");
                break;
            }

            default:
                break;
        }

        writer.close();
    }
}
