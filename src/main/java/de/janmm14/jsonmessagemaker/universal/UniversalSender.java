package de.janmm14.jsonmessagemaker.universal;

import net.md_5.bungee.api.chat.BaseComponent;

public interface UniversalSender {

	String getName();

	boolean isConsole();

	boolean hasPermission(String permission);

	void sendMessage(String msg);

	void sendMessage(BaseComponent msg);

	void sendMessage(BaseComponent... msg);

}
