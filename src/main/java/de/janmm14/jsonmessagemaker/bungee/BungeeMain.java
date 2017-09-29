package de.janmm14.jsonmessagemaker.bungee;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

import de.janmm14.jsonmessagemaker.bungee.universalimpl.BungeeCommandBridge;
import de.janmm14.jsonmessagemaker.bungee.universalimpl.BungeePlatformAccess;
import de.janmm14.jsonmessagemaker.universal.impl.JsonMessageMakerCommandExecutor;

@SuppressWarnings("unused")
public class BungeeMain extends Plugin {

	private static final String JMM_COMMAND_PERMISSION = "jsonmessagemaker.command";

	@Override
	public void onEnable() {
		ProxyServer proxy = getProxy();
		JsonMessageMakerCommandExecutor commandExecutor = new JsonMessageMakerCommandExecutor(new BungeePlatformAccess(proxy));
		proxy.getPluginManager().registerCommand(this, new BungeeCommandBridge(proxy, commandExecutor, "bjsonmessagemaker", JMM_COMMAND_PERMISSION));
		proxy.getPluginManager().registerCommand(this, new BungeeCommandBridge(proxy, commandExecutor, "bjmm", JMM_COMMAND_PERMISSION));
		proxy.getPluginManager().registerCommand(this, new BungeeCommandBridge(proxy, commandExecutor, "bjmsg", JMM_COMMAND_PERMISSION));
	}
}
