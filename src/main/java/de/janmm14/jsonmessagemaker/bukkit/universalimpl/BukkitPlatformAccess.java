package de.janmm14.jsonmessagemaker.bukkit.universalimpl;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import java.util.logging.Logger;

import de.janmm14.jsonmessagemaker.universal.PlatformAccess;
import de.janmm14.jsonmessagemaker.universal.UniversalSender;

public class BukkitPlatformAccess implements PlatformAccess {

	private final Server server;
	private final BukkitCommandSender bukkitCommandSender;
	private final JavaPlugin plugin;
	private Boolean debug;

	public BukkitPlatformAccess(Server server, JavaPlugin plugin) {
		this.server = server;
		this.plugin = plugin;
		bukkitCommandSender = new BukkitCommandSender(server.getConsoleSender());
	}

	@Override
	public Collection<UniversalSender> getPlayers() {
		final Collection<? extends Player> onlinePlayers = server.getOnlinePlayers();
		final Collection<UniversalSender> result = new ArrayList<>(onlinePlayers.size());
		for (final Player player : onlinePlayers) {
			result.add(new BukkitPlayerSender(player));
		}
		return result;
	}

	@Override
	public UniversalSender getPlayer(String name) {
		final Player plr = server.getPlayerExact(name);
		if (plr == null) {
			return null;
		}
		return new BukkitPlayerSender(plr);
	}

	@Override
	public UniversalSender getPlayer(UUID uuid) {
		final Player plr = server.getPlayer(uuid);
		if (plr == null) {
			return null;
		}
		return new BukkitPlayerSender(plr);
	}

	@Override
	public UniversalSender getConsole() {
		return bukkitCommandSender;
	}

	@Override
	public boolean sendPluginMessage(String message) {
		final ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("JsonMessageMakerBungee");
		out.writeUTF(message);
		final Player player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
		if (player == null) {
			plugin.getLogger().warning("Could not send message to bungee, because no player is online: " + message);
			return false;
		}
		if (isDebug()) {
			plugin.getLogger().info("sending message via " + player.getName() + " to bungee: " + message);
		}
		player.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
		return true;
	}

	@Override
	public boolean isDebug() {
		return debug == null ? debug = plugin.getConfig().getBoolean("debug") : debug;
	}

	@Override
	public Logger getLogger() {
		return plugin.getLogger();
	}
}
