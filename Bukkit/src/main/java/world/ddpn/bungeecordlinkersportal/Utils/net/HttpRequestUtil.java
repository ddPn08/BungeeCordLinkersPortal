package world.ddpn.bungeecordlinkersportal.Utils.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequestUtil {
    public static String GET(String urlStr) throws IOException{
        URL url = new URL(urlStr);
        HttpURLConnection http = (HttpURLConnection)url.openConnection();
        http.setRequestMethod("GET");
        http.connect();
        BufferedReader reader = new BufferedReader(new InputStreamReader(http.getInputStream()));
        String result = "", line = "";
        while ((line = reader.readLine()) != null)
            result += line;
        reader.close();

        return result;
    }
}
