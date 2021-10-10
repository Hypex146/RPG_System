package plugin.abilities;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import plugin.RPGSystem;
import plugin.utilities.APIExpansion;
import plugin.utilities.Configurator;

public class Pull implements Ability{ /* This class is not finished and not tested */
	private final static String pathToConfig = AbilityConfigCreator.getPathToConfig();
	private final static Abilities type = Abilities.REPULSION;
	private final static RPGSystem mainPlugin = 
			(RPGSystem) Bukkit.getServer().getPluginManager().getPlugin(RPGSystem.getPluginName());
	private static int maxLevel = 3;
	private int cost;					//Configurable
	private String useMessage;			//Configurable
	private String lowManaMessage;		//Configurable
	private int repulsionRadius;		//Configurable
	private double cosDetectionAngle;	//Configurable
	private boolean multipleTarget;		//Configurable
	private int limitPlayerRepulse;		//Configurable
	private int abilityLevel;
	
	public Pull(int abilityLevel) {
		this.abilityLevel = (abilityLevel>maxLevel || abilityLevel<1) ? 1 : abilityLevel;
		update();
	}
	
	public static void staticUpdate() {
		FileConfiguration config = Configurator.getCustomConfig(mainPlugin, pathToConfig);
		maxLevel = Configurator.getInt(config, type.toString(), 3);
		Configurator.saveCustomConfig(mainPlugin, pathToConfig, config);
	}
	
	private double calculateHorizontalSpeed(double distance) {
		double horizontalSpeed = 0;
		horizontalSpeed = 1;
		/*
		 * This function is not done!!!!
		 */
		return horizontalSpeed;
	}
	
	private void update() {
		FileConfiguration config = Configurator.getCustomConfig(mainPlugin, pathToConfig);
		String pathToLevelSection = type.toString() + ".Level" + "_" + abilityLevel;
		cost = Configurator.getInt(config, pathToLevelSection + ".cost", 1);
		useMessage = Configurator.getString(config, pathToLevelSection + ".useMessage", "Repulsion!");
		lowManaMessage = Configurator.getString(config, pathToLevelSection + ".lowManaMessage", "Low mana!");
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

	@Override
	public Abilities getType() {
		return type;
	}

	@Override
	public boolean canCall(Player player) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private void pull(Player player, List<LivingEntity> targetList) { /* is not done*/
		if (targetList==null) {return;}
		for (LivingEntity target : targetList) {
			Vector repulseVector = null;
			repulseVector = player.getLocation().toVector().subtract(target.getLocation().toVector());
			repulseVector.normalize();
			repulseVector.setX(repulseVector.getX() * 1);
			repulseVector.setY((repulseVector.getY() + 0.2) * calculateHorizontalSpeed(0));
			repulseVector.setZ(repulseVector.getZ() * 1);
			target.setVelocity(repulseVector);
		}
		return;
	}

	@Override
	public void onCall(Player player) { /* is not done */
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
		pull(player, targets);
		player.sendMessage(useMessage);
		player.giveExp((-1)*cost);
		return;
	}
	
}
