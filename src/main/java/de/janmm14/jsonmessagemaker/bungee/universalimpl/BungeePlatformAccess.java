package de.janmm14.jsonmessagemaker.bungee.universalimpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import de.janmm14.jsonmessagemaker.bungee.BungeeMain;
import de.janmm14.jsonmessagemaker.universal.PlatformAccess;
import de.janmm14.jsonmessagemaker.universal.UniversalSender;

public class BungeePlatformAccess implements PlatformAccess {

	private final ProxyServer server;
	private final BungeeMain plugin;
	private final BungeeCommandSender consoleSender;
	private Boolean debug;

	public BungeePlatformAccess(ProxyServer server, BungeeMain plugin) {
		this.server = server;
		this.plugin = plugin;
		consoleSender = new BungeeCommandSender(server.getConsole());
	}

	@Override
	public Collection<UniversalSender> getPlayers() {
		final Collection<ProxiedPlayer> players = server.getPlayers();
		final List<UniversalSender> result = new ArrayList<>(players.size());
		for (ProxiedPlayer player : players) {
			result.add(new BungeeCommandSender(player));
		}
		return result;
	}

	@Override
	public UniversalSender getPlayer(String name) {
		final ProxiedPlayer player = server.getPlayer(name);
		return player == null ? null : new BungeeCommandSender(player);
	}

	@Override
	public UniversalSender getPlayer(UUID uuid) {
		final ProxiedPlayer player = server.getPlayer(uuid);
		return player == null ? null : new BungeeCommandSender(player);
	}

	@Override
	public UniversalSender getConsole() {
		return consoleSender;
	}

	@Override
	public boolean sendPluginMessage(String command) {
		return false;
	}

	@Override
	public boolean isDebug() {
		if (debug == null) {
			if (plugin.getConfig() != null) {
				return debug = plugin.getConfig().getBoolean("debug");
			}
			return false;
		}
		return debug;
	}

	@Override
	public Logger getLogger() {
		return plugin.getLogger();
	}
}
