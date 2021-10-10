package plugin.abilities;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import plugin.RPGSystem;
import plugin.utilities.APIExpansion;
import plugin.utilities.Configurator;

public class Stun implements Ability{
	private final static String pathToConfig = AbilityConfigCreator.getPathToConfig();
	private final static Abilities type = Abilities.STUN;
	private final static RPGSystem mainPlugin = 
			(RPGSystem) Bukkit.getServer().getPluginManager().getPlugin(RPGSystem.getPluginName());
	private static int maxLevel = 3;
	private int cost;						//Configurable
	private String useMessage;				//Configurable
	private String lowManaMessage;			//Configurable
	private int stunDist;					//Configurable
	private int limitPlayerStun;			//Configurable
	private boolean unlimitedPlayerStun;	//Configurable
	private int timeStun;					//Configurable
	private double cosDetectionAngle;		//Configurable
	private int abilityLevel;
	
	public Stun(int abilityLevel) {
		this.abilityLevel = (abilityLevel>maxLevel || abilityLevel<1) ? 1 : abilityLevel;
		update();
	}
	
	public static void staticUpdate() {
		FileConfiguration config = Configurator.getCustomConfig(mainPlugin, pathToConfig);
		maxLevel = Configurator.getInt(config, type.toString(), 3);
		Configurator.saveCustomConfig(mainPlugin, pathToConfig, config);
	}
	
	private void update() {
		FileConfiguration config = Configurator.getCustomConfig(mainPlugin, pathToConfig);
		String pathToLevelSection = type.toString() + ".Level" + "_" + abilityLevel;
		cost = Configurator.getInt(config, pathToLevelSection + ".cost", 1);
		useMessage = Configurator.getString(config, pathToLevelSection + ".useMessage", "Stun!");
		lowManaMessage = Configurator.getString(config, pathToLevelSection + ".lowManaMessage", "Low mana!");
		stunDist = Configurator.getInt(config, pathToLevelSection + ".stunDist", 5);
		limitPlayerStun = Configurator.getInt(config, pathToLevelSection + ".limitPlayerStun", 1);
		unlimitedPlayerStun = Configurator.getBoolean(config, pathToLevelSection + ".unlimitedPlayerStun", false);
		timeStun = Configurator.getInt(config, pathToLevelSection + ".timeStun", 20);
		cosDetectionAngle = Configurator.getDouble(config, pathToLevelSection + ".cosDetectionAngle", 0.95D);
		Configurator.saveCustomConfig(mainPlugin, pathToConfig, config);
	}
	
	@Override
	public void setLevel(int level) {
		this.abilityLevel = level;
		update();
	}
	
	@Override
	public int getLevel() {
		return abilityLevel;
	}

	@Override
	public int getCost() {
		return cost;
	}
	
	public Abilities getType() {
		return type;
	}

	@Override
	public boolean canCall(Player player) {
		/*
		if ((player.getExp())<((float)cost/(float)mainPlugin.EXPTOFULL)) {
			player.sendMessage(lowManaMessage);
			return false;
		}*/
		return true;
	}	
	
	@Override
	public void onCall(Player player) {
		List<EntityType> entityType = new ArrayList<EntityType>();
		entityType.add(EntityType.PLAYER);
		List<LivingEntity> targets = null;
		if (unlimitedPlayerStun) {
			targets = APIExpansion.getUnderAbservation(player, 
					entityType, stunDist, cosDetectionAngle);
		} else {
			targets = APIExpansion.getUnderAbservation(player, 
					entityType, stunDist, cosDetectionAngle, limitPlayerStun);
		}
		if (targets!=null) {
			for (LivingEntity entity : targets) {
				if(entity.hasPotionEffect(PotionEffectType.SLOW)) {
					entity.removePotionEffect(PotionEffectType.SLOW);
				}
				entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, timeStun, 5));
				entity.getWorld().spawnParticle(Particle.CLOUD, entity.getLocation(), 25, 0.5, 0.5, 0.5, 0.25);
			}
		}
		player.sendMessage(useMessage);
		player.giveExp((-1)*cost);
		return;
	}

}
