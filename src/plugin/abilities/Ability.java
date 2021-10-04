package plugin.abilities;

import org.bukkit.entity.Player;

public interface Ability {
	
	public void setLevel(int level);
	
	public int getLevel();
	
	public int getCost();
	
	public Abilities getType();
	
	public boolean canCall(Player player);
	
	public void onCall(Player player);
	
}
