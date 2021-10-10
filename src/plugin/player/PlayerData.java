package plugin.player;

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
	
	public HashMap<String, Ability> getAbilitiesMap(){
		return abilitiesMap;
	}
	
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
		pathToFile = pathToFolder + "/" + uuid.toString();
		abilitiesMap = new HashMap<String, Ability>();
		loadConfig();
		return;
	}
	
	private void loadAbilities(List<String> possibleAbilitiesStr) {
		for (int i=0; i<possibleAbilitiesStr.size(); i++) {
			Ability ability;
			ability = Abilities.createAbility(possibleAbilitiesStr.get(i), 1);
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
	}
	
	private void loadConfig() {
		FileConfiguration config = Configurator.getCustomConfig(mainPlugin, pathToFile);
		Configurator.setString(config, "name", playerName);
		playerLevel = Configurator.getInt(config, "level", 1);
		List<String> possibleAbilitiesStr = Configurator.getStringList(config, "Available-Abilities");
		loadAbilities(possibleAbilitiesStr);
		Configurator.saveCustomConfig(mainPlugin, pathToFile, config);
		return;
	}
	
	public void savePlayerData() {
		FileConfiguration config = Configurator.getCustomConfig(mainPlugin, pathToFile);
		Configurator.setInt(config, "level", playerLevel);
		Configurator.saveCustomConfig(mainPlugin, pathToFile, config);
		return;
	}
	
}
