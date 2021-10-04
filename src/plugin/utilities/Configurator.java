package plugin.utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import plugin.RPGSystem;

public class Configurator {
	
	public static FileConfiguration getCustomConfig(RPGSystem mainPlugin, String pathToFile) {
		File configFile = new File(mainPlugin.getDataFolder()+"/"+pathToFile);
		File folderFile = configFile.getParentFile();
		if (!folderFile.exists()) {
			folderFile.mkdirs();
			mainPlugin.getLogger().log(Level.INFO, "Создана папка: "+folderFile.getAbsolutePath());
		}
		if (!configFile.exists()) {
			try {
				configFile.createNewFile();
				mainPlugin.getLogger().log(Level.INFO, "Создан файл: "+configFile.getAbsolutePath());
			} catch (IOException e) {
				mainPlugin.getLogger().log(Level.SEVERE, "Не удалось создать файл: (IOException) "
						+configFile.getAbsolutePath());
				e.printStackTrace();
			}
		}
		YamlConfiguration customConfig = new YamlConfiguration();
		try {
			customConfig.load(configFile);
			mainPlugin.getLogger().log(Level.INFO, "Считан файл: "+configFile.getAbsolutePath());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			mainPlugin.getLogger().log(Level.SEVERE, "Не удалось считать файл: (FileNotFoundException) "
					+configFile.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
			mainPlugin.getLogger().log(Level.SEVERE, "Не удалось считать файл: (IOException) "
					+configFile.getAbsolutePath());
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
			mainPlugin.getLogger().log(Level.SEVERE, "Не удалось считать файл (InvalidConfigurationException): "
					+configFile.getAbsolutePath());
		}
		return customConfig;
	}
	
	public static void saveCustomConfig(RPGSystem mainPlugin, String pathToFile, FileConfiguration configToSave) {
		File configFile = new File(mainPlugin.getDataFolder()+"/"+pathToFile);
		try {
			configToSave.save(configFile);
			mainPlugin.getLogger().log(Level.INFO, "Сохранён файл: "+configFile.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
			mainPlugin.getLogger().log(Level.SEVERE, "Не удалось сохранить файл: (IOException) "+configFile.getAbsolutePath());
		}
		return;
	}
	
	public static boolean getBoolean(FileConfiguration config, String field, boolean defaultValue) {
		if (!config.isBoolean(field)) {
			config.set(field, defaultValue);
		}
		return config.getBoolean(field);
	}
	
	public static void setBoolean(FileConfiguration config, String field, boolean value) {
		config.set(field, value);
	}
	
	public static int getInt(FileConfiguration config, String field, int defaultValue) {
		if (!config.isInt(field)) {
			config.set(field, defaultValue);
		}
		return config.getInt(field);
	}
	
	public static void setInt(FileConfiguration config, String field, int value) {
		config.set(field, value);
	}
	
	public static String getString(FileConfiguration config, String field, String defaultValue) {
		if (!config.isString(field)) {
			config.set(field, defaultValue);
		}
		return config.getString(field);
	}
	
	public static void setString(FileConfiguration config, String field, String value) {
		config.set(field, value);
	}
	
	public static double getDouble(FileConfiguration config, String field, double defaultValue) {
		if (!config.isDouble(field)) {
			config.set(field, defaultValue);
		}
		return config.getDouble(field);
	}
	
	public static void setDouble(FileConfiguration config, String field, double value) {
		config.set(field, value);
	}
	
	public static List<String> getStringList(FileConfiguration config, String field){
		List<String> stringList;
		stringList = config.getStringList(field);
		if (stringList.size()>0) {
			return stringList;
		}
		config.set(field, stringList);
		return stringList;
	}
	
}
