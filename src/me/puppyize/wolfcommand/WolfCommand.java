package me.puppyize.wolfcommand;

import org.bukkit.DyeColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;


/**
 * <p>
 * This Minecraft plugin allows a player advanced control over their tamed
 * wolves.
 * </p>
 * 
 * @author Puppy Firelyte <mc@puppyize.me>
 */
public final class WolfCommand extends JavaPlugin{
	
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new WolfListener(), this);
	}

	public void onDisable() {
		HandlerList.unregisterAll();
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (cmd.getName().equalsIgnoreCase("wolf")) {
			if (args.length < 1)
				return false;
			if (sender instanceof Player) {
				WolfPlayer wp = new WolfPlayer((Player) sender);
				switch (args[0].toLowerCase()) {
				case "sit":
					if (sender.hasPermission("wolf.state.sit") || sender.hasPermission("wolf.state")) {
						switch (args.length) {
						case 2:
							try{
								wp.sitWolves(Integer.valueOf(args[1]));
							} catch(Exception e){
								try{
									DyeColor color = DyeColor.valueOf(args[1].toUpperCase());
									wp.sitWolves(color);
								} catch(IllegalArgumentException iae){
									this.invalidCollarColor(sender);
									break;
								}
							}
							break;
						case 1:
							wp.sitWolves();
							break;
						default:
							sender.sendMessage("Usage: /wolf sit [color|number]");
							break;
						}
					} else {
						sender.sendMessage("You need permission to set tamed wolves to sitting.");
					}
					return true;
				case "stand":
					if (sender.hasPermission("wolf.state.stand") || sender.hasPermission("wolf.state")) {
						switch (args.length) {
						case 2:
							try{
								wp.standWolves(Integer.valueOf(args[1]));
							} catch(Exception e){
								try{
									DyeColor color = DyeColor.valueOf(args[1].toUpperCase());
									wp.standWolves(color);
								} catch(IllegalArgumentException iae){
									this.invalidCollarColor(sender);
									break;
								}
							}
							break;
						case 1:
							wp.standWolves();
							break;
						default:
							sender.sendMessage("Usage: /wolf stand [sitting|standing|color|number]");
							break;
						}
					} else {
						sender.sendMessage("You need permission to set tamed wolves to standing.");
					}
					return true;
				case "untame":
					if (sender.hasPermission("wolf.untame.command") || sender.hasPermission("wolf.untame")) {
						switch (args.length) {
						case 2:
							if(args[1].toLowerCase().startsWith("sit")){
								wp.untameWolf(true);
							} else if(args[1].toLowerCase().startsWith("stand")){
								wp.untameWolf(false);
							} else {
								try {
									wp.untameWolf(Integer.valueOf(args[1]));
								} catch(Exception e) {
									try{
										DyeColor color = DyeColor.valueOf(args[1].toUpperCase());
										wp.untameWolf(color);
									} catch(IllegalArgumentException iae){
										this.invalidCollarColor(sender);
										break;
									}
								}
							}
							break;
						case 1:
							wp.untameWolf(wp.getWolves().size());
							break;
						default:
							sender.sendMessage("Usage: /wolf untame [sitting|standing|color|number]");
							break;
						}
					} else {
						sender.sendMessage("You need permission to untame wolves.");
					}
					return true;
				case "color":
					if (sender.hasPermission("wolf.collar.color")) {
						String opt = "ALL";
						DyeColor color = null;
						
						switch (args.length) {
							case 3:
								if(args[2].toLowerCase().startsWith("sit")){
									opt = "SITTING";
								} else if(args[2].toLowerCase().startsWith("stand")){
									opt = "STANDING";
								} else {
									try {
										if(Integer.valueOf(args[2]) > 0){
											opt = "NUM:" + args[2];
										} else {
											throw new Exception();
										}
									} catch(Exception e) {
										sender.sendMessage("Usage: /wolf color (color) [sitting|standing|number]");
										break;
									}
								}
							case 2: // Don't include break from 'Case 3' to allow flow into 'Case 2'
								try{
									color = DyeColor.valueOf(args[1].toUpperCase());
								} catch(IllegalArgumentException e){
									this.invalidCollarColor(sender);
									break;
								}
							
								try {
									wp.colorWolfRouter(opt, color);
								} catch(IllegalArgumentException iae){
									sender.sendMessage(iae.getMessage());
								}
							
								break;
							default:
								sender.sendMessage("Usage: /wolf color <color> [sitting|standing|number]");
								break;
						}
					} else {
						sender.sendMessage("You need permission to bulk color wolves.");
					}
					return true;
				}
			} else {
				sender.sendMessage("Sender must be a Player");
			}
		}

		return false;
	}
	
	public void invalidCollarColor(CommandSender sender){
		String legalColor = "";
		for (DyeColor c : DyeColor.values())
			legalColor += c.toString() + ", ";
		legalColor.substring(0, legalColor.length() - 2);
		
		sender.sendMessage("Not a legal collar color.");
		sender.sendMessage("Try: "+legalColor);
	}
}
