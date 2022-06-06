package fr.station47.theme;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

/**
 * Created by micdu on 4/22/2017.
 */
public class Theme {
    private static Theme theme = null;
    private YamlConfiguration config;
    public static ChatColor mainColor;
    public static ChatColor accentColor;
    public static ChatColor thirdColor;
    public static String serverBrand;
    public static String serverName;
    public static String serverKind;
    public static String chatSeparator;
    public static String permissionDenied;

    public Theme(YamlConfiguration config){
        theme = this;
        load(config);
    }

    public String getHeader(){return this.getServerBrand()+getChatSeparator()+ ChatColor.RESET;}

    public ChatColor getMainColor() {
        return mainColor;
    }

    public ChatColor getAccentColor() {
        return accentColor;
    }

    public String getServerName() {
        return serverName;
    }

    public String getChatSeparator() {
        return chatSeparator;
    }

    public String getServerBrand() {
        return serverBrand;
    }

    public String getServerKind() {
        return serverKind;
    }

    public String getChatPrefix() {return serverBrand+chatSeparator;}

    public String permissionDenied() {return getChatPrefix()+permissionDenied;}

    public String getPermissionDeniedRaw(){return permissionDenied;}

    public static String parse(String s){
        return ChatColor.translateAlternateColorCodes('&',s.replaceAll("%theme_server_brand%", getTheme().getServerBrand())
                .replaceAll("%theme_server_prefix%",getTheme().getChatPrefix()+ChatColor.RESET)
                .replaceAll("%theme_main_color%", getTheme().getMainColor()+""+ChatColor.RESET)
                .replaceAll("%theme_accent_color%",getTheme().getAccentColor()+""+ChatColor.RESET)
                .replaceAll("%theme_permission_denied%",getTheme().getPermissionDeniedRaw()+ChatColor.RESET)
                .replaceAll("%theme_server_kind%", getTheme().getServerKind()+ChatColor.RESET)
                .replaceAll("%theme_server_name%",getTheme().getServerName()+ChatColor.RESET));

    }

    public void load(YamlConfiguration config){
        mainColor = ChatColor.valueOf(config.getString("color.main"));
        accentColor = ChatColor.valueOf(config.getString("color.accent"));
        thirdColor = ChatColor.valueOf(config.getString("color.third"));
        serverBrand = ChatColor.translateAlternateColorCodes('&',config.getString("serverBrand"));
        serverName = ChatColor.translateAlternateColorCodes('&',config.getString("serverName"));
        serverKind = ChatColor.translateAlternateColorCodes('&',config.getString("serverKind"));
        chatSeparator = ChatColor.translateAlternateColorCodes('&',config.getString("style.chatSeparator"));
        permissionDenied = ChatColor.translateAlternateColorCodes('&',config.getString("permissionDenied"));
    }

    public static Theme getTheme(){
        return theme;
    }

    public static void broadcast(String message){
        Bukkit.broadcastMessage(Theme.getTheme().getChatPrefix()+Theme.parse(message));
    }

    public static void sendMessage(CommandSender player, String message){
        player.sendMessage(getTheme().getChatPrefix()+parse(message));
    }

    public static void sendErrorMessage(CommandSender sender, String message){
        sendMessage(sender,ChatColor.RED+message);
    }
}
