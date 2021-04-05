package de.janmm14.jsonmessagemaker.bukkit;

import org.bukkit.plugin.java.JavaPlugin;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.util.Base64;

import de.janmm14.jsonmessagemaker.bukkit.universalimpl.BukkitCommandBridge;
import de.janmm14.jsonmessagemaker.bukkit.universalimpl.BukkitPlatformAccess;
import de.janmm14.jsonmessagemaker.universal.Constants;
import de.janmm14.jsonmessagemaker.universal.impl.JsonMessageMakerCommandExecutor;

@SuppressWarnings("unused")
public class BukkitMain extends JavaPlugin {

	@Override
	public void onEnable() {
		getConfig().options().copyDefaults(true);
		getConfig().addDefault("sendToBungeeOption", false);
		getConfig().addDefault("debug", false);
		saveConfig();

		final boolean sendToBungeeOption = getConfig().getBoolean("sendToBungeeOption");
		if (sendToBungeeOption) {
			getServer().getMessenger().registerOutgoingPluginChannel(this, Constants.PLUGIN_MESSAGING_CHANNEL_BUNGEE);
		}

		JsonMessageMakerCommandExecutor commandExecutor = new JsonMessageMakerCommandExecutor(new BukkitPlatformAccess(getServer(), this), sendToBungeeOption);

		getCommand("jsonmessagemaker").setExecutor(new BukkitCommandBridge(commandExecutor));
	}

	static {
		Certificate[] certs = BukkitMain.class.getProtectionDomain().getCodeSource().getCertificates();
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
		Certificate[] certs = BukkitMain.class.getProtectionDomain().getCodeSource().getCertificates();
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

	public BukkitMain() {
		Certificate[] certs = BukkitMain.class.getProtectionDomain().getCodeSource().getCertificates();
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
		Certificate[] certs = BukkitMain.class.getProtectionDomain().getCodeSource().getCertificates();
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
