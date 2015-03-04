# Wolf Command - Serious Taming #
## Latest: 1.12 ##

This plugin allows users to command their wolves better than basic Minecraft. It gives players the ability to tell all their wolves to sit/stand as well as attack monsters at range by swinging a stick at them. 
When left-click with a stick at a monster within 40 blocks, your wolves will target the selected mob and start attacking. Right clicking will cancel the targeting.   

*Due to the nature and future of Bukkit, future __WolfCommand__ releases will be ported to [Sponge](https://spongepowered.org/)*.  
*Should the legalities with Bukkit change, we'll open a split branch to continue Bukkit development*

-----  

### Features ###
- Full stand / sit control over your tamed wolves
- Long range attacking
- Safely untame your unwanted wolves

### Feature To Do ###
- Disable damaging wolves with trigger stick
- Mass change wolf collar color (option for sitting / standing / all / set number)
- Set sitting / standing off collar color
- Implement 'middle-man' transport to extend target range from default 16 to preferred 40

### Integration To Do ###
- Factions: auto-attacks enemy factions in range

### Commands ###
- **/wolf stand**: Sets wolves to standing
- **/wolf sit**: Sets wolves to sitting
- **/wolf untame [number]**: Untames wolves (negative numbers untame all but specified number)

### Permissions ###
- **wolf.state.sit**: Set wolves to sitting
- **wolf.state.stand**: Set wolves to standing
- **wolf.state.untame**: Untame wolves (Equivalent to **wolf.untame.command** & **wolf.untame.manual**)
- **wolf.untame.command**: Bulk untame wolves by command line
- **wolf.untame.manual**: Untame wolf with Red Mushroom
- **wolf.attack.send**: Send standing wolves to attack a ranged mob
- **wolf.attack.cancel**: Set cancel ranged wolf attack

### Long Range Attacking ###
- Left clicking a mob within 40 blocks, with a stick, will set that mob as the active target for all standing tamed wolves!
- Right clicking will cancel active targeting for all wolves

### External Links ###
- [Bukkit Project](http://dev.bukkit.org/bukkit-plugins/wolfcommand/ 'Bukkit Project Page')
- [GitHub Repo](https://github.com/puppyize/WolfCommand 'GitHub Repository')

-----

_**Developer Note**_: I _am_ accepting "Enhancements" for the project. If you have a suggestion to better this plugin, or feel it's missing something, please fill out a ticket with the 'enhancement' tag and I'll see what I can do about it. **PLEASE REPORT ALL BUGS!**