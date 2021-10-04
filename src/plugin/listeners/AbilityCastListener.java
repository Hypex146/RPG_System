package plugin.listeners;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import plugin.RPGSystem;
import plugin.abilities.Ability;

public class AbilityCastListener implements Listener {
	private RPGSystem mainPlugin;
	
	public AbilityCastListener(RPGSystem mainPlugin){
		this.mainPlugin = mainPlugin;
		return;
	}
	
	@EventHandler
	public void onRightClick(PlayerInteractEvent event) {
		//mainPlugin.logger.log(Level.WARNING, "OP1");
		if (event.getAction()!=Action.RIGHT_CLICK_AIR && event.getAction()!=Action.RIGHT_CLICK_BLOCK) {return;}
		if (event.getHand()!=EquipmentSlot.HAND) {return;}
		if (!event.hasItem()) {return;}
		//mainPlugin.logger.log(Level.WARNING, event.getItem().getType().toString());
		ItemStack itemStack = event.getItem();
		Player player = event.getPlayer();
		HashMap<String, Ability> abilityMap = 
				mainPlugin.getPlayerDataMap().get(player.getUniqueId().toString()).getAbilitiesMap();
		//mainPlugin.logger.log(Level.WARNING, "OP2");
		if (itemStack.getType()==Material.STICK) {
			//mainPlugin.logger.log(Level.WARNING, "OP3");
			Ability ability = abilityMap.get("EYEEXPLOSION");
			if (ability==null) {return;}
			if (!ability.canCall(player)) {return;}
			ability.onCall(player);
			return;
		}
		if (itemStack.getType()==Material.BONE) {
			//mainPlugin.logger.log(Level.WARNING, "OP4");
			Ability ability = abilityMap.get("STUN");
			if (ability==null) {return;}
			if (!ability.canCall(player)) {return;}
			ability.onCall(player);
			return;
		}
		if (itemStack.getType()==Material.WATCH) {
			Ability ability = abilityMap.get("REPULSION");
			if (ability==null) {return;}
			if (!ability.canCall(player)) {return;}
			ability.onCall(player);
			return;
		}
		return;
	}
	
	
	
}
