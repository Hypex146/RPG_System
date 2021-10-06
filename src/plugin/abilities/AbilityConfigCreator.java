package plugin.abilities;

import org.bukkit.configuration.file.FileConfiguration;

import plugin.RPGSystem;
import plugin.utilities.Configurator;

public class AbilityConfigCreator {
	private static final String pathToConfig = "Settings/Abilities.yml";
	
	static String getPathToConfig() {
		return pathToConfig;
	}
	
	private static void createEyeExplosionConfig(RPGSystem mainPlugin) {
		FileConfiguration config = Configurator.getCustomConfig(mainPlugin, pathToConfig);
		String pathToAbilitySection = Abilities.EYEEXPLOSION.toString();
		String pathToLevelSection = "";
		int maxLevel = Configurator.getInt(config, pathToAbilitySection + ".maxLevel", 3);
		for (int level=1; level<=maxLevel; level++) {
			pathToLevelSection = pathToAbilitySection + ".Level_" + level;
			Configurator.getInt(config, pathToLevelSection + ".cost", 1);
			Configurator.getString(config, pathToLevelSection + ".useMessage", "EyeExplosion!");
			Configurator.getString(config, pathToLevelSection + ".lowManaMessage", "Low mana!");
			Configurator.getInt(config, pathToLevelSection + ".damageRadius", 5);
			Configurator.getInt(config, pathToLevelSection + ".chargingTime", 5);
			Configurator.getDouble(config, pathToLevelSection + ".cosDetectionAngle", 0.5D);
			Configurator.getDouble(config, pathToLevelSection + ".damage", 3D);
		}
		Configurator.saveCustomConfig(mainPlugin, pathToConfig, config);
	}
	
	private static void createRepulsionConfig(RPGSystem mainPlugin) {
		FileConfiguration config = Configurator.getCustomConfig(mainPlugin, pathToConfig);
		String pathToAbilitySection = Abilities.REPULSION.toString();
		String pathToLevelSection = "";
		int maxLevel = Configurator.getInt(config, pathToAbilitySection + ".maxLevel", 3);
		for (int level=1; level<=maxLevel; level++) {
			pathToLevelSection = pathToAbilitySection + ".Level_" + level;
			Configurator.getInt(config, pathToLevelSection + ".cost", 1);
			Configurator.getString(config, pathToLevelSection + ".useMessage", "Repulsion!");
			Configurator.getString(config, pathToLevelSection + ".lowManaMessage", "Low mana!");
			Configurator.getDouble(config, pathToLevelSection + ".horizontalSpeed", 1.2D);
			Configurator.getDouble(config, pathToLevelSection + ".verticalSpeed", 1.2D);
			Configurator.getDouble(config, pathToLevelSection + ".verticalOffset", 0.2D);
			Configurator.getInt(config, pathToLevelSection + ".repulsionRadius", 5);
			Configurator.getDouble(config, pathToLevelSection + ".cosDetectionAngle", 0.95D);
			Configurator.getBoolean(config, pathToLevelSection + ".multipleTarget", true);
			Configurator.getInt(config, pathToLevelSection + ".limitPlayerRepulse", 1);
		}
		Configurator.saveCustomConfig(mainPlugin, pathToConfig, config);
	}
	
	private static void createStunConfig(RPGSystem mainPlugin) {
		FileConfiguration config = Configurator.getCustomConfig(mainPlugin, pathToConfig);
		String pathToAbilitySection = Abilities.STUN.toString();
		String pathToLevelSection = "";
		int maxLevel = Configurator.getInt(config, pathToAbilitySection + ".maxLevel", 3);
		for (int level=1; level<=maxLevel; level++) {
			pathToLevelSection = pathToAbilitySection + ".Level_" + level;
			Configurator.getInt(config, pathToLevelSection + ".cost", 1);
			Configurator.getString(config, pathToLevelSection + ".useMessage", "Stun!");
			Configurator.getString(config, pathToLevelSection + ".lowManaMessage", "Low mana!");
			Configurator.getInt(config, pathToLevelSection + ".stunDist", 5);
			Configurator.getInt(config, pathToLevelSection + ".limitPlayerStun", 1);
			Configurator.getBoolean(config, pathToLevelSection + ".unlimitedPlayerStun", false);
			Configurator.getInt(config, pathToLevelSection + ".timeStun", 20);
			Configurator.getDouble(config, pathToLevelSection + ".cosDetectionAngle", 0.95D);
		}
		Configurator.saveCustomConfig(mainPlugin, pathToConfig, config);
	}
	
	public static void createAllConfig(RPGSystem mainPlugin) {
		createEyeExplosionConfig(mainPlugin);
		createRepulsionConfig(mainPlugin);
		createStunConfig(mainPlugin);
	}
	
}
