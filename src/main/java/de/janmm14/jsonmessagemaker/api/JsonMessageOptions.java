package de.janmm14.jsonmessagemaker.api;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Class to set options for and create {@link JsonMessageConverter} objects<br>
 * <br>
 * Can also be created with {@link JsonMessageConverter#options()}
 *
 * @author Janmm14
 * @see #hover
 * @see #suggest
 * @see #run
 * @see #link
 * @see #translateAmp
 * @see #mineDownSupport
 * @see #create()
 * @since v1.0-SNAPSHOT
 */
@Getter
@Setter
@Accessors(fluent = true, chain = true)
@SuppressWarnings("unused")
public final class JsonMessageOptions {

	/**
	 * whether the hover effect is activated, default is {@code true}
	 */
	private boolean hover = true;
	/**
	 * whether suggesting a command or chat text on click is allowed, default is {@code true}
	 */
	private boolean suggest = true;
	/**
	 * whether running a command or sending a chat message on click is allowed, default is {@code true}
	 */
	private boolean run = true;
	/**
	 * whether embed links is allowed, default is {@code true}
	 */
	private boolean link = true;
	/**
	 * whether color codes with {@code &} will be translated, default is {@code true}
	 */
	private boolean translateAmp = true;
	/**
	 * whether <a href="https://github.com/Phoenix616/MineDown">MineDown format</a> is supported, default is {@code true}<br>
	 * MineDown format must start with {@code [md]}, if minedown format is detected, jmm format will not be parsed and the other options are ignored!
	 */
	private boolean mineDownSupport = true;

	/**
	 * @return a newly created {@link JsonMessageConverter} if at least one option is changed from default, otherwise it returns {@link JsonMessageConverter#DEFAULT}
	 */
	public JsonMessageConverter create() {
		if (hover && run && suggest && link && translateAmp && mineDownSupport) {
			return JsonMessageConverter.DEFAULT;
		}
		return new JsonMessageConverter(hover, run, suggest, link, translateAmp, mineDownSupport);
	}
}
