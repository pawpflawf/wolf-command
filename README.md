# Wolf Command - Serious Taming #
## Latest: 2.4 - Spigot 1.8.3 ##

**WolfCommand** allows players to command and control their tamed wolves better than basic Minecraft. It gives players the ability to tell their wolves to sit or stand in mass, as well as attack monsters at range by swinging a stick at them. It serves as the tool of choice for novice and experienced tamers, alike, who want more control over their pack than previously imagined. 

**WolfCommand** now takes advantage of MCStats for plugin statistical purposes. Statistical information retrieved from this tool is exclusively used by the developer to understand how the plugin is implemented and used 'in the wild' (a.k.a. production). Easy opt-out instructions are defined below! :D  

Previous CB releases (earlier than 1.8.3) are available (by request) for servers not yet updated. If you are in need of an earlier version, let me know (<wolfcommand@puppyize.me>) and I'll build one and send it out as soon as I can. :3    

-----  

### Features ###
- Full stand / sit control over your tamed wolves
- Long range attacking
- Safely untame your unwanted wolves
- Bulk color your wolves collars with ease
- Take complete control with color-coordinated collars
- Send your wolves to others, either strategically or as barter
- Limit maximum number of wolves players can tame

### Feature To Do ###
- Disable damaging wolves with trigger stick
- Mass heal your tamed wolves (inventory use is permission based)
- **Implement 'middle-man' transport to extend target range from default 16 to preferred 40**
- Statistics on currently tamed wolves (# of Adults/Pups | Collar distribution | Other...)

### Integration To Do ###
- Factions: auto-attacks enemy factions in range

### Commands ###
- **/wolf stand [color/number]**: Sets wolves to standing
- **/wolf sit [color/number]**: Sets wolves to sitting
- **/wolf untame [color/number]**: Untames wolves
- **/wolf color (color) [sit/stand/number]**: Change collar color
- **/wolf send (PlayerName) [sit/stand/number]**: Give wolves to another Player

### Permissions ###
- **wolf.state.sit**: Set wolves to sitting
- **wolf.state.stand**: Set wolves to standing
- **wolf.untame.command**: Bulk untame wolves by command line
- **wolf.untame.manual**: Untame wolf with Red Mushroom
- **wolf.attack.send**: Send standing wolves to attack a ranged mob
- **wolf.attack.cancel**: Set cancel ranged wolf attack
- **wolf.collar.color**: Bulk set collar color
- **wolf.send.command**: Give wolves away

### Grouped Permissions ###
- **wolf.state**: Change wolves state (Equivalent to **wolf.state.sit** & **wolf.state.stand**)
- **wolf.untame**: Untame wolves (Equivalent to **wolf.untame.command** & **wolf.untame.manual**)
- **wolf.attack**: Send wolves to attack (Equivalent to **wolf.attack.send** & **wolf.attack.cancel**)

-----

### Long Range Attacking ###
- Left clicking a mob within 40 blocks, with a stick, will set that mob as the active target for all standing tamed wolves!
- Right clicking will cancel active targeting for all wolves

### Safe Untaming ###
- Feeding a tamed wolf a **Red Mushroom** will safely untame it
- When using the **untame** command, if you provide a *negative* number, all wolves but that number will be untamed 

### Bulk Color Collars ###
- When no *optional specifier* is provided, default is all personally tamed wolves
- If a number is provided, command will attempt to color *number* **more** wolf collars
- Supports bulk coloring sitting or standing wolves, also

### Give Away Your Wolves ###
- You can give up your wolves to other players currently on the server
- Can be used for trade or stategic PvP purposes... the choice is yours
- Give off sitting, standing, a specified number
- Giving based off color coming soon

### Wolf Taming Limit ###
- Stops unfair players who tame more wolves than they should be allowed
- Setting config.yml `PLAYER_MAX_WOLF` to '-1' removes limit
- Defaults limit to 100 wolves
- Ops are exempt from limit

### Wolf Taming Limit ###
- Stops unfair players who tame more wolves than they should be allowed
- Setting config.yml `PLAYER_MAX_WOLF` to '-1' removes limit
- Defaults limit to 100 wolves
- Ops are exempt from limit

-----

### MCStats / Metrics ###

** Disabling Metrics **
The file ../plugins/Plugin Metrics/config.yml contains an option to opt-out  

The following data is **read** from the server in some way or another  

- File Contents of plugins/Plugin Metrics/config.yml (created if not existent)
- Players currently online (not max player count)
- Server version string (the same version string you see in /version)
- Plugin version of the metrics-supported plugin

The following data is **sent** to http://mcstats.org and can be seen under [http://mcstats.org/plugin/WolfCommand](http://mcstats.org/plugin/WolfCommand 'MCStats Page')

- Metrics revision of the implementing class
- Server's GUID
- Players currently online (not max player count)
- Server version string (the same version string you see in /version)
- Plugin version of the metrics-supported plugin

-----

### External Links ###
- [MCStats](http://mcstats.org/plugin/WolfCommand 'MCStats Details')
- [Bukkit Project](http://dev.bukkit.org/bukkit-plugins/wolfcommand/ 'Bukkit Project Page')
- [Spigot Project](http://www.spigotmc.org/resources/wolfcommand.4717/ 'Spigot Project Page')
- [GitHub Repo](https://github.com/puppyize/WolfCommand 'GitHub Repository')

-----

_**Developer Note**_: I _am_ accepting ideas for the project! If you have a suggestion to better this plugin, or feel it's missing something, please fill out an issue on GitHub and I'll see what I can do about it!  **PLEASE REPORT ALL BUGS!**
