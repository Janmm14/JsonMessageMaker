package de.janmm14.jsonmessagemaker.bungee.universalimpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import de.janmm14.jsonmessagemaker.universal.PlatformAccess;
import de.janmm14.jsonmessagemaker.universal.UniversalSender;

public class BungeePlatformAccess implements PlatformAccess {

	private final ProxyServer server;
	private final BungeeCommandSender consoleSender;

	public BungeePlatformAccess(ProxyServer server) {
		this.server = server;
		consoleSender = new BungeeCommandSender(server.getConsole());
	}

	@Override
	public Collection<UniversalSender> getPlayers() {
		Collection<ProxiedPlayer> players = server.getPlayers();
		List<UniversalSender> result = new ArrayList<>(players.size());
		for (ProxiedPlayer player : players) {
			result.add(new BungeeCommandSender(player));
		}
		return result;
	}

	@Override
	public UniversalSender getPlayer(String name) {
		return new BungeeCommandSender(server.getPlayer(name));
	}

	@Override
	public UniversalSender getConsole() {
		return consoleSender;
	}
}
