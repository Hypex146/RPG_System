package plugin.abilities;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import plugin.RPGSystem;
import plugin.utilities.APIExpansion;
import plugin.utilities.Configurator;

public class Repulsion implements Ability{
	private final static String pathToConfig = "Settings/Abilities.yml";
	private final Abilities type = Abilities.REPULSION;
	private final static int maxLevel = 3;
	private final RPGSystem mainPlugin;
	private int cost;					//Configurable
	private String useMessage;			//Configurable
	private String lowManaMessage;		//Configurable
	private int speedX;					//Configurable
	private int speedY;					//Configurable
	private int repulsionRadius;		//Configurable
	private double cosDetectionAngle;	//Configurable
	private boolean multipleTarget;		//Configurable
	private int abilityLevel;
	
	public Repulsion(RPGSystem mainPlugin, int abilityLevel) {
		this.mainPlugin = mainPlugin;
		this.abilityLevel = abilityLevel;
	}
	
	private void update() {
		FileConfiguration config = Configurator.getCustomConfig(mainPlugin, pathToConfig);
		String pathToLevelSection = type.toString() + ".Level" + "_" + abilityLevel;
		cost = Configurator.getInt(config, pathToLevelSection + ".cost", 1);
		useMessage = Configurator.getString(config, pathToLevelSection + ".useMessage", "EyeExplosion!");
		lowManaMessage = Configurator.getString(config, pathToLevelSection + ".lowManaMessage", "Low mana!");
		speedX = Configurator.getInt(config, pathToLevelSection + ".speedX", 1);
		speedY = Configurator.getInt(config, pathToLevelSection + ".speedY", 1);
		repulsionRadius = Configurator.getInt(config, pathToLevelSection + ".repulsionRadius", 1);
		cosDetectionAngle = Configurator.getDouble(config, pathToLevelSection + ".cosDetectionAngle", 0.95D);
		multipleTarget = Configurator.getBoolean(config, pathToLevelSection + ".multipleTarget", true);
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
	
	private List<Player> getTargets(Player player) {
		Player target = null;
		List<Player> targetList = null;
		for (Entity entity : player.getNearbyEntities(repulsionRadius, repulsionRadius, repulsionRadius)) {
			if (entity.getType()!=EntityType.PLAYER) {continue;}
			Player otherPlayer = (Player)entity;
			if (!APIExpansion.isLookingAt(player, otherPlayer, repulsionRadius, cosDetectionAngle)) {continue;}
			if (targetList==null) {targetList = new ArrayList<Player>();}
			if (multipleTarget) {
				targetList.add(otherPlayer);
			} else {
				if (target==null) {
					target = otherPlayer;
					continue;
				}
				if (target.getLocation().distance(player.getLocation()) >
				otherPlayer.getLocation().distance(player.getLocation())) {
					target = otherPlayer;
				}
			}
		}
		if (!multipleTarget && target!=null) {targetList.add(target);}
		return targetList;
	}
	
	private void repulse(Player player, List<Player> targetList) {
		if (targetList==null) {return;}
		for (Player target : targetList) {
			Vector repulseVector = null;
			repulseVector = player.getLocation().toVector().subtract(target.getLocation().toVector());
			repulseVector.normalize();
			repulseVector.setX(repulseVector.getX() * -1);
			repulseVector.setY(repulseVector.getY() * -1);
			repulseVector.setZ(repulseVector.getZ() * -1);
			target.setVelocity(repulseVector);
		}
		return;
	}

	@Override
	public void onCall(Player player) {
		repulse(player, getTargets(player));
		player.sendMessage(useMessage);
		player.giveExp((-1)*cost);
		return;
	}

}
