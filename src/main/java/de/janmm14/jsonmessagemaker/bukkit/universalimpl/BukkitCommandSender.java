package de.janmm14.jsonmessagemaker.bukkit.universalimpl;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;

import de.janmm14.jsonmessagemaker.universal.UniversalSender;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BukkitCommandSender implements UniversalSender {

	protected final CommandSender sender;

	@Override
	public String getName() {
		return sender.getName();
	}

	@Override
	public boolean isConsole() {
		return sender instanceof ConsoleCommandSender;
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
		sender.sendMessage(TextComponent.toLegacyText(msg));
	}

	@Override
	public void sendMessage(BaseComponent... msg) {
		sender.sendMessage(TextComponent.toLegacyText(msg));
	}
}
