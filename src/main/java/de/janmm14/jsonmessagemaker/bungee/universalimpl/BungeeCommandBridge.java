package de.janmm14.jsonmessagemaker.bungee.universalimpl;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Command;

import de.janmm14.jsonmessagemaker.universal.UniversalCommandExecutor;
import de.janmm14.jsonmessagemaker.universal.UniversalSender;

public class BungeeCommandBridge extends Command {

	private final ProxyServer proxy;
	private final UniversalCommandExecutor commandExecutor;

	public BungeeCommandBridge(ProxyServer proxy, UniversalCommandExecutor commandExecutor, String cmdName, String permission, String... aliases) {
		super(cmdName, permission, aliases);
		this.proxy = proxy;
		this.commandExecutor = commandExecutor;
	}

	@Override
	public void execute(CommandSender cs, String[] args) {
		UniversalSender sender = new BungeeCommandSender(proxy.getConsole());
		commandExecutor.executeCommand(sender, getName(), args);
	}
}
