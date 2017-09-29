package de.janmm14.jsonmessagemaker.universal.impl;

import java.util.ArrayList;
import java.util.Collection;

import net.md_5.bungee.api.chat.BaseComponent;

import de.janmm14.jsonmessagemaker.api.JsonMessageConverter;
import de.janmm14.jsonmessagemaker.universal.PlatformAccess;
import de.janmm14.jsonmessagemaker.universal.UniversalCommandExecutor;
import de.janmm14.jsonmessagemaker.universal.UniversalSender;

public class JsonMessageMakerCommandExecutor extends UniversalCommandExecutor {

	public JsonMessageMakerCommandExecutor(PlatformAccess platformAccess) {
		super(platformAccess);
	}

	@Override
	public void executeCommand(UniversalSender sender, String alias, String[] args) {
		if (args.length < 2) {
			sendHelp(sender, alias);
			return;
		}
		String[] target = args[0].split(":");
		if (target.length == 1) {
			UniversalSender player = getPlayerOrSendError(sender, target[0]);
			if (player == null) {
				return;
			}
			player.sendMessage(JsonMessageConverter.DEFAULT.convert(joinArgs(args, 1)));

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
			BaseComponent[] convertedMessage = JsonMessageConverter.DEFAULT.convert(joinArgs(args, 1));

			for (UniversalSender reciever : recievers) {
				reciever.sendMessage(convertedMessage);
			}
		} else {
			sender.sendMessage("§4Unknown reciever §6" + args[0]);
		}
	}

	private UniversalSender getPlayerOrSendError(UniversalSender sender, String plrName) {
		UniversalSender player = getPlatformAccess().getPlayer(plrName);
		if (player == null) {
			sender.sendMessage("§4Player §6" + plrName + "§4 not found");
			return null;
		}
		return player;
	}

	private static void sendHelp(UniversalSender sender, String alias) {
		sender.sendMessage("§5 §m============§6 JsonMessageMaker §eHelp §5§m============");
		sender.sendMessage("§c/" + alias + " <player> <jmessage>§7 - §6Sends the jmessage to the given player");
		sender.sendMessage("§c/" + alias + " perm:<permission> <jmessage>§7 - §6Sends the jmessage to the given player");
		sender.sendMessage("§c/" + alias + " :all <jmessage>§7 - §6Broadcasts a jmessage to all players");
	}

	private static String joinArgs(String[] args, int start) {
		StringBuilder sb = new StringBuilder();
		for (int i = start; i < args.length; i++) {
			sb.append(args[i]).append(' ');
		}
		return sb.substring(0, sb.length() - 1);
	}
}
