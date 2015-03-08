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
						wp.sitWolves();
					} else {
						sender.sendMessage("You need permission to set tamed wolves to sitting.");
					}
					return true;
				case "stand":
					if (sender.hasPermission("wolf.state.stand") || sender.hasPermission("wolf.state")) {
						wp.standWolves();
					} else {
						sender.sendMessage("You need permission to set tamed wolves to standing.");
					}
					return true;
				case "untame":
					if (sender.hasPermission("wolf.untame.command") || sender.hasPermission("wolf.untame")) {
						if (args.length > 1) {
							wp.untameWolves(Integer.valueOf(args[1]));
						} else {
							wp.untameWolves(wp.getWolves().size());
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
								} else if(Integer.getInteger(args[2]) != null){
									opt = "NUM:" + args[2];
								} else {
									sender.sendMessage("Usage: /wolf color (color) [sitting|standing|number]");
									break;
								}
							case 2: // Don't include break from 'Case 3' to allow flow into 'Case 2'
								try{
									color = DyeColor.valueOf(args[1]);
								} catch(IllegalArgumentException e){
									String legalColor = "";
									for (DyeColor c : DyeColor.values())
										legalColor += c.toString() + ",";
									legalColor.substring(0, legalColor.length() - 1);
									
									sender.sendMessage("Not a legal collar color.");
									sender.sendMessage("Try: "+legalColor);
								}
							
								try {
									wp.colorWolfRouter(opt, color);
								} catch(IllegalArgumentException e){
									sender.sendMessage(e.getMessage());
								}
							
								break;
							default:
								sender.sendMessage("Usage: /wolf color (color) [sitting|standing|number]");
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
	
}
