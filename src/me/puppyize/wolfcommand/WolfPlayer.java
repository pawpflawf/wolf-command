package me.puppyize.wolfcommand;

import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.entity.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * This Minecraft plugin allows a player advanced control over their tamed
 * wolves.
 * </p>
 *
 * @author Puppy Firelyte <wolfcommand@puppyize.me>
 */
@SuppressWarnings("UnusedReturnValue")
class WolfPlayer {
	//TODO: Move `Command Routing` functions to separate class(es)
	//TODO: Add proper Javadocs to all functions
	private final Double ATTACK_RANGE = 40D;
	
	/**
	 * Keeps the current player
	 */
	private final Player player;
	
	/**
	 * Creates a WolfPlayer to decorate a Player object
	 * @param player current active player
	 */
	public WolfPlayer(Player player) {
		this.player = player;
	}
	
	/**
	 * Sets all Player tamed wolves to a standing state
	 * @return number of wolves set to sitting
	 */
	public int sitWolves() {
		int count = 0;
		for (Wolf w : this.getWolves()) {
			if (!w.isSitting()) {
				w.setSitting(true);
				count++;
			}
		}
		return count;
	}
	
	/**
	 * Sets set number of Player tamed wolves to a standing state
	 */
	public void sitWolves(int numWolves) {
		if(numWolves > 0){
			for (Wolf w : this.getWolves()) {
				if (numWolves < 1) break;
				
				if (!w.isSitting()) {
					w.setSitting(true);
					numWolves--;
				}
			}
		} else {
			this.player.sendMessage("Number must be greater than 0");
		}
	}
	
	/**
	 * Sets tamed wolves with a specified collar color to a standing state
	 * @return number of wolves set to sitting
	 */
	public int sitWolves(DyeColor c) {
		int count = 0;
		for (Wolf w : this.getWolves()) {
			if (!w.isSitting() & (w.getCollarColor() == c)) {
				w.setSitting(true);
				count++;
			}
		}
		return count;
	}
	
	/**
	 * Sets all Player tamed wolves to a standing state
	 * @return number of wolves set to standing
	 */
	public int standWolves() {
		int count = 0;
		for (Wolf w : this.getWolves()) {
			if (w.isSitting()) {
				w.setSitting(false);
				count++;
			}
		}
		return count;
	}
	
	/**
	 * Sets set number of Player tamed wolves to a standing state
	 */
	public void standWolves(int numWolves) {
		if(numWolves > 0){
			for (Wolf w : this.getWolves()) {
				if (numWolves < 1) break;
				
				if (w.isSitting()) {
					w.setSitting(false);
					numWolves--;
				}
			}
		} else {
			this.player.sendMessage("Number must be greater than 0");
		}
	}
	
	/**
	 * Sets tamed wolves with a specified collar color to a standing state
	 * @return number of wolves set to standing
	 */
	public int standWolves(DyeColor c) {
		int count = 0;
		for (Wolf w : this.getWolves()) {
			if (w.isSitting() & (w.getCollarColor() == c)) {
				w.setSitting(false);
				count++;
			}
		}
		return count;
	}

	/**
	 * Tamed wolf collar color router
	 */
	public void colorWolfRouter(String group, DyeColor color) {
		switch (group) {
			case "ALL":
				colorWolfCollar(color);
			case "SITTING":
				colorWolfCollar(color, true);
				break;
			case "STANDING":
				colorWolfCollar(color, false);
				break;
			default:
				if(group.startsWith("NUM:")){
					group = group.substring(4);
					colorWolfCollar(color, Integer.valueOf(group));
				} else {
					throw new IllegalArgumentException("Not a valid optional specifier");
				}
				break;
		}
	}

	/**
	 * Colors all player's tamed wolves collars to specified color
	 * @param	c	specified DyeColor
	 * @return	number of changed wolves
	 */
	private int colorWolfCollar(DyeColor c){
		int count = 0;
		for (Wolf w : this.getWolves()) {
			if (w.getCollarColor() != c) {
				w.setCollarColor(c);
				count++;
			}
		}
		return count;
	}
	
	/**
	 * Colors player's tamed sitting or standing wolves to the specified color
	 * @param	c	specified DyeColor
	 * @param	isSitting	boolean of WolfState
	 * @return	number of changed wolves
	 */
	private int colorWolfCollar(DyeColor c, boolean isSitting){
		int count = 0;
		for (Wolf w : this.getWolves()) {
			if ((w.isSitting() == isSitting) & (w.getCollarColor() != c)) {
				w.setCollarColor(c);
				count++;
			}
		}
		return count;
	}
	
	/**
	 * Color's defined number of player's tamed wolves to the specified color
	 * @param	c	specified DyeColor
	 * @param	numWolves	integer describing how many wolves to change
	 */
	private void colorWolfCollar(DyeColor c, int numWolves){
		if(numWolves > 0){
			for (Wolf w : this.getWolves()) {
				if(numWolves < 1) break;
				if(w.getCollarColor() == c) continue;
				
				w.setCollarColor(c);
				numWolves--;
			}
		} else {
			this.player.sendMessage("Number must be greater than 0");
		}
	}

	/**
	 * Forfeit tamed wolf router
	 */
	public void giveWolfRouter(String group, Player p) {
		switch (group) {
			case "ALL":
				giveWolf(p);
			case "SITTING":
				giveWolf(p, true);
				break;
			case "STANDING":
				giveWolf(p, false);
				break;
			default:
				if(group.startsWith("NUM:")){
					group = group.substring(4);
					giveWolf(p, Integer.valueOf(group));
				} else {
					throw new IllegalArgumentException("Not a valid optional specifier");
				}
				break;
		}
	}

	/**
	 * Gives all tamed wolves to specified Player
	 * @param	giveTo	specified Player
	 * @return number of transferred wolves
	 */
	private int giveWolf(Player giveTo){
		int count = 0;
		for (Wolf w : this.getWolves()) {
			w.setOwner(giveTo);
			if (w.getOwner() != giveTo) {
				w.setOwner(giveTo);
				count++;
			}
		}
		return count;
	}

	/**
	 * Gives all tamed wolves to specified Player
	 * @param	giveTo	specified Player
	 * @param isSitting boolean of WolfState
	 * @return number of transferred wolves
	 */
	private int giveWolf(Player giveTo, boolean isSitting){
		int count = 0;
		for (Wolf w : this.getWolves()) {
			if ((w.isSitting() == isSitting) & (w.getOwner() != giveTo)) {
				w.setOwner(giveTo);
				count++;
			}
		}
		return count;
	}

	/**
	 * Gives all tamed wolves to specified Player
	 * @param	giveTo	specified Player
	 * @param numWolves  number of wolves to transfer
	 */
	private void giveWolf(Player giveTo, int numWolves){
		if (numWolves > 0) {
			for (Wolf w : this.getWolves()) {
				if (numWolves < 1) break;
				if (w.getOwner() == giveTo) continue;

				w.setOwner(giveTo);
				numWolves--;
			}
		} else {
			this.player.sendMessage("Number must be greater than 0");
		}
	}


	/**
	 * Heal tamed wolf router
	 */
	public void healWolfRouter(String group, boolean withInventory) {
		switch (group) {
			case "ALL":
				healWolf(withInventory);
			case "SITTING":
				healWolf(withInventory, true);
				break;
			case "STANDING":
				healWolf(withInventory, false);
				break;
			default:
				if (group.startsWith("COLOR:")) {
					group = group.substring(6);
					healWolf(withInventory, DyeColor.valueOf(group));
				} else {
					throw new IllegalArgumentException("Not a valid optional specifier");
				}
				break;
		}
	}

	/**
	 * Heals as many wolves as it can with(out) Inventory
	 *
	 * @param withInv whether to deduct inventory or not
	 * @return number of healed wolves
	 */
	private int healWolf(boolean withInv) { //TODO: Complete Function
		int count = 0;
		for (Wolf w : this.getWolves()) {
			if (w.getHealth() == w.getMaxHealth()) continue;

			if (withInv) {

			}

			w.setHealth(w.getMaxHealth());
			count++;
		}
		return count;
	}

	/**
	 * Heals wolves in given state with(out) Inventory
	 *
	 * @param isSitting boolean of WolfState
	 * @param withInv   whether to deduct inventory or not
	 * @return number of healed wolves
	 */
	private int healWolf(boolean withInv, boolean isSitting) { //TODO: Complete Function
		int count = 0;
		for (Wolf w : this.getWolves()) {
			if ((w.isSitting() == isSitting) & (w.getHealth() != w.getMaxHealth())) {
				if (withInv) {

				}

				w.setHealth(w.getMaxHealth());
				count++;
			}
		}
		return count;
	}

	/**
	 * Heals specified number of wolves with(out) Inventory
	 *
	 * @param color color of wolf collar to heal
	 * @param withInv   whether to deduct inventory or not
	 * @return number of healed wolves
	 */
	private int healWolf(boolean withInv, DyeColor color) { //TODO: Complete Function
		int count = 0;
		for (Wolf w : this.getWolves()) {
			if (w.getCollarColor() == color) continue;
			if (w.getHealth() == w.getMaxHealth()) continue;

			if (withInv) {

			}

			w.setHealth(w.getMaxHealth());
			count++;
		}
		return count;
	}


	/**
	 * Untames tamed wolves based on state
	 * @param	isSitting	desired state of wolves
	 */
	public int untameWolf(boolean isSitting) {
		int numWolves = 0;
		
		for (Wolf w : this.getWolves()) {
			if (w.isTamed() && (w.isSitting() == isSitting)) {
				this.setUntame(w);
				numWolves++;
			}
		}
		
		return numWolves;
	}
	
	/**
	 * Untames desired number of wolves. Negative numbers will untame all but the specified number.
	 * @param	number	desired number of wolves
	 */
	public void untameWolf(int number) {
		int numWolves = this.getWolves().size();
		
		if(number >= 0){
			numWolves = number;
		} else {
			numWolves = numWolves + number; // Add number since negative already
		}
		
		for (Wolf w : this.getWolves()) {
			if(numWolves < 1) break;
			if (w.isTamed()) {
				this.setUntame(w);
				numWolves--;
			}
		}
	}
	
	/**
	 * Untames tamed wolves based on their collar color
	 * @param	c	specified DyeColor
	 */
	public int untameWolf(DyeColor c){
		int numWolves = 0; 
		
		for (Wolf w : this.getWolves()) {
			if(w.getCollarColor() == c){
				this.setUntame(w);
				numWolves++;
			}
		}
		
		return numWolves;
	}
	
	
	/**
	 * Untames an individual wolf
	 * @param target wolf entity to untame
	 */
	public void untameWolf(LivingEntity target) {
		for (Wolf w : this.getWolves()) {
			if(target.getUniqueId() == w.getUniqueId()){
				this.setUntame(w);
			}
		}
	}
	
	/**
	 * Set tamed wolf to standing then untames them
	 * @param	w	wolf to untame
	 */
	private void setUntame(Wolf w){
		w.setSitting(false); // Make stand before untaming to prevent AI breaking
		w.setTamed(false);
	}
	
	/**
	 * Teleports standing wolves to Player
	 * @return number of wolves that return to player
	 */
	public int returnToPlayer() {
		int count = 0;
		for (Wolf w : this.getWolves()) {
			if (!w.isSitting()) {
				w.teleport(this.player.getLocation());
				count++;
			}
		}
		return count;
	}
	
	/**
	 * Gets player's tamed wolves
	 * @return List of currently tamed wolves
	 */
	public List<Wolf> getWolves() {
		List<Wolf> entities = new ArrayList<>();
		for (Entity e : player.getNearbyEntities(ATTACK_RANGE, ATTACK_RANGE, ATTACK_RANGE)) {
			if (e instanceof Wolf) {
				Tameable t = (Tameable) e;
				if (t.isTamed() && t.getOwner() == this.player) {
					entities.add((Wolf) e);
				}
			}
		}
		return entities;
	}
	
	/**
	 * Sets player's wolves target
	 * @param target the LivingEntity in player crosshair
	 */
	public void setTarget(LivingEntity target) {
		for (Wolf w : this.getWolves()) {
			if (!w.isSitting()) {
				w.setTarget(target); //FIXME Hard coded limit of 16 blocks, need work around to extend to 40 blocks as per plugin specification
			}
		}
	}
	
	/**
	 * Gets the closest tamed wolf in player's crosshair
	 * @return target
	 */
	public LivingEntity getWolfTarget() {
		Location observerPos = this.player.getEyeLocation();
        Vector3D observerDir = new Vector3D(observerPos.getDirection());
        Vector3D observerStart = new Vector3D(observerPos);
        Vector3D observerEnd = observerStart.add(observerDir.multiply(ATTACK_RANGE));
        Entity targetEntity = null;

        // Loop through nearby entities (may be slow if too many around)
        for (Entity entity : this.player.getNearbyEntities(ATTACK_RANGE, ATTACK_RANGE, ATTACK_RANGE)) {
        	// Skip not living entities
        	if (!(entity instanceof LivingEntity)) {
        		continue;
        	}
        	
        	if (!this.player.hasLineOfSight(entity)) {
        		continue;
        	}

        	// Select only our own wolves
        	if (entity instanceof Tameable && entity instanceof Wolf) {
        		Tameable t = (Tameable) entity;
        		if (t.isTamed() && t.getOwner() == this.player) {
        			Vector3D targetPos = new Vector3D(entity.getLocation());
                    Vector3D minimum = targetPos.add(-0.5, 0, -0.5);
                    Vector3D maximum = targetPos.add(0.5, 1.67, 0.5);

                    if (entity != this.player && Vector3D.hasIntersection(observerStart, observerEnd, minimum, maximum)) {
                    	// Get closest living entity on vector
                        if (targetEntity == null ||
                        	targetEntity.getLocation().distanceSquared(observerPos) > entity.getLocation().distanceSquared(observerPos)) {
                            targetEntity = entity;
                        }
                    }
        		}
        	}
        }
        return (LivingEntity) targetEntity;
	}
	
	/**
	 * Gets the closest LivingEntity in player's crosshair
	 * @return target
	 */
	public LivingEntity getTarget() {
		Location observerPos = this.player.getEyeLocation();
        Vector3D observerDir = new Vector3D(observerPos.getDirection());
        Vector3D observerStart = new Vector3D(observerPos);
        Vector3D observerEnd = observerStart.add(observerDir.multiply(ATTACK_RANGE));
        Entity targetEntity = null;

        // Loop through nearby entities (may be slow if too many around)
        for (Entity entity : this.player.getNearbyEntities(ATTACK_RANGE, ATTACK_RANGE, ATTACK_RANGE)) {
        	// Skip not living entities
        	if (!(entity instanceof LivingEntity)) {
        		continue;
        	}

        	// Skip our own wolves
        	if (entity instanceof Tameable && entity instanceof Wolf) {
        		Tameable t = (Tameable) entity;
        		if (t.isTamed() && t.getOwner() == this.player) {
        			continue;
        		}
        	}

        	// We can't attack what we can't see
        	if (!this.player.hasLineOfSight(entity)) {
        		continue;
        	}

        	// Bukkit API does not export an axis-aligned bounding box, so we'll settle for this
        	// Bounding box is set to 1 block in width and 1.67 blocks high from entity's center
        	Vector3D targetPos = new Vector3D(entity.getLocation());
            Vector3D minimum = targetPos.add(-0.5, 0, -0.5);
            Vector3D maximum = targetPos.add(0.5, 1.67, 0.5);

            if (entity != this.player && Vector3D.hasIntersection(observerStart, observerEnd, minimum, maximum)) {
            	// Get closest living entity on vector
                if (targetEntity == null ||
                	targetEntity.getLocation().distanceSquared(observerPos) > entity.getLocation().distanceSquared(observerPos)) {
                    targetEntity = entity;
                }
            }
        }
        return (LivingEntity) targetEntity;
	}
}
