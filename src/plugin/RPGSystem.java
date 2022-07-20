package plugin;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.economy.Economy;
import plugin.abilities.AbilityConfigCreator;
import plugin.abilities.EyeExplosion;
import plugin.abilities.Repulsion;
import plugin.abilities.Stun;
import plugin.commands.CommandLvl;
import plugin.commands.CommandRpg;
import plugin.commands.CommandTest;
import plugin.listeners.AbilityCastListener;
import plugin.listeners.OtherEventListener;
import plugin.player.PlayerData;
import plugin.pluginsExpansions.PAPIExpansion;
import plugin.tasks.RepeatingTasks;
import plugin.utilities.Configurator;



public class RPGSystem extends JavaPlugin {
	private final static String pluginName = "RPG_System";
	private FileConfiguration config;
	private boolean enable;
	private HashMap<String, PlayerData> playerDataMap;
	private static Economy econ = null;
	public final int addExpInSec = 1;
	
	public static String getPluginName() {
		return pluginName;
	}
	
	public RPGSystem() {
		return;
	}
	
	public HashMap<String, PlayerData> getPlayerDataMap() {
		return playerDataMap;
	}
	
	public void checkConfig() {
		config = getConfig();
		enable = Configurator.getBoolean(config, "enable", true);
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
    
    public void generateStaticFieldForAbilities() {
    	EyeExplosion.staticUpdate();
    	Repulsion.staticUpdate();
    	Stun.staticUpdate();
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
		RepeatingTasks.addSchedule(this);
		getServer().getPluginManager().registerEvents(new OtherEventListener(this), this);
		getServer().getPluginManager().registerEvents(new AbilityCastListener(this), this);
		generateStaticFieldForAbilities();
		AbilityConfigCreator.createAllConfig(this);
		this.getCommand("rpg").setExecutor(new CommandRpg(this));
		this.getCommand("lvl").setExecutor(new CommandLvl(this));
		this.getCommand("test").setExecutor(new CommandTest(this));
		printHelloInConsole();
		return;
	}
	
	
}
