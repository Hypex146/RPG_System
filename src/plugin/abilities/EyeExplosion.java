package plugin.abilities;

import org.bukkit.entity.Player;

import plugin.RPGSystem;

public class EyeExplosion implements Ability {
	private final Abilities type = Abilities.EYEEXPLOSION;
	private static RPGSystem mainPlugin;
	private int cost;
	private String useMessage;
	private String lowManaMessage;
	private int damageRadius;
	private int chargingTime;
	private double cosDetectionAngle;
	private double damage;
	
	public EyeExplosion(RPGSystem mainPlugin) {
		this.mainPlugin = mainPlugin;
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onCall(Player player) {
		// TODO Auto-generated method stub
		return;
	}

}
