package world.ddpn.debug;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class server {

    public static void main(String args[]){
        try {
            ServerSocket ss = new ServerSocket(25566);

            Socket socket = ss.accept();

            byte[] data = new byte[1024];

            InputStream input = socket.getInputStream();

            int readSize = input.read(data);

            data = Arrays.copyOf(data, readSize);


            System.out.println("\"" + new String(data, "UTF-8") + "\"");

            input.close();

            ss.close();
    
        } catch (Exception e) {
            //TODO: handle exception
        }
    }
}
