package me.puppyize.wolfcommand;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTameEvent;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * <p>
 * This Minecraft plugin allows a player advanced control over their tamed
 * wolves.
 * </p>
 *
 * @author Pawpy Firelyte <dev@puppyize.me>
 */

final class WolfListener implements Listener {
	@EventHandler
	public void attackDistantCreature(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (p.getItemInHand().getType() == Material.STICK) {
			LivingEntity target = null;
			Action a = e.getAction();


			WolfPlayer wp = new WolfPlayer(p); // Decorate Player object

			if (a == Action.LEFT_CLICK_AIR || a == Action.LEFT_CLICK_BLOCK) {
				if (p.hasPermission("wolf.attack.send") || p.hasPermission("wolf.attack")) {
					target = wp.getTarget();
				} else {
					needPermission(p);
				}
			} else if (!(a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK)) {
				if (p.hasPermission("wolf.attack.cancel") || p.hasPermission("wolf.attack")) {
					wp.returnToPlayer();

					return;
				} else {
					needPermission(p);
				}
			}

			wp.setTarget(target);
		}
	}

	@EventHandler
	public void untameWolf(PlayerInteractEvent e) {
		Player p = e.getPlayer();

		if (p.getItemInHand().getType() == Material.RED_MUSHROOM) {
			if (p.hasPermission("wolf.untame.manual") || p.hasPermission("wolf.untame")) {
				WolfPlayer wp = new WolfPlayer(p);
				wp.untameWolf(wp.getWolfTarget());

				if (p.getGameMode() == GameMode.SURVIVAL) { // Only decrease on Survival
					int newAmount = p.getItemInHand().getAmount() - 1;
					if (newAmount < 1) {
						p.setItemInHand(null);
					} else {
						p.getItemInHand().setAmount(newAmount);
					}
				}
			} else {
				needPermission(p);
			}
		}
	}

	@EventHandler
	public void limitWolf(EntityTameEvent e) {
		if (e.getEntity().getName().equalsIgnoreCase("WOLF")) {
			WolfCommand wc = (WolfCommand) Bukkit.getPluginManager().getPlugin("WolfCommand");
			int MaxWolf = wc.getConfig().getInt("PLAYER_MAX_WOLF");

			CommandSender s = (CommandSender) e.getOwner();
			WolfPlayer p = new WolfPlayer((Player) e.getOwner());

			if (p.getWolves().size() >= MaxWolf && MaxWolf >= 0 && !s.isOp()) {
				e.setCancelled(true);
				s.sendMessage(ChatColor.RED + "You're not skilled enough to control this many wolves");
			}
		}
	}

	@EventHandler
	public void limitWolf(CreatureSpawnEvent e) {
		if (e.getEntity().getName().equalsIgnoreCase("WOLF") && e.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.BREEDING)) {

			WolfCommand wc = (WolfCommand) Bukkit.getPluginManager().getPlugin("WolfCommand");
			Wolf w = (Wolf) e.getEntity();
			int MaxWolf = wc.getConfig().getInt("PLAYER_MAX_WOLF");

			CommandSender s = (CommandSender) w.getOwner();
			WolfPlayer p = new WolfPlayer((Player) w.getOwner());

			if (p.getWolves().size() >= MaxWolf && MaxWolf >= 0 && !s.isOp()) {
				e.setCancelled(true);
				s.sendMessage(ChatColor.RED + "You're not skilled enough to control this many wolves");
			}
		}
	}

	@EventHandler
	public void disableStickDamage(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Wolf && e.getDamager() instanceof Player) {
			Wolf w = (Wolf) e.getEntity();
			Player p = (Player) e.getDamager();

			if (w.getOwner().equals(p) && p.getItemInHand().getType() == Material.STICK) {
				Bukkit.getPluginManager().getPlugin("WolfCommand").getConfig().getBoolean("DISABLE_STICK_DAMAGE");
				e.setCancelled(true);
			}
		}
	}


	private void needPermission(CommandSender s) {
		s.sendMessage(ChatColor.RED + "You do not have sufficient permissions to do this action");
	}

}
