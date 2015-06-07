package de.janmm14.jsonmessagemaker.api;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.junit.Assert;
import org.junit.Test;

public class JsonMessageConverterTest {

    @Test
    public void test() {
        final BaseComponent[] components = JsonMessageConverter.DEFAULT.convert("§aasdf [jmm|hover=suggests qwerta|jmm|suggest=qwerta]§bqwertzuiop[/jmm] " +
                "[jmm|hover=runs /list|jmm|run=/list]§erun §c/list[/jmm] " +
                "[jmm|hover=opens google|jmm|link=http://www.google.de]google§blink[/jmm]");
        Assert.assertEquals("conversion not working correctly",
                "{\"extra\":[{\"color\":\"green\",\"text\":\"asdf \"},{\"extra\":[{\"color\":\"aqua\",\"text\":\"qwertzuiop\"}],\"clickEvent\":{\"action\":" +
                        "\"suggest_command\",\"value\":\"qwerta\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":[\"suggests qwerta\"]},\"text\":\"\"},\"" +
                        " \",{\"extra\":[{\"color\":\"yellow\",\"text\":\"run \"},{\"color\":\"red\",\"text\":\"/list\"}],\"clickEvent\":" +
                        "{\"action\":\"run_command\",\"value\":\"/list\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":[\"runs /list\"]},\"text\":\"\"}," +
                        "\" \",{\"extra\":[\"google\",{\"color\":\"aqua\",\"text\":\"link\"}],\"clickEvent\":{\"action\":\"open_url\",\"value\":\"http://www.google.de\"}," +
                        "\"hoverEvent\":{\"action\":\"show_text\",\"value\":[\"opens google\"]},\"text\":\"\"}],\"text\":\"\"}",
                ComponentSerializer.toString(components));
    }
}
