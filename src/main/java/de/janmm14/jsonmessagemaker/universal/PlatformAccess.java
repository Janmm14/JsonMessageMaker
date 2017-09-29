package de.janmm14.jsonmessagemaker.universal;

import java.util.Collection;

public interface PlatformAccess {

	Collection<UniversalSender> getPlayers();

	UniversalSender getPlayer(String name);

	UniversalSender getConsole();
}
