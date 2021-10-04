package plugin.tasks;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import plugin.RPGSystem;

public class Tasks {
	
	static public void addSchedule(RPGSystem mainPlugin) {
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		
		scheduler.scheduleSyncRepeatingTask(
				mainPlugin, new Runnable() {
					@Override
					public void run() {
						Server server = Bukkit.getServer();
						for (Player player : server.getOnlinePlayers()) {
							player.giveExp(mainPlugin.addExpInSec);
							int playerLevel = mainPlugin.playerDataMap.get(player.getName()).getPlayerLevel();
							if (player.getLevel()>playerLevel) {
								player.setLevel(playerLevel);
								player.setExp(0.99f);
							} else if (player.getLevel()<playerLevel) {
								player.setLevel(playerLevel);
								player.setExp(0f);
							}
						}
						return;
					}
				}, 0, 20);
		
	}
	
}
