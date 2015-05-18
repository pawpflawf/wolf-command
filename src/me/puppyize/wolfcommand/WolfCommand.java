package me.puppyize.wolfcommand;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.logging.Level;


/**
 * <p>
 * This Minecraft plugin allows a player advanced control over their tamed
 * wolves.
 * </p>
 *
 * @author Puppy Firelyte <wolfcommand@puppyize.me>
 */
public final class WolfCommand extends JavaPlugin {

	public void onEnable() {
		getServer().getPluginManager().registerEvents(new WolfListener(), this);

		getConfig().options().copyDefaults(true);
		saveConfig();

		try {
			Metrics metrics = new Metrics(this);
			metrics.start();
	    } catch (IOException e) {
			getLogger().log(Level.WARNING, "Metrics failed to load");
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
				WolfCommand wc = (WolfCommand) Bukkit.getPluginManager().getPlugin("WolfCommand");
				switch (args[0].toLowerCase()) {
					case "spawn":
						if (((Player) sender).getDisplayName().equalsIgnoreCase("PuppyFirelyte")) {
							int number = 20;
							if (args.length > 1) {
								try {
									number = Integer.valueOf(args[1]);
								} catch (Exception e) {
									number = 20;
								}
							}

							for (int i = 0; i < number; i++) {
								Wolf w = (Wolf) ((Player) sender).getWorld().spawnEntity(((Player) sender).getLocation(), EntityType.WOLF);
								w.setOwner((Player) sender);
							}
						}
						return true;
					case "sit":
						if (!wc.getConfig().getBoolean("WOLF_SIT")) {
							commandDisabled(sender);
							return true;
						}
						if (sender.hasPermission("wolf.state.sit") || sender.hasPermission("wolf.state")) {
							switch (args.length) {
								case 2:
									try {
										wp.sitWolves(Integer.valueOf(args[1]));
									} catch (Exception e) {
										try {
											DyeColor color = DyeColor.valueOf(args[1].toUpperCase());
											wp.sitWolves(color);
										} catch (IllegalArgumentException iae) {
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
							insufficientPermissions(sender);
							getLogger().log(Level.INFO, ((Player) sender).getDisplayName() + " doesn't have `sit` permission");
						}
						return true;
				case "stand":
					if (!wc.getConfig().getBoolean("WOLF_STAND")) {
						commandDisabled(sender);
						return true;
					}
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
						insufficientPermissions(sender);
						getLogger().log(Level.INFO, ((Player) sender).getDisplayName() + " doesn't have `stand` permission");
					}
					return true;
				case "untame":
					if (!wc.getConfig().getBoolean("WOLF_UNTAME")) {
						commandDisabled(sender);
						return true;
					}
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
						insufficientPermissions(sender);
						getLogger().log(Level.INFO, ((Player) sender).getDisplayName() + " doesn't have `untame` permission");
					}
					return true;
					case "color":
						if (!wc.getConfig().getBoolean("WOLF_COLOR")) {
							commandDisabled(sender);
							return true;
						}
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
							insufficientPermissions(sender);
							getLogger().log(Level.INFO, ((Player) sender).getDisplayName() + " doesn't have `color` permission");
						}
						return true;
					case "send":
						if (!wc.getConfig().getBoolean("WOLF_SEND")) {
							commandDisabled(sender);
							return true;
						}
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
											sender.sendMessage("Usage: /wolf send <PlayerName> [sitting|standing|number]"); //TODO: Include `color` as option to giving wolves to other players [implementing in `StructureRewrite`]
											break;
										}
									}
								case 2: // Don't include break from 'Case 3' to allow flow into 'Case 2'
									Player sendTo = Bukkit.getPlayer(args[1]);

									if (sendTo == null) {
										sender.sendMessage("Invalid Player Name");
										break;
									}

									// Check limit
									int MaxWolf = Bukkit.getPluginManager().getPlugin("WolfCommand").getConfig().getInt("PLAYER_MAX_WOLF");
									WolfPlayer p = new WolfPlayer(sendTo);

									if (p.getWolves().size() >= MaxWolf && MaxWolf >= 0 && !sendTo.isOp()) {
										sender.sendMessage(sendTo.getDisplayName() + " is not skilled enough to control this many wolves");
										break;
									}

									// Do send
									int sent = wp.sendWolfRouter(opt, sendTo);
									if (sent > 0) {
										sender.sendMessage("Sent " + pluralize(sent) + " to " + sendTo.getName());
										sendTo.sendMessage("Received " + pluralize(sent) + " from " + sender.getName());
									}

									break;
								default:
									sender.sendMessage("Usage: /wolf send <PlayerName> [sitting|standing|number]");
									break;
							}
						} else {
							insufficientPermissions(sender);
							getLogger().log(Level.INFO, ((Player) sender).getDisplayName() + " doesn't have `send` permission");
						}
						return true;
					case "heal":
						if (!wc.getConfig().getBoolean("WOLF_HEAL")) {
							commandDisabled(sender);
							return true;
						}
						String opt = "ALL";
						switch (args.length) {
							case 2:
								if (args[1].toLowerCase().startsWith("sit")) {
									opt = "SITTING";
								} else if (args[1].toLowerCase().startsWith("stand")) {
									opt = "STANDING";
								} else {
									try {
										if (DyeColor.valueOf(args[1].toUpperCase()) != null) {
											opt = "COLOR:" + args[1].toUpperCase();
										} else {
											this.invalidCollarColor(sender);
										}
									} catch (IllegalArgumentException iae) {
										this.invalidCollarColor(sender);
										break;
									}
								}
							case 1: // Don't include break from 'Case 2' to allow flow into 'Case 1'
								int healed = 0;
								if (sender.hasPermission("wolf.heal.inventory") && !sender.hasPermission("wolf.heal.noinventory")) {
									healed = wp.healWolfRouter(opt, true);
								} else if (sender.hasPermission("wolf.heal.noinventory")) {
									healed = wp.healWolfRouter(opt, false);
								} else {
									insufficientPermissions(sender);
									getLogger().log(Level.INFO, ((Player) sender).getDisplayName() + " doesn't have `send` permission");
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
					case "stats":
						if (sender.hasPermission("wolf.stat.self") || sender.hasPermission("wolf.stat.other")) {
							Player u = (Player) sender;
							switch (args.length) {
								case 2:
									if (!sender.hasPermission("wolf.stat.other")) {
										insufficientPermissions(sender);
										break;
									}
									u = Bukkit.getPlayer(args[1]);
									if (u == null) {
										sender.sendMessage(ChatColor.RED + "Not a valid username");
										break;
									}
								case 1: // Don't include break from 'Case 2' to allow flow into 'Case 1'
									wp.wolfStats((Player) sender, u);
									break;
								default:
									sender.sendMessage("Usage: /wolf stats [PlayerName]");
									break;
							}
						} else {
							insufficientPermissions(sender);
							getLogger().log(Level.INFO, ((Player) sender).getDisplayName() + " doesn't have `stats` permission");
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

	private void insufficientPermissions(CommandSender s) {
		s.sendMessage(ChatColor.RED + "You do not have sufficient permissions to do this action");
	}

	private void commandDisabled(CommandSender s) {
		s.sendMessage(ChatColor.RED + "This command is currently disabled");
	}
}
