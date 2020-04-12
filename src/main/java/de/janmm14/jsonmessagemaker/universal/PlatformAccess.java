package de.janmm14.jsonmessagemaker.universal;

import java.util.Collection;
import java.util.UUID;

public interface PlatformAccess {

	Collection<UniversalSender> getPlayers();

	UniversalSender getPlayer(String name);

	UniversalSender getPlayer(UUID uuid);

	UniversalSender getConsole();

	boolean sendPluginMessage(String command);
}
