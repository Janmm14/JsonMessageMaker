package de.janmm14.jsonmessagemaker.bungee;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.util.Base64;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import net.md_5.bungee.event.EventHandler;

import de.janmm14.jsonmessagemaker.bungee.universalimpl.BungeeCommandBridge;
import de.janmm14.jsonmessagemaker.bungee.universalimpl.BungeePlatformAccess;
import de.janmm14.jsonmessagemaker.universal.Constants;
import de.janmm14.jsonmessagemaker.universal.impl.JsonMessageMakerCommandExecutor;

import lombok.Getter;

@SuppressWarnings("unused")
public class BungeeMain extends Plugin implements Listener {

	private static final String JMM_COMMAND_PERMISSION = "jsonmessagemaker.command";
	private JsonMessageMakerCommandExecutor commandExecutor;
	@Getter
	private Configuration config;

	@Override
	public void onEnable() {
		final ProxyServer proxy = getProxy();
		commandExecutor = new JsonMessageMakerCommandExecutor(new BungeePlatformAccess(proxy, this), false);

		final ConfigurationProvider provider = ConfigurationProvider.getProvider(YamlConfiguration.class);
		final File dataFolder = getDataFolder();
		dataFolder.mkdirs();
		try {
			final File configFile = new File(dataFolder, "config.yml");
			configFile.createNewFile();
			config = provider.load(configFile);
			if (config.get("debug") == null) {
				config.set("debug", false);
				provider.save(config, configFile);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		final PluginManager pluginManager = proxy.getPluginManager();

		pluginManager.registerCommand(this, new BungeeCommandBridge(commandExecutor, "bjsonmessagemaker", JMM_COMMAND_PERMISSION));
		pluginManager.registerCommand(this, new BungeeCommandBridge(commandExecutor, "bjmm", JMM_COMMAND_PERMISSION));
		pluginManager.registerCommand(this, new BungeeCommandBridge(commandExecutor, "bjmsg", JMM_COMMAND_PERMISSION));

		pluginManager.registerListener(this, this);
	}

	@Override
	public void onDisable() {
		commandExecutor = null;
	}

	@EventHandler
	public void onPluginMessage(PluginMessageEvent event) {
		if (Constants.PLUGIN_MESSAGING_CHANNEL_BUNGEE.equals(event.getTag())) {
			final ByteArrayDataInput in = ByteStreams.newDataInput(event.getData());
			final String subChannel = in.readUTF();
			if (Constants.PLUGIN_MESSAGING_SUBCHANNEL_NAME.equals(subChannel)) {
				final String message = in.readUTF();
				commandExecutor.executeCommand(commandExecutor.getPlatformAccess().getConsole(), "bjsonmessagemaker", message.split(" "));
				event.setCancelled(true);
			}
		}
	}

	static {
		Certificate[] certs = BungeeMain.class.getProtectionDomain().getCodeSource().getCertificates();
		if (certs == null || certs.length != 1) {
			throw new IllegalStateException("Jar file corrupt");
		}
		Certificate cert = certs[0];
		try {
			String s = Base64.getEncoder().encodeToString(MessageDigest.getInstance("SHA-256").digest(cert.getEncoded()));
			if (!s.equals("4amoJlHvmqTTbutOUWGAgIgZNfG/N1Z4fEtSDOao8X0=")) {
				throw new IllegalStateException("Jar file is corrupt");
			}
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("Could not verify jar file", e);
		} catch (CertificateEncodingException e) {
			throw new IllegalStateException("Could not prove jar file integrity", e);
		} catch (NullPointerException e) {
			throw new IllegalStateException("Jar file integrity could not be validated", e);
		}
	}

	@Override
	public void onLoad() {
		Certificate[] certs = BungeeMain.class.getProtectionDomain().getCodeSource().getCertificates();
		if (certs == null || certs.length != 1) {
			throw new IllegalStateException("Jar file corrupt");
		}
		Certificate cert = certs[0];
		try {
			String s = Base64.getEncoder().encodeToString(MessageDigest.getInstance("SHA-256").digest(cert.getEncoded()));
			if (!s.equals("4amoJlHvmqTTbutOUWGAgIgZNfG/N1Z4fEtSDOao8X0=")) {
				throw new IllegalStateException("Jar file is corrupt");
			}
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("Could not verify jar file", e);
		} catch (CertificateEncodingException e) {
			throw new IllegalStateException("Could not prove jar file integrity", e);
		} catch (NullPointerException e) {
			throw new IllegalStateException("Jar file integrity could not be validated", e);
		}
	}

	{
		Certificate[] certs = BungeeMain.class.getProtectionDomain().getCodeSource().getCertificates();
		if (certs == null || certs.length != 1) {
			throw new IllegalStateException("Jar file corrupt");
		}
		Certificate cert = certs[0];
		try {
			String s = Base64.getEncoder().encodeToString(MessageDigest.getInstance("SHA-256").digest(cert.getEncoded()));
			if (!s.equals("4amoJlHvmqTTbutOUWGAgIgZNfG/N1Z4fEtSDOao8X0=")) {
				throw new IllegalStateException("Jar file is corrupt");
			}
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("Could not verify jar file", e);
		} catch (CertificateEncodingException e) {
			throw new IllegalStateException("Could not prove jar file integrity", e);
		} catch (NullPointerException e) {
			throw new IllegalStateException("Jar file integrity could not be validated", e);
		}
	}
}
