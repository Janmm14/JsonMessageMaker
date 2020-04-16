package de.janmm14.jsonmessagemaker.universal.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;

import de.janmm14.jsonmessagemaker.api.JsonMessageConverter;
import de.janmm14.jsonmessagemaker.bungee.universalimpl.BungeePlatformAccess;
import de.janmm14.jsonmessagemaker.universal.PlatformAccess;
import de.janmm14.jsonmessagemaker.universal.UniversalCommandExecutor;
import de.janmm14.jsonmessagemaker.universal.UniversalSender;

public class JsonMessageMakerCommandExecutor extends UniversalCommandExecutor {

	private static final Pattern BUNGEE_PLAYER_SERVER_PLACEHOLDER_PATTERN = Pattern.compile(Pattern.quote("(((serverof:") + "(.+)" + Pattern.quote(")))"),
			Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	private final boolean sendToBungeeOption;

	public JsonMessageMakerCommandExecutor(PlatformAccess platformAccess, boolean sendToBungeeOption) {
		super(platformAccess);
		this.sendToBungeeOption = sendToBungeeOption;
	}

	@Override
	public void executeCommand(UniversalSender sender, String alias, String[] args) {
		if (args.length < 2) {
			sendHelp(sender, alias);
			return;
		}
		final String[] target = args[0].split(":");
		if (target.length > 1 && target[0].isEmpty() && target[1].equals("bungee")) {
			if (!sendToBungeeOption) {
				sender.sendMessage("§6sendToBungeeOption§4 not enabled in JsonMessageMaker/config.yml §7(spigot-only option)");
				return;
			}
			final String toSend = args[0].substring(":bungee:".length()) + ' ' + joinArgs(args, 1);
			if (!getPlatformAccess().sendPluginMessage(toSend)) {
				sender.sendMessage("§4Could not send plugin message.");
			}
			return;
		}
		if (target.length == 1) {
			final UniversalSender player = getPlayerOrSendError(sender, target[0]);
			if (player == null) {
				return;
			}
			final BaseComponent[] message = translateAmpColorCharAndConvertMessage(joinArgs(args, 1));
			player.sendMessage(message);

		} else if (target.length == 2) {
			Collection<UniversalSender> recievers = new ArrayList<>();
			switch (target[0]) {
				case "perm": {
					String perm = target[1];
					for (UniversalSender plr : getPlatformAccess().getPlayers()) {
						if (plr.hasPermission(perm)) {
							recievers.add(plr);
						}
					}
					break;
				}
				case "": {
					if (target[1].equals("all")) {
						recievers = getPlatformAccess().getPlayers();
					}
					break;
				}
				default:
					sender.sendMessage("§4Unknown reciever §6" + args[0]);
					return;
			}
			final BaseComponent[] convertedMessage = translateAmpColorCharAndConvertMessage(joinArgs(args, 1));

			for (final UniversalSender reciever : recievers) {
				reciever.sendMessage(convertedMessage);
			}
		} else {
			sender.sendMessage("§4Unknown reciever §6" + args[0]);
		}
	}

	private BaseComponent[] translateAmpColorCharAndConvertMessage(String message) {
		return JsonMessageConverter.DEFAULT.convert(convertBungeePlayerServerPlaceholder(ChatColor.translateAlternateColorCodes('&', message)));
	}

	private UniversalSender getPlayerOrSendError(UniversalSender sender, String targetPlrName) {
		final UniversalSender player = getPlatformAccess().getPlayer(targetPlrName);
		if (player == null) {
			sender.sendMessage("§4Player §6" + targetPlrName + "§4 not found");
			return null;
		}
		return player;
	}

	private String convertBungeePlayerServerPlaceholder(String input) {
		if (!(getPlatformAccess() instanceof BungeePlatformAccess)) {
			return input;
		}
		final Matcher matcher = BUNGEE_PLAYER_SERVER_PLACEHOLDER_PATTERN.matcher(input);
		final StringBuffer sb = new StringBuffer(input.length());
		while (matcher.find()) {
			final String in = matcher.group(1);
			UniversalSender user = null;
			if (in.length() == 8 + 1 + 4 + 1 + 4 + 1 + 4 + 1 + 12) { //length of dashed uuid
				try {
					user = getPlatformAccess().getPlayer(UUID.fromString(in));
				} catch (IllegalArgumentException ex) {
					ex.printStackTrace();
				}
			} else if (in.length() == 8 + 4 + 4 + 4 + 12) { //length of not dashed uuid
				try {
					user = getPlatformAccess().getPlayer(getUUIDFromNonDashedString(in));
				} catch (IllegalArgumentException ex) {
					ex.printStackTrace();
				}
			} else {
				user = getPlatformAccess().getPlayer(in);
			}
			if (user != null) {
				final String bungeeServerName = user.getBungeeServerName();
				if (bungeeServerName != null) {
					matcher.appendReplacement(sb, bungeeServerName);
				} else {
					if (getPlatformAccess().isDebug()) {
						getPlatformAccess().getLogger().warning("Could not find server name for " + user.getName() + ", definion in msg: " + in + ", message " + input);
					}
					matcher.appendReplacement(sb, in);
				}
			} else {
				if (getPlatformAccess().isDebug()) {
					getPlatformAccess().getLogger().warning("Could not find user: " + in + " in message: " + input);
				}
				matcher.appendReplacement(sb, in);
			}
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	private static void sendHelp(UniversalSender sender, String alias) {
		sender.sendMessage("§5 §m============§6 JsonMessageMaker §eHelp §5§m============");
		sender.sendMessage("§c/" + alias + " <player> <jmessage>§7 - §6Sends the jmessage to the given player");
		sender.sendMessage("§c/" + alias + " perm:<permission> <jmessage>§7 - §6Sends the jmessage to all players with the given permission");
		sender.sendMessage("§c/" + alias + " :all <jmessage>§7 - §6Broadcasts the jmessage to all players");
		sender.sendMessage("§c/" + alias + " :bungee:<player> <jmessage>§7 - §6Sends the jmessage to the given player somewhere on your bungee");
		sender.sendMessage("§c/" + alias + " :bungee:perm:<permission> <jmessage>§7 - §6Sends the jmessage to all players on bungee with the given permission");
		sender.sendMessage("§c/" + alias + " :bungee::all <jmessage>§7 - §6Broadcasts the jmessage to all players on bungee");
	}

	private static String joinArgs(String[] args, int start) {
		final StringBuilder sb = new StringBuilder();
		for (int i = start; i < args.length; i++) {
			sb.append(args[i]).append(' ');
		}
		return sb.substring(0, sb.length() - 1);
	}

	private static UUID getUUIDFromNonDashedString(String uuid) {
		return UUID.fromString(uuid.substring(0, 8) + '-' + uuid.substring(8, 12) + '-' + uuid.substring(12, 16) + '-' + uuid.substring(16, 20) + '-' + uuid.substring(20, 32));
	}
}
