package world.ddpn.bungeecordlinkersportal.Utils;

import org.bukkit.ChatColor;

public class MessageUtil {

    public static String info(String message){
        return ChatColor.AQUA + "[BungeeCordLinkersPortal] " + message;
    }
    public static String error(String message){
        return ChatColor.RED + "[BungeeCordLinkersPortal] " + message;
    }
    
}
