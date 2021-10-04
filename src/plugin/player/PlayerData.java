package plugin.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import plugin.RPGSystem;
import plugin.abilities.Abilities;
import plugin.abilities.Ability;
import plugin.utilities.Configurator;

public class PlayerData {
	private RPGSystem mainPlugin;
	private final String pathToFolder = "Player_Data";
	private UUID uuid;
	private String pathToFile;
	private HashMap<String, Ability> abilitiesMap;
	private String playerName;
	private int playerLevel;
	
	public void setPlayerLevel(int value) {
		this.playerLevel = value;
		return;
	}
	
	public int getPlayerLevel() {
		return playerLevel;
	}
	
	public PlayerData(RPGSystem mainPlugin, Player player){
		this.mainPlugin = mainPlugin;
		this.uuid = player.getUniqueId();
		this.playerName = player.getName();
		pathToFile = pathToFolder + "/" + uuid.hashCode();
		abilitiesMap = new HashMap<String, Ability>();
		checkConfig();
		return;
	}
	
	private void checkConfig() {
		FileConfiguration config = Configurator.getCustomConfig(mainPlugin, pathToFile);
		playerLevel = Configurator.getInt(config, "Player.level", 1);
		List<String> possibleAbilitiesStr = Configurator.getStringList(config, "AvailableAbilities");
		for (int i=0; i<possibleAbilitiesStr.size(); i++) {
			Ability ability;
			ability = Abilities.createAbility(possibleAbilitiesStr.get(i), mainPlugin, 1);
			if (ability == null) {
				mainPlugin.getLogger().log(Level.WARNING, 
						"ќбнаружена недействительна€ способность "+possibleAbilitiesStr.get(i)
						+" у игрока "+playerName);
				continue;
				}
			String abilityName = ability.getType().toString();
			if (!abilitiesMap.containsKey(abilityName)) {
				mainPlugin.getLogger().log(Level.INFO, 
						"«арегистрирована способность "+abilityName+" у игрока "+playerName);
				abilitiesMap.put(abilityName, ability);
			} else {
				mainPlugin.getLogger().log(Level.WARNING,
						"”же зарегистрирована способность "+abilityName+" у игрока "+playerName);
			}
		}
		Configurator.saveCustomConfig(mainPlugin, pathToFile, config);
		return;
	}
	
	public void savePlayerData() {
		FileConfiguration config = Configurator.getCustomConfig(mainPlugin, pathToFile);
		Configurator.setInt(config, "Player.level", playerLevel);
		Configurator.saveCustomConfig(mainPlugin, pathToFile, config);
		return;
	}
	
}
