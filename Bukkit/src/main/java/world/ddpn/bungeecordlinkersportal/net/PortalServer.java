package world.ddpn.bungeecordlinkersportal.net;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

import world.ddpn.bungeecordlinkersportal.BungeeCordLinkersPortal;

public class PortalServer {

    private BungeeCordLinkersPortal plugin;
    private ServerSocket server;

    public PortalServer(BungeeCordLinkersPortal plugin) throws IOException {
        this.plugin = plugin;
        server = new ServerSocket(plugin.getConfig().getInt("port"));
        main();
    }

    public void main() throws IOException {
        Socket socket = server.accept();

        byte[] data = new byte[1024];

        InputStream input = socket.getInputStream();

        int readSize = input.read(data);

        data = Arrays.copyOf(data, readSize);

        System.out.println("\"" + new String(data, "UTF-8") + "\"を受信しました。");

        input.close();
    }
    
    public void close() throws IOException{
        server.close();
    }

}
