package me.puppyize.wolfcommand;

import org.bukkit.Color;
import org.bukkit.command.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
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
					if (sender.hasPermission("wolf.state.sit") || sender.hasPermission("wolf.state.sitstand")) {
						wp.sitWolves();
					} else {
						sender.sendMessage("You need permission to set tamed wolves to sitting.");
					}
					return true;
				case "stand":
					if (sender.hasPermission("wolf.state.stand") || sender.hasPermission("wolf.state.sitstand")) {
						wp.standWolves();
					} else {
						sender.sendMessage("You need permission to set tamed wolves to standing.");
					}
					return true;
				case "untame":
					if (sender.hasPermission("wolf.untame.command") || sender.hasPermission("wolf.state.untame")) {
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
						if (args.length > 1) {
							
						} else {
							sender.sendMessage("Usage: /wolf color (color) [sitting|standing|number]");
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
