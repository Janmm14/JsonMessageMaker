# JsonMessageMaker [![Build Status](https://s.janmm14.de/jsonmessagemakerbuildstatus)](https://s.janmm14.de/jsonmessagemakerci)

A plugin for the minecraft server implementation [Spigot](https://www.spigotmc.org/) and the minecraft proxy [BungeeCord](https://github.com/SpigotMC/BungeeCord), with an API for other plugins.

Minecraft chat messages support various hover and click effects. However this feature is really inaccessible for many people, because it requires to write messages in json format, making them huge, confusing and hard to edit again. There are message generator websites out there, but they are not that often used, because copy-pasting a config full of messages is something people do not want to do.

Many plugin authors and server administrators still use the old minecraft chat messages with color and formatting codes, because it is handy to use, easy to remember and does not generate much overhead.

## JMessage format

JsonMessageMaker allows people to use the old minecraft chat message format with the new features with a simple format to add basic hover and click effects.

The format to add any effects to a part of a chat text is `[jmm|*options*]chat text[/jmm]`

Option | Action | Description
------ | ------ | -----------
`suggest=<suggestion>` | Click | `<suggestion>` will appear in the player chat text feld
`run=<text>` | Click | Player will send `<text>`, commands are possible too
`link=<url>` | Click | Player will be asked if he wants to open `<url>` in a browser
`hover=<text>` | Hover | `<text>` will appear above the cursor if hovered by the mouse

To add multiple options for chat text use `|jmm|` as deliminter. Please note that you can of course just add one possible option per action.

Here are some example JMessages:

- `§aasdf [jmm|suggest=qwerta]§bqwertzuiop[/jmm]`
- `[jmm|hover=runs /list|jmm|run=/list]§erun §c/list[/jmm]`
- `[jmm|hover=opens google|jmm|link=http://www.google.de]google§blink[/jmm]`

## API

The base feature of JsonMessageMaker is to provide an API which other plugins can use to convert for example configuration strings using the JMessage format to an array of BaseComponents, ready to get sent via the spigot or bungee api.

Baisc API usage:

```java
BaseComponent[] components = JsonMessageConverter.DEFAULT.convert(yourJMessage);
```

The API provides the option to disable certain features of the jmessage format. The default converter (see above) has all features enabled. If you want to havecertain features disabled, you need to ccreate your own JsonMessageConverter through the JsonMesssageOption class.

```java
JsonMessageConverter converter = new JsonMessageOptions().suggest(true).run(true).link(true).hover(true).create();
```

## Command

Since v2.0 this plugin additionally provides an own command to send JMessages to players.

Command Option | Description
-------------- | -----------
`/<command> <player> <jmessage>` | Sends the JMessage to the given player
`/<command> perm:<permission> <jmessage>` | Sends the JMessage to all players with the given permission
`/<command> :all <jmessage>` | Broadcasts the JMessage to all players

### Spigot
Command: `/jsonmessagemaker`

Command Aliases: `/jmm`, `/jmsg`

Permission: `jsonmessagemaker.command`

### BungeeCord
Command: `/bjsonmessagemaker`

Command Aliases: `/bjmm`, `/bjmsg`

Permission: `jsonmessagemaker.command`

## Downloads

### SpigotMC resource

All release versions will be uploaded to this spigotmc resource: https://www.spigotmc.org/resources/jsonmessagemaker.7938/

### Development versions

You can download indev versions from my jenkins server: https://s.janmm14.de/jsonmessagemakerci

### Maven repository

JsonMessageMaker is deployed to my own maven repository:

```xml
    <repositories>
        <repository>
            <id>janmm14-public</id>
            <url>https://repo.janmm14.de/artifactory/public/</url>
        </repository>
    </repositories>
```
```xml
    <dependencies>
        <dependency>
            <groupId>de.janmm14</groupId>
            <artifactId>jsonmessagemaker</artifactId>
            <version>3.1.0</version>
        </dependency>
    </dependencies>
```
