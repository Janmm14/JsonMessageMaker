package de.janmm14.jsonmessagemaker.bungee.universalimpl;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import de.janmm14.jsonmessagemaker.universal.UniversalSender;

public class BungeeCommandSender implements UniversalSender {

	private final CommandSender sender;

	public BungeeCommandSender(CommandSender sender) {
		this.sender = sender;
	}

	@Override
	public String getName() {
		return sender.getName();
	}

	@Override
	public boolean isConsole() {
		return !(sender instanceof ProxiedPlayer);
	}

	@Override
	public boolean hasPermission(String permission) {
		return sender.hasPermission(permission);
	}

	@Override
	public void sendMessage(String msg) {
		sender.sendMessage(msg);
	}

	@Override
	public void sendMessage(BaseComponent msg) {
		sender.sendMessage(msg);
	}

	@Override
	public void sendMessage(BaseComponent... msg) {
		sender.sendMessage(msg);
	}
}
