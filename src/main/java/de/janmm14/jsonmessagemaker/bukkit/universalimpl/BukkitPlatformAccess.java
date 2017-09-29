package de.janmm14.jsonmessagemaker.bukkit.universalimpl;

import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;

import de.janmm14.jsonmessagemaker.universal.PlatformAccess;
import de.janmm14.jsonmessagemaker.universal.UniversalSender;

public class BukkitPlatformAccess implements PlatformAccess {

	private final Server server;
	private final BukkitCommandSender bukkitCommandSender;

	public BukkitPlatformAccess(Server server) {
		this.server = server;
		bukkitCommandSender = new BukkitCommandSender(server.getConsoleSender());
	}

	@Override
	public Collection<UniversalSender> getPlayers() {
		Collection<? extends Player> onlinePlayers = server.getOnlinePlayers();
		Collection<UniversalSender> result = new ArrayList<>(onlinePlayers.size());
		for (Player player : onlinePlayers) {
			result.add(new BukkitPlayerSender(player));
		}
		return result;
	}

	@Override
	public UniversalSender getPlayer(String name) {
		Player plr = server.getPlayerExact(name);
		if (plr == null) {
			return null;
		}
		return new BukkitPlayerSender(plr);
	}

	@Override
	public UniversalSender getConsole() {
		return bukkitCommandSender;
	}
}
