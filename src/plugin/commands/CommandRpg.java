package plugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import plugin.RPGSystem;

public class CommandRpg implements CommandExecutor {
	private RPGSystem mainPlugin;
	
	public CommandRpg(RPGSystem mainPlugin) {
		this.mainPlugin = mainPlugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!sender.hasPermission("rpg.admin")) {
			sender.sendMessage("У вас нет на это прав!");
			return false;
		}
		if (args.length!=1||!args[0].equals("reload")) {
			sender.sendMessage("Ошбика в аргументах");
			return false;
		}
		return true;
	}
	
}
