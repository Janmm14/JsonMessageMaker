package de.janmm14.jsonmessagemaker.universal;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class UniversalCommandExecutor {

	@Getter
	private final PlatformAccess platformAccess;

	public abstract void executeCommand(UniversalSender sender, String alias, String[] args);
}
