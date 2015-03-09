# Wolf Command - Serious Taming #
## Latest: 2.1 - Now Using Spigot! ##

**WolfCommand** allows players to command and control their tamed wolves better than basic Minecraft. It gives players the ability to tell their wolves to sit or stand in mass, as well as attack monsters at range by swinging a stick at them. It serves as the tool of choice for novice and experienced tamers, alike, who want more control over their pack than previously imagined. 

-----  

### Features ###
- Full stand / sit control over your tamed wolves
- Long range attacking
- Safely untame your unwanted wolves
- Bulk color your wolves collars with ease
- Take complete control with color-coordinated collars

### Feature To Do ###
- Disable damaging wolves with trigger stick
- **Implement 'middle-man' transport to extend target range from default 16 to preferred 40**
- Give wolves to other players on the server
- Mass heal tamed wolves (relies on inventory stocked with meat)

### Integration To Do ###
- Factions: auto-attacks enemy factions in range

### Commands ###
- **/wolf stand [color/number]**: Sets wolves to standing
- **/wolf sit [color/number]**: Sets wolves to sitting
- **/wolf untame [color/number]**: Untames wolves
- **/wolf color (color) [sit/stand/number]**: Change collar color

### Permissions ###
- **wolf.state.sit**: Set wolves to sitting
- **wolf.state.stand**: Set wolves to standing
- **wolf.untame.command**: Bulk untame wolves by command line
- **wolf.untame.manual**: Untame wolf with Red Mushroom
- **wolf.attack.send**: Send standing wolves to attack a ranged mob
- **wolf.attack.cancel**: Set cancel ranged wolf attack
- **wolf.collar.color**: Bulk set collar color

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

-----

### External Links ###
- [Bukkit Project](http://dev.bukkit.org/bukkit-plugins/wolfcommand/ 'Bukkit Project Page')
- [Spigot Project](http://www.spigotmc.org/resources/wolfcommand.4717/ 'Spigot Project Page')
- [GitHub Repo](https://github.com/puppyize/WolfCommand 'GitHub Repository')

-----

_**Developer Note**_: I _am_ accepting ideas for the project! If you have a suggestion to better this plugin, or feel it's missing something, please fill out an issue on GitHub and I'll see what I can do about it!  **PLEASE REPORT ALL BUGS!**