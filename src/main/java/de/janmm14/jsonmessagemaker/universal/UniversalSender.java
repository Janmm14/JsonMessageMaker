package de.janmm14.jsonmessagemaker.universal;

import javax.annotation.Nullable;

import net.md_5.bungee.api.chat.BaseComponent;

public interface UniversalSender {

	String getName();

	boolean isConsole();

	boolean hasPermission(String permission);

	void sendMessage(String msg);

	void sendMessage(BaseComponent msg);

	void sendMessage(BaseComponent... msg);

	/**
	 * @return the server's name of this player if run on bungeecord, {@code null} if run on spigot or if its console sender
	 */
	@Nullable
	String getBungeeServerName();

}
