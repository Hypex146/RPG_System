package plugin.abilities;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import plugin.RPGSystem;
import plugin.utilities.APIExpansion;
import plugin.utilities.Configurator;

public class EyeExplosion implements Ability {
	private final static String pathToConfig = AbilityConfigCreator.getPathToConfig();
	private final static Abilities type = Abilities.EYEEXPLOSION;
	private final static int maxLevel = 3;
	private final RPGSystem mainPlugin;
	private int cost;					//Configurable
	private String useMessage;			//Configurable
	private String lowManaMessage;		//Configurable
	private int damageRadius;			//Configurable
	private int chargingTime;			//Configurable
	private double cosDetectionAngle;	//Configurable
	private double damage;				//Configurable
	private int abilityLevel;
	
	public EyeExplosion(RPGSystem mainPlugin, int abilityLevel) {
		this.mainPlugin = mainPlugin;
		this.abilityLevel = abilityLevel;
		update();
	}
	
	private void update() {
		FileConfiguration config = Configurator.getCustomConfig(mainPlugin, pathToConfig);
		String pathToLevelSection = type.toString() + ".Level" + "_" + abilityLevel;
		cost = Configurator.getInt(config, pathToLevelSection + ".cost", 1);
		useMessage = Configurator.getString(config, pathToLevelSection + ".useMessage", "EyeExplosion!");
		lowManaMessage = Configurator.getString(config, pathToLevelSection + ".lowManaMessage", "Low mana!");
		damageRadius = Configurator.getInt(config, pathToLevelSection + ".damageRadius", 5);
		chargingTime = Configurator.getInt(config, pathToLevelSection + ".chargingTime", 5);
		cosDetectionAngle = Configurator.getDouble(config, pathToLevelSection + ".cosDetectionAngle", 0.5D);
		damage = Configurator.getDouble(config, pathToLevelSection + ".damage", 3D);
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
	
	private void abilityExplosion(Player player) {
		List<EntityType> entityType = new ArrayList<EntityType>();
		entityType.add(EntityType.PLAYER);
		List<LivingEntity> targets = APIExpansion.getLookingEntities(player, entityType, damageRadius, cosDetectionAngle);
		for (LivingEntity target : targets) {
			target.damage(damage);
		}
		return;
	}

	@Override
	public void onCall(Player player) {
		player.sendMessage(useMessage);
		player.giveExp((-1)*cost);
		Location playerLoc = player.getLocation();
		player.getWorld().spawnParticle(Particle.CLOUD, playerLoc, 25, 0.5, 0.5, 0.5, 0.1);
		player.setAllowFlight(true);
		player.setFlying(true);
		player.setFlySpeed(0);
		Location teleportTo = playerLoc.clone();
		teleportTo.setY(playerLoc.getY()+1D);
		player.teleport(teleportTo);
		Bukkit.getServer().getScheduler().runTaskLater(
				mainPlugin, new Runnable() {
					@Override
					public void run() {
						Location playerLoc = player.getLocation();
						playerLoc.setY(playerLoc.getY()+1.5D);
						player.getWorld().spawnParticle(Particle.DRAGON_BREATH, playerLoc, 75, 0.5, 0.5, 0.5, 0.25);
						abilityExplosion(player);
						player.setFlySpeed(0.1f);
						player.setFlying(false);
						player.setAllowFlight(false);
						return;
					}
				}, chargingTime);
		return;
	}

}
