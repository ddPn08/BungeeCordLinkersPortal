package world.ddpn.debug;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class client {
    
    public static void main (String args[]){
        try {
            Socket sock = new Socket("localhost", 25566);

            OutputStream out = sock.getOutputStream();

            String sendData = "{\"username\":\"ddPn08\"}";

            out.write(sendData.getBytes("UTF-8"));

            System.out.println("sended data ;\"" + sendData + "\"");

            out.close();

            sock.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
