package world.ddpn.bungeecordlinkersportal.Utils.net;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import world.ddpn.bungeecordlinkersportal.BungeeCordLinkersPortal;

public class SendSocket {
    public static void Send(String TargetIP,int TargetPort,String data,BungeeCordLinkersPortal plugin){
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, new Runnable(){
            @Override
            public void run(){
                System.out.println(TargetIP + ":" + String.valueOf(TargetPort) + "\n" + data);

                Socket socket = null;
                OutputStream outputStream = null;
                InputStream inputStream  = null;
                try {
                    socket = new Socket(TargetIP, TargetPort);
                    outputStream = socket.getOutputStream();
                    inputStream = socket.getInputStream();

                    outputStream.write(data.getBytes("UTF-8"));
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (socket != null)
                            socket.close();
                        if (outputStream != null)
                            outputStream.close();
                        if (inputStream != null)
                            inputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
