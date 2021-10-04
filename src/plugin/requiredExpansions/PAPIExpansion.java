package plugin.requiredExpansions;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import plugin.RPGSystem;
import plugin.utilities.Configurator;

public class PAPIExpansion extends PlaceholderExpansion {
    private final RPGSystem mainPlugin;
    private final String pathToConfig = "Settings/Placeholders.yml";
    private FileConfiguration config;
    
    public PAPIExpansion(RPGSystem mainPlugin) {
        this.mainPlugin = mainPlugin;
        this.config = Configurator.getCustomConfig(mainPlugin, pathToConfig);
        Configurator.getString(this.config, "lvl", "lvl");
        Configurator.saveCustomConfig(mainPlugin, pathToConfig, this.config);
        return;
    }
    
    @Override
    public String getAuthor() {
        return "Hypex";
    }
    
    @Override
    public String getIdentifier() {
        return "rpgsyst";
    }

    @Override
    public String getVersion() {
        return "0.0.1";
    }
    
    @Override
    public boolean persist() {
        return true; // This is required or else PlaceholderAPI will unregister the Expansion on reload
    }
    
    @Override
    public String onRequest(OfflinePlayer player, String params) {
        if(params.equalsIgnoreCase(config.getString("lvl"))){
            return String.valueOf(mainPlugin.getPlayerDataMap().get(player.getName()).getPlayerLevel());
        }   
        return null; // Placeholder is unknown by the Expansion
    }
    
}
