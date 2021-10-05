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
		String pathToLevelSection = "";
		// Level: 1
		pathToLevelSection = Abilities.EYEEXPLOSION.toString() + ".Level_1";
		Configurator.getInt(config, pathToLevelSection + ".cost", 1);
		Configurator.getString(config, pathToLevelSection + ".useMessage", "EyeExplosion!");
		Configurator.getString(config, pathToLevelSection + ".lowManaMessage", "Low mana!");
		Configurator.getInt(config, pathToLevelSection + ".damageRadius", 1);
		Configurator.getInt(config, pathToLevelSection + ".chargingTime", 1);
		Configurator.getDouble(config, pathToLevelSection + ".cosDetectionAngle", 0.95D);
		Configurator.getDouble(config, pathToLevelSection + ".damage", 1D);
		// Level: 2
		pathToLevelSection = Abilities.EYEEXPLOSION.toString() + ".Level_2";
		Configurator.getInt(config, pathToLevelSection + ".cost", 1);
		Configurator.getString(config, pathToLevelSection + ".useMessage", "EyeExplosion!");
		Configurator.getString(config, pathToLevelSection + ".lowManaMessage", "Low mana!");
		Configurator.getInt(config, pathToLevelSection + ".damageRadius", 1);
		Configurator.getInt(config, pathToLevelSection + ".chargingTime", 1);
		Configurator.getDouble(config, pathToLevelSection + ".cosDetectionAngle", 0.95D);
		Configurator.getDouble(config, pathToLevelSection + ".damage", 1D);
		// Level: 3
		pathToLevelSection = Abilities.EYEEXPLOSION.toString() + ".Level_3";
		Configurator.getInt(config, pathToLevelSection + ".cost", 1);
		Configurator.getString(config, pathToLevelSection + ".useMessage", "EyeExplosion!");
		Configurator.getString(config, pathToLevelSection + ".lowManaMessage", "Low mana!");
		Configurator.getInt(config, pathToLevelSection + ".damageRadius", 1);
		Configurator.getInt(config, pathToLevelSection + ".chargingTime", 1);
		Configurator.getDouble(config, pathToLevelSection + ".cosDetectionAngle", 0.95D);
		Configurator.getDouble(config, pathToLevelSection + ".damage", 1D);
		//
		Configurator.saveCustomConfig(mainPlugin, pathToConfig, config);
	}
	
	private static void createRepulsionConfig(RPGSystem mainPlugin) {
		FileConfiguration config = Configurator.getCustomConfig(mainPlugin, pathToConfig);
		String pathToLevelSection = "";
		// Level: 1
		pathToLevelSection = Abilities.REPULSION.toString() + ".Level_1";
		Configurator.getInt(config, pathToLevelSection + ".cost", 1);
		Configurator.getString(config, pathToLevelSection + ".useMessage", "EyeExplosion!");
		Configurator.getString(config, pathToLevelSection + ".lowManaMessage", "Low mana!");
		Configurator.getInt(config, pathToLevelSection + ".speedX", 1);
		Configurator.getInt(config, pathToLevelSection + ".speedY", 1);
		Configurator.getInt(config, pathToLevelSection + ".repulsionRadius", 1);
		Configurator.getDouble(config, pathToLevelSection + ".cosDetectionAngle", 0.95D);
		Configurator.getBoolean(config, pathToLevelSection + ".multipleTarget", true);
		Configurator.getInt(config, pathToLevelSection + ".limitPlayerRepulse", 1);
		// Level: 2
		pathToLevelSection = Abilities.REPULSION.toString() + ".Level_2";
		Configurator.getInt(config, pathToLevelSection + ".cost", 1);
		Configurator.getString(config, pathToLevelSection + ".useMessage", "EyeExplosion!");
		Configurator.getString(config, pathToLevelSection + ".lowManaMessage", "Low mana!");
		Configurator.getInt(config, pathToLevelSection + ".speedX", 1);
		Configurator.getInt(config, pathToLevelSection + ".speedY", 1);
		Configurator.getInt(config, pathToLevelSection + ".repulsionRadius", 1);
		Configurator.getDouble(config, pathToLevelSection + ".cosDetectionAngle", 0.95D);
		Configurator.getBoolean(config, pathToLevelSection + ".multipleTarget", true);
		Configurator.getInt(config, pathToLevelSection + ".limitPlayerRepulse", 1);
		// Level: 3
		pathToLevelSection = Abilities.REPULSION.toString() + ".Level_3";
		Configurator.getInt(config, pathToLevelSection + ".cost", 1);
		Configurator.getString(config, pathToLevelSection + ".useMessage", "EyeExplosion!");
		Configurator.getString(config, pathToLevelSection + ".lowManaMessage", "Low mana!");
		Configurator.getInt(config, pathToLevelSection + ".speedX", 1);
		Configurator.getInt(config, pathToLevelSection + ".speedY", 1);
		Configurator.getInt(config, pathToLevelSection + ".repulsionRadius", 1);
		Configurator.getDouble(config, pathToLevelSection + ".cosDetectionAngle", 0.95D);
		Configurator.getBoolean(config, pathToLevelSection + ".multipleTarget", true);
		Configurator.getInt(config, pathToLevelSection + ".limitPlayerRepulse", 1);
		//
		Configurator.saveCustomConfig(mainPlugin, pathToConfig, config);
	}
	
	private static void createStunConfig(RPGSystem mainPlugin) {
		FileConfiguration config = Configurator.getCustomConfig(mainPlugin, pathToConfig);
		String pathToLevelSection = "";
		// Level: 1
		pathToLevelSection = Abilities.STUN.toString() + ".Level_3";
		Configurator.getInt(config, pathToLevelSection + ".cost", 1);
		Configurator.getString(config, pathToLevelSection + ".useMessage", "EyeExplosion!");
		Configurator.getString(config, pathToLevelSection + ".lowManaMessage", "Low mana!");
		Configurator.getInt(config, pathToLevelSection + ".stunDist", 1);
		Configurator.getInt(config, pathToLevelSection + ".limitPlayerStun", 1);
		Configurator.getBoolean(config, pathToLevelSection + ".unlimitedPlayerStun", false);
		Configurator.getInt(config, pathToLevelSection + ".timeStun", 1);
		Configurator.getDouble(config, pathToLevelSection + ".cosDetectionAngle", 0.95D);
		// Level: 2
		pathToLevelSection = Abilities.STUN.toString() + ".Level_3";
		Configurator.getInt(config, pathToLevelSection + ".cost", 1);
		Configurator.getString(config, pathToLevelSection + ".useMessage", "EyeExplosion!");
		Configurator.getString(config, pathToLevelSection + ".lowManaMessage", "Low mana!");
		Configurator.getInt(config, pathToLevelSection + ".stunDist", 1);
		Configurator.getInt(config, pathToLevelSection + ".limitPlayerStun", 1);
		Configurator.getBoolean(config, pathToLevelSection + ".unlimitedPlayerStun", false);
		Configurator.getInt(config, pathToLevelSection + ".timeStun", 1);
		Configurator.getDouble(config, pathToLevelSection + ".cosDetectionAngle", 0.95D);
		// Level: 3
		pathToLevelSection = Abilities.STUN.toString() + ".Level_3";
		Configurator.getInt(config, pathToLevelSection + ".cost", 1);
		Configurator.getString(config, pathToLevelSection + ".useMessage", "EyeExplosion!");
		Configurator.getString(config, pathToLevelSection + ".lowManaMessage", "Low mana!");
		Configurator.getInt(config, pathToLevelSection + ".stunDist", 1);
		Configurator.getInt(config, pathToLevelSection + ".limitPlayerStun", 1);
		Configurator.getBoolean(config, pathToLevelSection + ".unlimitedPlayerStun", false);
		Configurator.getInt(config, pathToLevelSection + ".timeStun", 1);
		Configurator.getDouble(config, pathToLevelSection + ".cosDetectionAngle", 0.95D);
		//
		Configurator.saveCustomConfig(mainPlugin, pathToConfig, config);
	}
	
	public static void createAllConfig(RPGSystem mainPlugin) {
		createEyeExplosionConfig(mainPlugin);
		createRepulsionConfig(mainPlugin);
		createStunConfig(mainPlugin);
	}
	
}
