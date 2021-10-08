package plugin.commands;

import java.io.IOException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import plugin.RPGSystem;

public class CommandTest implements CommandExecutor {
	private RPGSystem mainPlugin;
	
	public CommandTest(RPGSystem mainPlugin) {
		this.mainPlugin = mainPlugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("You cant use this command from here!");
			return false;
		}
		Player senderP = (Player)sender;
		if (!senderP.isOp()) {
			sender.sendMessage("You cant use this command without OP!");
			return false;
		}
		if (args.length == 5 && args[0].equals("pull")) {
			Player target = mainPlugin.getServer().getPlayer(args[1]);
			if (target==null) {
				sender.sendMessage("Error in commands!");
				return false;
			}
			Vector repulseVector = senderP.getLocation().toVector().subtract(target.getLocation().toVector());
			repulseVector.normalize();
			try {
				repulseVector.setX(repulseVector.getX() * Double.parseDouble(args[2]));
				repulseVector.setY((repulseVector.getY() + Double.parseDouble(args[3])) * Double.parseDouble(args[4]));
				repulseVector.setZ(repulseVector.getZ() * Double.parseDouble(args[2]));
			} catch(Exception e) {
				return false;
			}
			target.setVelocity(repulseVector);
			return true;
		}
		sender.sendMessage("Error in commands!");
		return false;
	}
	
}
