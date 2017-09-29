package de.janmm14.jsonmessagemaker.bukkit.universalimpl;

import org.bukkit.entity.Player;

import net.md_5.bungee.api.chat.BaseComponent;

public class BukkitPlayerSender extends BukkitCommandSender {

	public BukkitPlayerSender(Player player) {
		super(player);
	}

	@Override
	public boolean isConsole() {
		return false;
	}

	@Override
	public void sendMessage(BaseComponent msg) {
		((Player) sender).spigot().sendMessage(msg);
	}

	@Override
	public void sendMessage(BaseComponent... msg) {
		((Player) sender).spigot().sendMessage(msg);
	}
}
