package de.janmm14.jsonmessagemaker.api;

import de.themoep.minedown.MineDown;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Basic class for conversion of strings to JsonMessages<br>
 * Class contains options of enabled features, for more information about these see {@link JsonMessageOptions}<br>
 * Object creation is handled by {@link JsonMessageOptions}
 *
 * @author Janmm14
 * @since v1.0-SNAPSHOT
 * @see #convert(String) 
 */
@Getter
@SuppressWarnings("unused")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public final class JsonMessageConverter {

	/**
	 * the converter with the default options, for more information see {@link JsonMessageOptions}
	 */
	public static final JsonMessageConverter DEFAULT = new JsonMessageConverter(true, true, true, true, true, true);
	private static final Pattern JMM_PATTERN = Pattern.compile("\\[jmm\\|(.+?)\\](.+?)\\[\\/jmm\\]", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
	private static final Pattern JMM_ARG_SPLIT_PATTERN = Pattern.compile("|jmm|", Pattern.CASE_INSENSITIVE | Pattern.LITERAL);

	/**
	 * whether the hover effect is activated, default is {@code true}
	 */
	private final boolean hover;
	/**
	 * whether suggesting a command or chat text on click is allowed, default is {@code true}
	 */
	private final boolean run;
	/**
	 * whether running a command or sending a chat message on click is allowed, default is {@code true}
	 */
	private final boolean suggest;
	/**
	 * whether embed links is allowed, default is {@code true}
	 */
	private final boolean link;
	/**
	 * whether color codes with {@code &} will be translated, default is {@code true}
	 */
	private final boolean translateAmp;
	/**
	 * whether <a href="https://github.com/Phoenix616/MineDown">MineDown format</a> is supported, default is {@code true}<br>
	 * MineDown format must start with {@code [md]}, if minedown format is detected, jmm format will not be parsed and the other options are ignored!
	 */
	private final boolean mineDownSupport;

	/**
	 * Converts strings to a json message using net.md-5:bungeecord-chat and uses the set options
	 *
	 * @param input the input string, the format of the conversion parts is available in README.md
	 * @return the converted message as {@link BaseComponent}
	 */
	@NonNull
	public BaseComponent[] convert(@NonNull String input) {
		input = input.replace("\\n", "\n");
		if (mineDownSupport && input.length() > 4 && input.charAt(0) == '[' && input.charAt(3) == ']') {
			char c1 = input.charAt(1);
			char c2 = input.charAt(2);
			if ((c1 == 'm' || c1 == 'M') && (c2 == 'd' || c2 == 'D')) {
				return MineDown.parse(input.substring(4));
			}
		}
		if (translateAmp) {
			input = ChatColor.translateAlternateColorCodes('&', input);
		}
		List<BaseComponent> components = new ArrayList<>();
		final Matcher matcher = JMM_PATTERN.matcher(input);
		int lastEnd = 0;
		while (matcher.find()) {
			final String argsStr = matcher.group(1);
			final String text = matcher.group(2);
			final String before = input.substring(lastEnd, matcher.start());
			components.addAll(Arrays.asList(TextComponent.fromLegacyText(before)));
			final String[] args = JMM_ARG_SPLIT_PATTERN.split(argsStr);
			final TextComponent txt = new TextComponent(TextComponent.fromLegacyText(text));
			for (String arg : args) {
				final int i = arg.indexOf('=');
				final String opt = arg.substring(0, i).toLowerCase();
				final String val = arg.substring(i + 1);

				switch (opt) {
					case "hover":
						if (hover) {
							txt.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(val)));
						}
						break;
					case "suggest":
						if (suggest) {
							txt.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, val));
						}
						break;
					case "run":
						if (run) {
							txt.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, val));
						}
						break;
					case "link":
						if (link) {
							txt.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, val));
						}
						break;
				}
			}
			components.add(txt);

			lastEnd = matcher.end();
		}
		if (lastEnd < (input.length() - 1)) {
			final String after = input.substring(lastEnd);
			components.addAll(Arrays.asList(TextComponent.fromLegacyText(after)));
		}
		return components.toArray(new BaseComponent[0]);
	}

	/**
	 * Returns a new {@linkplain JsonMessageOptions} instance
	 */
	public static JsonMessageOptions options() {
		return new JsonMessageOptions();
	}
}
