package fr.station47.theme;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class Main extends JavaPlugin implements CommandExecutor {

    private static Theme theme;
    private File configFile;
    private YamlConfiguration config;

    public void onEnable(){
        configFile = new File(getDataFolder(), "config.yml");
        config = retrieveConfig();
        theme = new Theme(config);
    }

    public static Theme getTheme(){
        return theme;
    }

    public void onDisable(){}

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if (sender.hasPermission("station.theme.reload")){
            config = retrieveConfig();
            theme.load(config);
            sender.sendMessage(theme.getChatPrefix()+ ChatColor.GREEN+" le theme a été rechargé");
        } else {
            sender.sendMessage(theme.permissionDenied());
        }
        return true;
    }

    private YamlConfiguration retrieveConfig(){
        if (!configFile.exists()) {
            saveResource("config.yml", false);
        }

        YamlConfiguration config = new YamlConfiguration();
        try {
            config.load(configFile);
        } catch (InvalidConfigurationException ex){
            Bukkit.getServer().getPluginManager().disablePlugin(this);
            Bukkit.getLogger().severe("Erreur dans la configuration du fichier de config!");
            ex.printStackTrace();

        } catch (IOException ex){
            Bukkit.getServer().getPluginManager().disablePlugin(this);
            Bukkit.getLogger().severe("Erreur IOException!");
            ex.printStackTrace();
        }
        return config;
    }
}
