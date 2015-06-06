package de.janmm14.jsonmessagemaker.api;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.chat.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class JsonMessageConverter {

    public static final JsonMessageConverter DEFAULT = new JsonMessageConverter(true, true, true, true);
    private static final Pattern JMM_PATTERN = Pattern.compile("\\[jmm\\|(.+?)\\](.+?)\\[\\/jmm\\]", Pattern.CASE_INSENSITIVE);
    private static final Pattern ARG_SPLIT_PATTERN = Pattern.compile("\\|jmm\\|", Pattern.CASE_INSENSITIVE);

    private final boolean hover;
    private final boolean run;
    private final boolean suggest;
    private final boolean link;


    @NonNull
    public BaseComponent[] convert(@NonNull String input) {
        List<BaseComponent> components = new ArrayList<>();
        final Matcher matcher = JMM_PATTERN.matcher(input);
        int lastEnd = 0;
        while (matcher.find()) {
            final String argsStr = matcher.group(1);
            final String text = matcher.group(2);
            final String before = input.substring(lastEnd, matcher.start());
            components.addAll(Arrays.asList(TextComponent.fromLegacyText(before)));
            final String[] args = ARG_SPLIT_PATTERN.split(argsStr);
            final TextComponent txt = new TextComponent(TextComponent.fromLegacyText(text));
            for (String arg : args) {
                final int i = arg.indexOf('=');
                final String opt = arg.substring(0, i).toLowerCase();
                final String val = arg.substring(i + 1, arg.length());

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
        return components.toArray(new BaseComponent[components.size()]);
    }
}
