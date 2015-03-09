# Wolf Command - Serious Taming #
## Latest: 2.1 ##

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
- Implement 'middle-man' transport to extend target range from default 16 to preferred 40

### Integration To Do ###
- Factions: auto-attacks enemy factions in range

### Commands ###
- **/wolf stand [color/number]**: Sets wolves to standing
- **/wolf sit [color/number]**: Sets wolves to sitting
- **/wolf untame [color/number]**: Untames wolves (negative numbers untame all but specified number)
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

### Long Range Attacking ###
- Left clicking a mob within 40 blocks, with a stick, will set that mob as the active target for all standing tamed wolves!
- Right clicking will cancel active targeting for all wolves

### Bulk Color Collars ###
- When no *optional specifier* is provided, default is all personally tamed wolves
- If a number is provided, command will attempt to color *number* **more** wolf collars

### External Links ###
- [Bukkit Project](http://dev.bukkit.org/bukkit-plugins/wolfcommand/ 'Bukkit Project Page')
- [GitHub Repo](https://github.com/puppyize/WolfCommand 'GitHub Repository')

-----

_**Developer Note**_: I _am_ accepting "Enhancements" for the project. If you have a suggestion to better this plugin, or feel it's missing something, please fill out a ticket with the 'enhancement' tag and I'll see what I can do about it. **PLEASE REPORT ALL BUGS!**