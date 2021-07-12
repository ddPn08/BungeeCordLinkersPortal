package world.ddpn.bungeecordlinkersportal.net;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.Iterator;

import com.google.gson.Gson;

import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import world.ddpn.bungeecordlinkersportal.BungeeCordLinkersPortal;

public class PortalServer {
    
    private BungeeCordLinkersPortal plugin;
    private Configuration config;

    public PortalServer(BungeeCordLinkersPortal plugin) throws IOException{
        this.plugin = plugin;
    }

    public void enable() {
        plugin.getProxy().getScheduler().runAsync(plugin, new Runnable() {
            @Override
            public void run() {
                try (ServerSocketChannel ssc = ServerSocketChannel.open(); Selector selector = Selector.open();) {
                    config = ConfigurationProvider.getProvider(YamlConfiguration.class)
                            .load(new File(plugin.getDataFolder(), "config.yml"));


                    // ノンブロッキングモードにしてSelectorに受付チャネルを登録する
                    ssc.configureBlocking(false);
                    ssc.socket().bind(new InetSocketAddress(config.getInt("port")));
                    ssc.register(selector, SelectionKey.OP_ACCEPT);
                    plugin.getLogger().info("サーバが起動しました " + ssc.socket().getLocalSocketAddress());

                    // チャネルにイベントが登録されるまで待つ
                    while (selector.select() > 0) {
                        for (Iterator it = selector.selectedKeys().iterator(); it.hasNext();) {
                            SelectionKey key = (SelectionKey) it.next();
                            it.remove();

                            if (key.isAcceptable()) {
                                doAccept((ServerSocketChannel) key.channel(), selector);
                            } else if (key.isReadable()) {
                                doRead((SocketChannel) key.channel(), selector);
                            } else if (key.isWritable()) {
                                byte[] message = (byte[]) key.attachment();
                                doWrite((SocketChannel) key.channel(), selector, message);
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            
            private void doAccept(ServerSocketChannel ssc, Selector selector) {
                try {
                    SocketChannel channel = ssc.accept();
                    plugin.getLogger().info("connected " + channel.socket().getRemoteSocketAddress());
                    channel.configureBlocking(false);
                    channel.register(selector, SelectionKey.OP_READ);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            
            public void doRead(SocketChannel channel, Selector selector) {
                try {
                    ByteBuffer buffer = ByteBuffer.allocate(1024);

                    // ソケットから入力を読み込む
                    // コネクションが切れていればチャネルをクローズし、読み込めなければリターンする
                    int readBytes = channel.read(buffer);
                    if (readBytes == -1) {
                        plugin.getLogger().info("disconnected " + channel.socket().getRemoteSocketAddress());
                        channel.close();
                        return;
                    }
                    if (readBytes == 0) {
                        return;
                    }

                    // 入力されたメッセージを取り出し、チャネルに登録する
                    buffer.flip();
                    byte[] bytes = new byte[buffer.limit()];
                    buffer.get(bytes);

                    String line = new String(buffer.array(), "UTF-8").replaceAll(System.getProperty("line.separator"),
                            "");
                    plugin.getLogger().info("recieved " + line + " from " + channel.socket().getRemoteSocketAddress());

                    plugin.getLogger().info("register " + channel.socket().getRemoteSocketAddress());
                    channel.register(selector, SelectionKey.OP_WRITE, bytes);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            
            public void doWrite(SocketChannel channel, Selector selector, byte[] message) {
                try {
                    ByteBuffer byteBuffer = ByteBuffer.wrap(message);
                    channel.write(byteBuffer);
                    ByteBuffer restByteBuffer = byteBuffer.slice();

                    // ログに送信したメッセージを表示する
                    byteBuffer.flip();
                    byte[] sendBytes = new byte[byteBuffer.limit()];
                    byteBuffer.get(sendBytes);
                    String line = new String(sendBytes, "UTF-8").replaceAll(System.getProperty("line.separator"), "");
                    plugin.getLogger().info("echo " + line + " to " + channel.socket().getRemoteSocketAddress());

                    // メッセージを最後まで出力したら入力を受け付ける
                    if (restByteBuffer.hasRemaining()) {
                        byte[] restBytes = new byte[restByteBuffer.limit()];
                        restByteBuffer.get(restBytes);
                        channel.register(selector, SelectionKey.OP_WRITE, restBytes);
                    } else {
                        channel.register(selector, SelectionKey.OP_READ);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
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
