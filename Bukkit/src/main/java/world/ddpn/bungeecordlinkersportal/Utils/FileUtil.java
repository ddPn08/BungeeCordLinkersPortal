package world.ddpn.bungeecordlinkersportal.Utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtil {
    public static String fileToString(String filePath){
        Path fileName = Path.of(filePath);

        try {
            return Files.readString(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void writeFile(File file, String string) throws IOException {
        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
        BufferedWriter writer = new BufferedWriter(osw);
        writer.write(string);
        writer.close();
    }
}
