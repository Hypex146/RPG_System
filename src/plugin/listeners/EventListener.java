package plugin.listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

import plugin.RPGSystem;
import plugin.player.PlayerData;

public class EventListener implements Listener {
	private RPGSystem mainPlugin;
	
	public EventListener(RPGSystem mainPlugin) {
		this.mainPlugin = mainPlugin;
	}
	
	@EventHandler
	public void onToggleFlightEvent(PlayerToggleFlightEvent event) {
		if (event.getPlayer().getGameMode()==GameMode.CREATIVE) {return;}
		event.setCancelled(true);
		return;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		mainPlugin.getPlayerDataMap().put(player.getUniqueId().toString(), new PlayerData(mainPlugin, player));
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if (player.getWalkSpeed()==0) {player.setWalkSpeed(0.2f);}
		if (player.getFlySpeed()==0) {player.setFlySpeed(0.1f);}
		mainPlugin.getPlayerDataMap().get(player.getUniqueId().toString()).savePlayerData();
		mainPlugin.getPlayerDataMap().remove(player.getUniqueId().toString());
		return;
	}
	
	
}
