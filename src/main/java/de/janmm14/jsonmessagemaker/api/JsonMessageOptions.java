package de.janmm14.jsonmessagemaker.api;

@SuppressWarnings("unused")
/**
 * class for easy setting of options for and creation of {@link JsonMessageConverter} objects
 *
 *  * @since v1.0-SNAPSHOT
 * @author Janmm14
 */
public final class JsonMessageOptions {

	private boolean hover = true;
	private boolean suggest = true;
	private boolean run = true;
	private boolean link = true;

	/**
	 * @return the current state whether hover effect is allowed or not
	 */
	public boolean hover() {
		return hover;
	}

	/**
	 * @return the current state whether suggesting a command or chat text on click is allowed or not
	 */
	public boolean suggest() {
		return suggest;
	}

	/**
	 * @return the current state whether running a command or sending a chat message on click is allowed or not
	 */
	public boolean run() {
		return run;
	}

	/**
	 * @return the current state whether to embed links is allowed or not
	 */
	public boolean link() {
		return link;
	}

	/**
	 * set whether the hover effect is activated, default is {@code true}
	 * @param hover the new activity state of the hover option
	 * @return {@code this} object for chaining
	 */
	public JsonMessageOptions hover(boolean hover) {
		this.hover = hover;
		return this;
	}

	/**
	 * set whether the allowing to suggest a command or chat on click is activated, default is {@code true}
	 * @param suggest the new activity state of the suggest option
	 * @return {@code this} object for chaining
	 */
	public JsonMessageOptions suggest(boolean suggest) {
		this.suggest = suggest;
		return this;
	}

	/**
	 * set whether the allowing to run a command or chat on click is activated, default is {@code true}
	 * @param run the new activity state of the run option
	 * @return {@code this} object for chaining
	 */
	public JsonMessageOptions run(boolean run) {
		this.run = run;
		return this;
	}

	/**
	 * set whether the availability to embed a link is activated, default is {@code true}
	 * @param link the new activity state of the link option
	 * @return {@code this} object for chaining
	 */
	public JsonMessageOptions link(boolean link) {
		this.link = link;
		return this;
	}

	/**
	 * @return a newly created {@link JsonMessageConverter} if at least one option is changed from default, otherwise it returns {@link JsonMessageConverter#DEFAULT}
	 */
	public JsonMessageConverter create() {
		if (hover && run && suggest && link) {
			return JsonMessageConverter.DEFAULT;
		}
		return new JsonMessageConverter(hover, run, suggest, link);
	}
}
