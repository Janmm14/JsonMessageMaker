package de.janmm14.jsonmessagemaker.bukkit;

import org.bukkit.plugin.java.JavaPlugin;

import de.janmm14.jsonmessagemaker.bukkit.universalimpl.BukkitCommandBridge;
import de.janmm14.jsonmessagemaker.bukkit.universalimpl.BukkitPlatformAccess;
import de.janmm14.jsonmessagemaker.universal.impl.JsonMessageMakerCommandExecutor;

@SuppressWarnings("unused")
public class BukkitMain extends JavaPlugin {

	@Override
	public void onEnable() {
		getConfig().options().copyDefaults(true);
		getConfig().addDefault("sendToBungeeOption", false);
		saveConfig();

		final boolean sendToBungeeOption = getConfig().getBoolean("sendToBungeeOption");
		if (sendToBungeeOption) {
			getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		}

		JsonMessageMakerCommandExecutor commandExecutor = new JsonMessageMakerCommandExecutor(new BukkitPlatformAccess(getServer(), this), sendToBungeeOption);

		getCommand("jsonmessagemaker").setExecutor(new BukkitCommandBridge(commandExecutor));
	}

}
