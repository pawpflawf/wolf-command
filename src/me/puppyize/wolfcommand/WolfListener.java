package me.puppyize.wolfcommand;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * <p>
 * This Minecraft plugin allows a player advanced control over their tamed
 * wolves.
 * </p>
 * 
 * @author Puppy Firelyte <mc@puppyize.me>
 */

public final class WolfListener implements Listener {
	@EventHandler
	public void attackDistantCreature(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (p.getItemInHand().getType() == Material.STICK) {
			LivingEntity target = null;
			Action a = e.getAction();
			
			WolfPlayer wp = new WolfPlayer(p); // Decorate Player object
			
			if (a == Action.LEFT_CLICK_AIR || a == Action.LEFT_CLICK_BLOCK) {
				if(p.hasPermission("wolf.attack.send") || p.hasPermission("wolf.attack")){
					target = wp.getTarget();
				} else {
					p.sendMessage("You need permission to use ranged attack.");
				}
			} else if (!(a == Action.RIGHT_CLICK_AIR  || a == Action.RIGHT_CLICK_BLOCK)) {
				if(p.hasPermission("wolf.attack.cancel") || p.hasPermission("wolf.attack")){
					wp.returnToPlayer();
				
					return;
				} else {
					p.sendMessage("You need permission to cancel ranged attack.");
				}
			}

			wp.setTarget(target);
		}
	}
	
	@EventHandler
	public void untameWolf(PlayerInteractEvent e){
		Player p = e.getPlayer();
	
		if(p.getItemInHand().getType() == Material.RED_MUSHROOM){
			if(p.hasPermission("wolf.untame.manual") || p.hasPermission("wolf.untame")){
				WolfPlayer wp = new WolfPlayer(p);
				wp.untameMe(wp.getWolfTarget());
			} else {
				p.sendMessage("You need permission to untame wolves.");
			}
		}
	}
}
