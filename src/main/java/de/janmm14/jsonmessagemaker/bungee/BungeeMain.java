package de.janmm14.jsonmessagemaker.bungee;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import net.md_5.bungee.event.EventHandler;

import de.janmm14.jsonmessagemaker.bungee.universalimpl.BungeeCommandBridge;
import de.janmm14.jsonmessagemaker.bungee.universalimpl.BungeePlatformAccess;
import de.janmm14.jsonmessagemaker.universal.impl.JsonMessageMakerCommandExecutor;

@SuppressWarnings("unused")
public class BungeeMain extends Plugin implements Listener {

	private static final String JMM_COMMAND_PERMISSION = "jsonmessagemaker.command";
	private JsonMessageMakerCommandExecutor commandExecutor;

	@Override
	public void onEnable() {
		final ProxyServer proxy = getProxy();
		commandExecutor = new JsonMessageMakerCommandExecutor(new BungeePlatformAccess(proxy), false);

		final PluginManager pluginManager = proxy.getPluginManager();

		pluginManager.registerCommand(this, new BungeeCommandBridge(proxy, commandExecutor, "bjsonmessagemaker", JMM_COMMAND_PERMISSION));
		pluginManager.registerCommand(this, new BungeeCommandBridge(proxy, commandExecutor, "bjmm", JMM_COMMAND_PERMISSION));
		pluginManager.registerCommand(this, new BungeeCommandBridge(proxy, commandExecutor, "bjmsg", JMM_COMMAND_PERMISSION));

		pluginManager.registerListener(this, this);
	}

	@Override
	public void onDisable() {
		commandExecutor = null;
	}

	@EventHandler
	public void onPluginMessage(PluginMessageEvent event) {
		if ("BungeeCord".equals(event.getTag())) {
			final ByteArrayDataInput in = ByteStreams.newDataInput(event.getData());
			final String subChannel = in.readUTF();
			if ("JsonMessageMakerBungee".equals(subChannel)) {
				final String message = in.readUTF();
				System.out.println("Recieved message: " + message);
				commandExecutor.executeCommand(commandExecutor.getPlatformAccess().getConsole(), "bjsonmessagemaker", message.split(" "));
			}
		}
	}
}
