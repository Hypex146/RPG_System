package plugin.abilities;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import plugin.RPGSystem;
import plugin.utilities.APIExpansion;
import plugin.utilities.Configurator;

public class Repulsion implements Ability{
	private final static String pathToConfig = AbilityConfigCreator.getPathToConfig();
	private final static Abilities type = Abilities.REPULSION;
	private static int maxLevel = 3;
	private final RPGSystem mainPlugin;
	private int cost;					//Configurable
	private String useMessage;			//Configurable
	private String lowManaMessage;		//Configurable
	private double horizontalSpeed;		//Configurable
	private double verticalSpeed;			//Configurable
	private double verticalOffset;			//Configurable
	private int repulsionRadius;		//Configurable
	private double cosDetectionAngle;	//Configurable
	private boolean multipleTarget;		//Configurable
	private int limitPlayerRepulse;		//Configurable
	private int abilityLevel;
	
	public Repulsion(RPGSystem mainPlugin, int abilityLevel) {
		this.mainPlugin = mainPlugin;
		this.abilityLevel = (abilityLevel>maxLevel || abilityLevel<1) ? 1 : abilityLevel;
		update();
	}
	
	public static void staticUpdate(RPGSystem mainPlugin) {
		FileConfiguration config = Configurator.getCustomConfig(mainPlugin, pathToConfig);
		maxLevel = Configurator.getInt(config, type.toString(), 3);
		Configurator.saveCustomConfig(mainPlugin, pathToConfig, config);
	}
	
	private void update() {
		FileConfiguration config = Configurator.getCustomConfig(mainPlugin, pathToConfig);
		String pathToLevelSection = type.toString() + ".Level" + "_" + abilityLevel;
		cost = Configurator.getInt(config, pathToLevelSection + ".cost", 1);
		useMessage = Configurator.getString(config, pathToLevelSection + ".useMessage", "Repulsion!");
		lowManaMessage = Configurator.getString(config, pathToLevelSection + ".lowManaMessage", "Low mana!");
		horizontalSpeed = Configurator.getDouble(config, pathToLevelSection + ".horizontalSpeed", 1.2D);
		verticalSpeed = Configurator.getDouble(config, pathToLevelSection + ".verticalSpeed", 1.2D);
		verticalOffset = Configurator.getDouble(config, pathToLevelSection + ".verticalOffset", 0.2D);
		repulsionRadius = Configurator.getInt(config, pathToLevelSection + ".repulsionRadius", 5);
		cosDetectionAngle = Configurator.getDouble(config, pathToLevelSection + ".cosDetectionAngle", 0.95D);
		multipleTarget = Configurator.getBoolean(config, pathToLevelSection + ".multipleTarget", true);
		limitPlayerRepulse = Configurator.getInt(config, pathToLevelSection + ".limitPlayerRepulse", 1);
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
	
	private void repulse(Player player, List<LivingEntity> targetList) {
		if (targetList==null) {return;}
		for (LivingEntity target : targetList) {
			Vector repulseVector = null;
			repulseVector = player.getLocation().toVector().subtract(target.getLocation().toVector());
			repulseVector.normalize();
			repulseVector.setX(repulseVector.getX() * -1 * horizontalSpeed);
			repulseVector.setY((repulseVector.getY() * -1 + verticalOffset) * verticalSpeed);
			repulseVector.setZ(repulseVector.getZ() * -1 * horizontalSpeed);
			target.setVelocity(repulseVector);
		}
		return;
	}

	@Override
	public void onCall(Player player) {
		List<EntityType> entityType = new ArrayList<EntityType>();
		entityType.add(EntityType.PLAYER);
		List<LivingEntity> targets = null;
		if (multipleTarget) {
			targets = APIExpansion.getUnderAbservation(player, 
					entityType, repulsionRadius, cosDetectionAngle);
		} else {
			targets = APIExpansion.getUnderAbservation(player, 
					entityType, repulsionRadius, cosDetectionAngle, limitPlayerRepulse);
		}
		repulse(player, targets);
		player.sendMessage(useMessage);
		player.giveExp((-1)*cost);
		return;
	}

}
