package de.janmm14.jsonmessagemaker.bukkit;

import org.bukkit.plugin.java.JavaPlugin;

import de.janmm14.jsonmessagemaker.bukkit.universalimpl.BukkitCommandBridge;
import de.janmm14.jsonmessagemaker.bukkit.universalimpl.BukkitPlatformAccess;
import de.janmm14.jsonmessagemaker.universal.impl.JsonMessageMakerCommandExecutor;

@SuppressWarnings("unused")
public class BukkitMain extends JavaPlugin {

	@Override
	public void onEnable() {
		final JsonMessageMakerCommandExecutor commandExecutor = new JsonMessageMakerCommandExecutor(new BukkitPlatformAccess(getServer()));

		getCommand("jsonmessagemaker").setExecutor(new BukkitCommandBridge(commandExecutor));
	}

}
