package plugin.commands;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import plugin.RPGSystem;

public class CommandLvl implements CommandExecutor {
	private RPGSystem mainPlugin;
	
	public CommandLvl(RPGSystem mainPlugin) {
		this.mainPlugin = mainPlugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Данную команду нельзя использовать в консоли!");
			return false;
		}
		if (args.length>1) {
			sender.sendMessage("Лишние аргументы!");
			return false;
		}
		if (args.length==0) {
			sender.sendMessage("Ваш уровень: "+mainPlugin.getPlayerDataMap().get(sender.getName()).getPlayerLevel());
			return true;
		}
		if (args[0].equals("up")) {
			if (mainPlugin.getEconomy().getBalance((OfflinePlayer)sender)>=500) {
				mainPlugin.getEconomy().withdrawPlayer((OfflinePlayer)sender, 500);
				mainPlugin.getPlayerDataMap().get(sender.getName()).setPlayerLevel(
						mainPlugin.getPlayerDataMap().get(sender.getName()).getPlayerLevel()+1);
				sender.sendMessage("Вы повысили уровень!");
			} else {
				sender.sendMessage("Не хвтает денег!");
			}

		}
		return false;
	}
}
