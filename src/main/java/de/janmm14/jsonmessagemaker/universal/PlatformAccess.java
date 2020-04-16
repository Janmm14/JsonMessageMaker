package de.janmm14.jsonmessagemaker.universal;

import java.util.Collection;
import java.util.UUID;
import java.util.logging.Logger;

public interface PlatformAccess {

	Collection<UniversalSender> getPlayers();

	UniversalSender getPlayer(String name);

	UniversalSender getPlayer(UUID uuid);

	UniversalSender getConsole();

	boolean sendPluginMessage(String command);
	
	boolean isDebug();
	
	Logger getLogger();
}
