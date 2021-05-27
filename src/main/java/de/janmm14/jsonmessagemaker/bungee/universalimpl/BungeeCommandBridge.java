package de.janmm14.jsonmessagemaker.bungee.universalimpl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.util.Base64;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

import de.janmm14.jsonmessagemaker.bungee.BungeeMain;
import de.janmm14.jsonmessagemaker.universal.UniversalCommandExecutor;
import de.janmm14.jsonmessagemaker.universal.UniversalSender;

public class BungeeCommandBridge extends Command {

	private final UniversalCommandExecutor commandExecutor;

	public BungeeCommandBridge(UniversalCommandExecutor commandExecutor, String cmdName, String permission, String... aliases) {
		super(cmdName, permission, aliases);
		this.commandExecutor = commandExecutor;
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
	public void execute(CommandSender cs, String[] args) {
		UniversalSender sender = new BungeeCommandSender(cs);
		commandExecutor.executeCommand(sender, getName(), args);
	}
}
