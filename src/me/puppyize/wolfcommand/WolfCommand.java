package me.puppyize.wolfcommand;

import org.bukkit.DyeColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;


/**
 * <p>
 * This Minecraft plugin allows a player advanced control over their tamed
 * wolves.
 * </p>
 *
 * @author Puppy Firelyte <wolfcommand@puppyize.me>
 */
public final class WolfCommand extends JavaPlugin{

	public void onEnable() {
		getServer().getPluginManager().registerEvents(new WolfListener(), this);

		getConfig().options().header("WolfCommand Configurations").copyDefaults(true);
		saveConfig();

		try {
			Metrics metrics = new Metrics(this);
			metrics.start();
	    } catch (IOException e) {
	        // Failed to submit the stats :-(
	    }
	}

	public void onDisable() {
		HandlerList.unregisterAll();
	}

	private String pluralize(int num) {
		if (num == 1) {
			return num + " wolf";
		} else {
			return num + " wolves";
		}
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label,
							 String[] args) { //TODO: Break out into separate functions per command (cleaner looking)
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
						int untamed;
						switch (args.length) {
						case 2:
							if(args[1].toLowerCase().startsWith("sit")){
								untamed = wp.untameWolf(true);
							} else if(args[1].toLowerCase().startsWith("stand")){
								untamed = wp.untameWolf(false);
							} else {
								try {
									untamed = wp.untameWolf(Integer.valueOf(args[1]));
								} catch(Exception e) {
									try{
										DyeColor color = DyeColor.valueOf(args[1].toUpperCase());
										untamed = wp.untameWolf(color);
									} catch(IllegalArgumentException iae){
										this.invalidCollarColor(sender);
										break;
									}
								}
							}
							if (untamed > 0) sender.sendMessage(pluralize(untamed) + " untamed");
							break;
						case 1:
							untamed = wp.untameWolf(wp.getWolves().size());
							if (untamed > 0) sender.sendMessage(pluralize(untamed) + " untamed");
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
							DyeColor color;

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
											sender.sendMessage("Usage: /wolf color <color> [sitting|standing|number]");
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
										int colored = wp.colorWolfRouter(opt, color);
										if (colored > 0)
											sender.sendMessage("Colored " + pluralize(colored) + " " + color.toString().toLowerCase());
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
					case "send":
						if (sender.hasPermission("wolf.send.command")) {
							String opt = "ALL";

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
											sender.sendMessage("Usage: /wolf send <PlayerName> [sitting|standing|number]"); //TODO: Include `color` as option to giving wolves to other players
											break;
										}
									}
								case 2: // Don't include break from 'Case 3' to allow flow into 'Case 2'
									Player sendTo = null;

									for(Player p : this.getServer().getOnlinePlayers()){
										if(p.getName().equalsIgnoreCase(args[1])){
											sendTo = p;
											break;
										}
									}

									if (sendTo == null) {
										sender.sendMessage("Invalid Player Name");
										break;
									}

									// Do send
									int sent = wp.sendWolfRouter(opt, sendTo);
									if (sent > 0) {
										sender.sendMessage("Sent " + pluralize(sent) + " to " + sendTo.getName());
										sendTo.sendMessage("Received " + pluralize(sent) + " wolf from " + sender.getName());
									}

									break;
								default:
									sender.sendMessage("Usage: /wolf send <PlayerName> [sitting|standing|number]");
									break;
							}
						} else {
							sender.sendMessage("You need permission to send your wolves to another player.");
						}
						return true;
					case "heal":
						String opt = "ALL";

						switch (args.length) {
							case 3:
								if (args[2].toLowerCase().startsWith("sit")) {
									opt = "SITTING";
								} else if (args[2].toLowerCase().startsWith("stand")) {
									opt = "STANDING";
								} else {
									try {
										if (DyeColor.valueOf(args[2].toUpperCase()) != null) {
											opt = "COLOR:" + args[2].toUpperCase();
										} else {
											this.invalidCollarColor(sender);
										}
									} catch (IllegalArgumentException iae) {
										this.invalidCollarColor(sender);
										break;
									}
								}
							case 2: // Don't include break from 'Case 3' to allow flow into 'Case 2'

								int healed = 0;
								if (sender.hasPermission("wolf.heal.inventor") && !sender.hasPermission("wolf.heal.noinventor")) {
									healed = wp.healWolfRouter(opt, true);
								} else if (sender.hasPermission("wolf.heal.noinventor")) {
									healed = wp.healWolfRouter(opt, false);
								} else {
									sender.sendMessage("You need permission to mass heal your wolves.");
								}

								if (healed > 0) {
									sender.sendMessage("Healed " + pluralize(healed));
								}

								break;
							default:
								sender.sendMessage("Usage: /wolf heal [sitting|standing|color]");
								break;
						}

						return true;
				}
			} else {
				sender.sendMessage("Sender must be a Player");
			}
		}

		return false;
	}
	
	private void invalidCollarColor(CommandSender sender){
		String legalColor = "";
		for (DyeColor c : DyeColor.values())
			legalColor += c.toString() + ", ";
		legalColor = legalColor.substring(0, legalColor.length() - 2);
		
		sender.sendMessage("Not a legal collar color.");
		sender.sendMessage("Try: "+legalColor);
	}
}
