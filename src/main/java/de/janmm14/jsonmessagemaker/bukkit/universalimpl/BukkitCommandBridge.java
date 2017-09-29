package de.janmm14.jsonmessagemaker.bukkit.universalimpl;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.janmm14.jsonmessagemaker.universal.UniversalCommandExecutor;
import de.janmm14.jsonmessagemaker.universal.UniversalSender;

public class BukkitCommandBridge implements CommandExecutor {

	private final UniversalCommandExecutor commandExecutor;

	public BukkitCommandBridge(UniversalCommandExecutor commandExecutor) {
		this.commandExecutor = commandExecutor;
	}

	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String alias, String[] args) {
		UniversalSender sender;
		if (cs instanceof Player) {
			sender = new BukkitPlayerSender((Player) cs);
		} else {
			sender = new BukkitCommandSender(cs);
		}
		commandExecutor.executeCommand(sender, alias, args);
		return true;
	}
}
