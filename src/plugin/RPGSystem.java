package plugin;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.economy.Economy;
import plugin.commands.CommandLvl;
import plugin.commands.CommandRpg;
import plugin.listeners.AbilityCastListener;
import plugin.listeners.EventListener;
import plugin.player.PlayerData;
import plugin.requiredExpansions.PAPIExpansion;
import plugin.tasks.Tasks;
import plugin.utilities.Configurator;

public class RPGSystem extends JavaPlugin {
	private FileConfiguration config;
	private boolean enable;
	public HashMap<String, PlayerData> playerDataMap;
	private static Economy econ = null;
	public int addExpInSec = 1;
	
	public RPGSystem() {
		return;
	}
	
	public void checkConfig() {
		config = getConfig();
		enable = Configurator.getBoolean(config, "enable", true);
		addExpInSec = Configurator.getInt(config, "add-mana-in-sec", 1);
		saveConfig();
		return;
	}
	
	private void printHelloInConsole() {
		ConsoleCommandSender console = getServer().getConsoleSender();
		console.sendMessage("===========================");
		console.sendMessage("|   |     ___   ___        ");
		console.sendMessage("|   |\\   /|  |  |     \\  / ");
		console.sendMessage("|===| \\ / |__|  |__    \\/  ");
		console.sendMessage("|   |  |  |     |      /\\  ");
		console.sendMessage("|   |  |  |     |__   /  \\ ");
		console.sendMessage("===========================");
		return;
	}
	
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
    
    public Economy getEconomy() {
        return econ;
    }
    
	@Override
	public void onEnable() {
		checkConfig();
		if (!enable) {
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
        if (!setupEconomy() ) {
        	ConsoleCommandSender console = getServer().getConsoleSender();
    		console.sendMessage("[RPG_System] Disabled due to no Vault dependency found!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        if(getServer().getPluginManager().getPlugin("PlaceholderAPI")==null) {
        	ConsoleCommandSender console = getServer().getConsoleSender();
    		console.sendMessage("[RPG_System] Disabled due to no PAPI dependency found!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        new PAPIExpansion(this).register();
        getLogger().log(Level.INFO, "Был зарегестирован PAPIExpansion");
		playerDataMap = new HashMap<String, PlayerData>();
		Tasks.addSchedule(this);
		getServer().getPluginManager().registerEvents(new EventListener(this), this);
		getServer().getPluginManager().registerEvents(new AbilityCastListener(this), this);
		//readAbilityConfig();
		this.getCommand("rpg").setExecutor(new CommandRpg(this));
		this.getCommand("lvl").setExecutor(new CommandLvl(this));
		printHelloInConsole();
		return;
	}
	
	
}
