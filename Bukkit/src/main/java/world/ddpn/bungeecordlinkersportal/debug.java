package world.ddpn.bungeecordlinkersportal;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;

public class debug {
    public static void main(String args[]){
        String data = "{\"method\":\"sendPlayer\",\"data\":\"{\"username\":\"ddPn08\",\"Target\":\"lobby\"}\"}";

        try (Socket socket = new Socket("localhost", 25566);
                OutputStream outputStream = socket.getOutputStream();
                InputStream inputStream = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));) {
            outputStream.write(data.getBytes("UTF-8"));

            System.out.println("ok");

            while (true) {
                System.out.println("ok");
                String input = keyboard.readLine();
                if (input.equals("¥¥q")) {
                    break;
                }
                System.out.println("［Server］" + reader.readLine());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
