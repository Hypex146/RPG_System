package plugin.abilities;

import org.bukkit.entity.Player;

import plugin.RPGSystem;

public class Repulsion implements Ability{
	private final Abilities type = Abilities.REPULSION;
	private RPGSystem mainPlugin;
	private int cost;
	private String useMessage;
	private String lowManaMessage;
	private int speedX;
	private int speedY;
	private int repulsionRadius;
	private double cosDetectionAngle;
	private boolean multipleTarget;
	
	public Repulsion(RPGSystem mainPlugin) {
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
		
	}

}
