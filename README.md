# JsonMessageMaker [![Build Status](https://ci.janmm14.de/job/public~jsonmessagemaker/badge/icon)](https://ci.janmm14.de/job/public~jsonmessagemaker)

A plugin for the minecraft server implementation [Spigot](https://www.spigotmc.org/) and the minecraft proxy [BungeeCord](https://github.com/SpigotMC/BungeeCord), with an API for other plugins.

Minecraft chat messages support various hover and click effects. However this feature is really inaccessible for many people, because it requires to write messages in json format, making them huge, confusing and hard to edit again. There are message generator websites out there, but they are not that often used, because copy-pasting a config full of messages is something people do not want to do.

Many plugin authors and server administrators still use the old minecraft chat messages with color and formatting codes, because it is handy to use, easy to remember and does not generate much overhead.

## JMessage format

JsonMessageMaker allows people to use the old minecraft chat message format with the new features with a simple format to add basic hover and click effects.

The format to add any effects is `[jmm|*options*]chat text[/jmm]`

Option | Action | Description
------ | --------------- | -----------
`suggest=<suggestion>` | Click | `<suggestion>` will appear in the player chat text feld
`run=<text>` | Click | Player will send `<text>`, commands are possible too
`link=<url>` | Click | Player will be asked if he wants to open `<url>` in a browser
`hover=<text>` | Hover | `<text>` will appear above the cursor if hovered by the mouse

Example input | Example output
------------- | --------------
`§aasdf [jmm\|suggest=qwerta]§bqwertzuiop[/jmm]` | *coming soon*
`[jmm\|hover=runs /list\|jmm\|run=/list]§erun §c/list[/jmm]` | *coming soon*
`[jmm\|hover=opens google\|jmm\|link=http://www.google.de]google§blink[/jmm]` | *coming soon*

## Command

This plugin provides an own command to send jmessages to players.

Command: `/jsonmessagemaker`
Command Aliases: `/jmm`, `/jmsg`
Permission: `jsonmessagemaker.command`

Command Syntax | Description
-------------- | -----------
`/jmsg <player> <jmessage>` | Sends the jmessage to the given player
`/jmsg perm:<permission> <jmessage>` | Sends the jmessage to all players with the given permission
`/jmsg :all <jmessage>` | Broadcasts the jmessage to all players

## Downloads

### SpigotMC resource

All release versions will be uploaded to this spigotmc resource: https://www.spigotmc.org/resources/jsonmessagemaker.7938/

## Development versions

You can download indev versions from my jenkins server: https://ci.janmm14.de/job/pubic~JsonMessageMaker/

