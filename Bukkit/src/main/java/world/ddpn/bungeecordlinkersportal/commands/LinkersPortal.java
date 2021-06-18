package world.ddpn.bungeecordlinkersportal.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import net.md_5.bungee.api.ChatColor;
import world.ddpn.bungeecordlinkersportal.BungeeCordLinkersPortal;
import world.ddpn.bungeecordlinkersportal.Utils.MessageUtil;
import world.ddpn.bungeecordlinkersportal.portal.Portal;
import world.ddpn.bungeecordlinkersportal.portal.PortalManager;

public class LinkersPortal implements TabExecutor{

    private BungeeCordLinkersPortal plugin;

    public LinkersPortal(BungeeCordLinkersPortal plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1){
            sender.sendMessage(ChatColor.GOLD + "[Description]"
                    + ChatColor.AQUA + "\nTitle : "+ ChatColor.LIGHT_PURPLE + plugin.getDescription().getName()
                    + ChatColor.AQUA + "\nVersion : " + ChatColor.LIGHT_PURPLE + plugin.getDescription().getVersion());
            return false;
        }

        PortalManager manager = plugin.getPortalManager();
        switch (args[0]) {
            case "select": {
                if (plugin.isSelecting()) {
                    plugin.getPortalManager().cancel();
                    sender.sendMessage(MessageUtil.info("セレクトモードがオフになりました。"));
                    break;
                }

                plugin.setSelecting(true);
                sender.sendMessage(MessageUtil.info("セレクトモードがオンになりました。"));

                break;
            }

            case "create":{
                if(!plugin.isSelecting()){
                    sender.sendMessage(MessageUtil.error("範囲を選択してください。"));
                    break;
                }

                if(args.length < 2){
                    sender.sendMessage(MessageUtil.error("名前を入力してください。\nex) /linkersportal create <PortalName>"));
                    break;
                }

                if(manager.exist(args[1])){
                    sender.sendMessage(MessageUtil.error("すでに登録されています。"));
                    break;
                }

                manager.Create(args[1]);
                sender.sendMessage(MessageUtil.info("ポータル(" + args[1] + ") を作成しました。"));

                break;
            }
            case "delete":{
                if (args.length < 2) {
                    sender.sendMessage(MessageUtil.error("名前を入力してください。\nex) /linkersportal delete <PortalName>"));
                    break;
                }
                if (!manager.exist(args[1])) {
                    sender.sendMessage(MessageUtil.error("そのような名前のポータルは存在しません。"));
                    break;
                }

                manager.Delete(args[1]);
                sender.sendMessage(MessageUtil.info("削除しました。(" + args[1] + ")"));

                break;
            }

            case "fill":{
                if (args.length < 2) {
                    sender.sendMessage(MessageUtil.error("名前を入力してください。\nex) /linkersportal delete <PortalName>"));
                    break;
                }
                if (!manager.exist(args[1])) {
                    sender.sendMessage(MessageUtil.error("そのような名前のポータルは存在しません。"));
                    break;
                }
                if (args.length < 3) {
                    sender.sendMessage(MessageUtil.error("ブロックIDを入力してください。\nex) /linkersportal delete <PortalName>"));
                    break;
                }
                
                manager.fill(args[1],Material.valueOf(args[2]));
                sender.sendMessage(MessageUtil.info("ポータル("+ args[1] +") を" + args[2] + "で埋めました。"));

                break;
            }

            case "setTarget":{
                if (args.length < 2) {
                    sender.sendMessage(MessageUtil.error("名前を入力してください。\nex) /linkersportal delete <PortalName> <ParentServer> <ServerName>"));
                    break;
                }
                if (!manager.exist(args[1])) {
                    sender.sendMessage(MessageUtil.error("そのような名前のポータルは存在しません。"));
                    break;
                }
                if (args.length < 3) {
                    sender.sendMessage(MessageUtil.error("送信先の親のサーバー名を入力してください。\nex) /linkersportal setTarget <PortalName> <ParentServer> <ServerName>"));
                    break;
                }
                if (args.length < 4) {
                    sender.sendMessage(MessageUtil.error("送信先のサーバーを入力してください。\nex) /linkersportal delete <PortalName> <ParentServer> <ServerName>"));
                    break;
                }

                manager.setTarget(args[1], args[2], args[3]);
                sender.sendMessage(MessageUtil.info("ポータル(" + args[1] + ") の送信先を" + args[2] + " の " + args[3] + "に設定しました。"));
                
                break;
            }

            default:
                break;
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completes = new ArrayList<>();

        PortalManager manager = plugin.getPortalManager();
        if(args.length == 1){
            completes.add("select");
            completes.add("create");
            completes.add("fill");
            completes.add("delete");
            completes.add("setTarget");
        }


        if (args.length == 2) {
            completes.clear();
            if (args[0].equals("create")) {
                completes.add("<PortalName>");
            }
            if (args[0].matches("fill|delete|setTarget")) {
                for (Portal portal : manager.getPortalList())
                    completes.add(portal.getName());
            }
        }

        if(args.length == 3){
            if (args[0].equals("fill")) {
                completes.clear();
                completes.add("<Others...>");
                completes.add("WATER");
                completes.add("LAVA");
                completes.add("NETHER_PORTAL");
                completes.add("END_GATEWAY");
                completes.add("LEGACY_PORTAL");
                completes.add("GLASS_PANE");
                completes.add("IRON_BARS");
            }

            if(args[0].equals("setTarget")){
                completes.clear();
                FileConfiguration config = plugin.getConfig();
                ConfigurationSection parents = config.getConfigurationSection("parents");
                for(String key : parents.getKeys(false))
                    completes.add(key);
            }
        }

        return completes;
    }
}
