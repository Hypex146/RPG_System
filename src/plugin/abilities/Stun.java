package plugin.abilities;

import org.bukkit.entity.Player;

import plugin.RPGSystem;

public class Stun implements Ability{
	private final Abilities type = Abilities.STUN;
	private RPGSystem mainPlugin;
	private int cost;
	private String useMessage;
	private String lowManaMessage;
	private int stunDist;
	private int limitPlayerStun;
	private boolean unlimitedPlayerStun;
	private int timeStun;
	private double cosDetectionAngle;
	
	public Stun(RPGSystem mainPlugin) {
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
