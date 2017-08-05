package me.puppyize.wolfcommand;

import net.minecraft.server.v1_12_R1.ItemFood;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

/**
 * <p>
 * This Minecraft plugin allows a player advanced control over their tamed
 * wolves.
 * </p>
 *
 * @author Pawpy Firelyte <wolfcommand@puppyize.me>
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
	 *
	 * @param player current active player
	 */
    WolfPlayer(Player player) {
		this.player = player;
	}

	/**
	 * Sets all Player tamed wolves to a standing state
	 *
	 * @return number of wolves set to sitting
	 */
    int sitWolves() {
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
    int sitWolves(int numWolves) {
		int count = 0;
		if (numWolves > 0) {
			for (Wolf w : this.getWolves()) {
				if (numWolves < 1) break;

				if (!w.isSitting()) {
					w.setSitting(true);
					numWolves--;
					count++;
				}
			}
		} else {
			this.player.sendMessage("Number must be greater than 0");
		}
		return count;
	}

	/**
	 * Sets tamed wolves with a specified collar color to a standing state
	 *
	 * @return number of wolves set to sitting
	 */
    int sitWolves(DyeColor c) {
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
	 *
	 * @return number of wolves set to standing
	 */
    int standWolves() {
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
    int standWolves(int numWolves) {
		int count = 0;
		if (numWolves > 0) {
			for (Wolf w : this.getWolves()) {
				if (numWolves < 1) break;

				if (w.isSitting()) {
					w.setSitting(false);
					numWolves--;
					count++;
				}
			}
		} else {
			this.player.sendMessage("Number must be greater than 0");
		}
		return count;
	}

	/**
	 * Sets tamed wolves with a specified collar color to a standing state
	 *
	 * @return number of wolves set to standing
	 */
    int standWolves(DyeColor c) {
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
    int colorWolfRouter(String group, DyeColor color) {
		int affected;
		switch (group) {
			case "ALL":
				affected = colorWolfCollar(color);
				break;
			case "SITTING":
				affected = colorWolfCollar(color, true);
				break;
			case "STANDING":
				affected = colorWolfCollar(color, false);
				break;
			default:
				if (group.startsWith("NUM:")) {
					group = group.substring(4);
					affected = colorWolfCollar(color, Integer.valueOf(group));
				} else {
					throw new IllegalArgumentException("Not a valid optional specifier");
				}
				break;
		}
		return affected;
	}

	/**
	 * Colors all player's tamed wolves collars to specified color
	 *
	 * @param    c    specified DyeColor
	 * @return number of changed wolves
	 */
	private int colorWolfCollar(DyeColor c) {
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
	 *
	 * @param    c    specified DyeColor
	 * @param    isSitting    boolean of WolfState
	 * @return number of changed wolves
	 */
	private int colorWolfCollar(DyeColor c, boolean isSitting) {
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
	 *
	 * @param    c    specified DyeColor
	 * @param    numWolves    integer describing how many wolves to change
	 */
	private int colorWolfCollar(DyeColor c, int numWolves) {
		int count = 0;
		if (numWolves > 0) {
			for (Wolf w : this.getWolves()) {
				if (numWolves < 1) break;
				if (w.getCollarColor() == c) continue;

				w.setCollarColor(c);
				numWolves--;
				count++;
			}
		} else {
			this.player.sendMessage("Number must be greater than 0");
		}
		return count;
	}

	/**
	 * Send tamed wolf router
	 */
    int sendWolfRouter(String group, Player p) {
		int affected;
		switch (group) {
			case "ALL":
				affected = sendWolf(p);
				break;
			case "SITTING":
				affected = sendWolf(p, true);
				break;
			case "STANDING":
				affected = sendWolf(p, false);
				break;
			default:
				if (group.startsWith("NUM:")) {
					group = group.substring(4);
					affected = sendWolf(p, Integer.valueOf(group));
				} else {
					throw new IllegalArgumentException("Not a valid optional specifier");
				}
				break;
		}
		return affected;
	}

	/**
	 * Gives all tamed wolves to specified Player
	 *
	 * @param sendTo specified Player
	 * @return number of transferred wolves
	 */
	private int sendWolf(Player sendTo) {
		int count = 0;
		for (Wolf w : this.getWolves()) {
			if (w.getOwner() != sendTo) {
				w.setOwner(sendTo);
				w.setSitting(false);
				count++;
			}
		}
		return count;
	}

	/**
	 * Gives all tamed wolves to specified Player
	 *
	 * @param sendTo    specified Player
	 * @param isSitting boolean of WolfState
	 * @return number of transferred wolves
	 */
	private int sendWolf(Player sendTo, boolean isSitting) {
		int count = 0;
		for (Wolf w : this.getWolves()) {
			if ((w.isSitting() == isSitting) & (w.getOwner() != sendTo)) {
				w.setOwner(sendTo);
				w.setSitting(false);
				count++;
			}
		}
		return count;
	}

	/**
	 * Gives all tamed wolves to specified Player
	 *
	 * @param sendTo    specified Player
	 * @param numWolves number of wolves to transfer
	 * @return number of transferred wolves
	 */
	private int sendWolf(Player sendTo, int numWolves) {
		int count = 0;
		if (numWolves > 0) {
			for (Wolf w : this.getWolves()) {
				if (numWolves < 1) break;
				if (w.getOwner() == sendTo) continue;

				w.setOwner(sendTo);
				w.setSitting(false);
				numWolves--;
				count++;
			}
		} else {
			this.player.sendMessage("Number must be greater than 0");
		}
		return count;
	}

	void wolfStats(Player sendTo, Player fetchFrom) {
		Map<String, Integer> AdultPup = new HashMap<>();
		Map<String, Integer> SitStand = new HashMap<>();
		Map<String, Integer> ColorArray = new HashMap<>();
		WolfPlayer wp = new WolfPlayer(fetchFrom);
		List<Wolf> wps = wp.getWolves();

		sendTo.sendMessage(ChatColor.BLUE + ".: Wolf Stats for " + ChatColor.RED + fetchFrom.getDisplayName() + ChatColor.RESET + ChatColor.BLUE + " :." + ChatColor.RESET);
		sendTo.sendMessage(ChatColor.GRAY + "Total tamed wolves: " + ChatColor.DARK_AQUA + wps.size() + ChatColor.RESET);

		if (wps.size() < 1) return;

		for (Wolf w : wps) {
			// Adult:Pup
			if (w.isAdult())
				if (AdultPup.containsKey("ADULT")) AdultPup.put("ADULT", AdultPup.get("ADULT") + 1);
				else AdultPup.put("ADULT", 1);
			else if (AdultPup.containsKey("PUP")) AdultPup.put("PUP", AdultPup.get("PUP") + 1);
			else AdultPup.put("PUP", 1);

			// Sitting:Standing
			if (w.isSitting())
				if (SitStand.containsKey("SITTING")) SitStand.put("SITTING", SitStand.get("SITTING") + 1);
				else SitStand.put("SITTING", 1);
			else if (SitStand.containsKey("STANDING")) SitStand.put("STANDING", SitStand.get("STANDING") + 1);
			else SitStand.put("STANDING", 1);

			// Color Array
			if (ColorArray.containsKey(w.getCollarColor().name()))
				ColorArray.put(w.getCollarColor().name(), ColorArray.get(w.getCollarColor().name()) + 1);
			else ColorArray.put(w.getCollarColor().name(), 1);
		}

		double TOTAL = wps.size();
		int ADULT = (AdultPup.getOrDefault("ADULT", 0));
		int PUP = (AdultPup.getOrDefault("PUP", 0));
		int SITTING = (SitStand.getOrDefault("SITTING", 0));
		int STANDING = (SitStand.getOrDefault("STANDING", 0));

		sendTo.sendMessage(ChatColor.GREEN + "-- Adults / Pups --" + ChatColor.RESET);
		String AP = String.valueOf(ChatColor.GRAY) + "Adults: " + ChatColor.DARK_AQUA + ADULT + ChatColor.AQUA + " (" + Math.round((ADULT / TOTAL) * 100) + "%)" +
				ChatColor.BLACK + " | " +
				ChatColor.GRAY + "Pups: " + ChatColor.DARK_AQUA + PUP + ChatColor.AQUA + " (" + Math.round((PUP / TOTAL) * 100) + "%)" + ChatColor.RESET;
		sendTo.sendMessage(AP);

		sendTo.sendMessage(ChatColor.GREEN + "-- Sitting / Standing --" + ChatColor.RESET);
		String SS = String.valueOf(ChatColor.GRAY) + "Sitting: " + ChatColor.DARK_AQUA + SITTING + ChatColor.AQUA + " (" + Math.round((SITTING / TOTAL) * 100) + "%)" +
				ChatColor.BLACK + " | " +
				ChatColor.GRAY + "Standing: " + ChatColor.DARK_AQUA + STANDING + ChatColor.AQUA + " (" + Math.round((STANDING / TOTAL) * 100) + "%)" + ChatColor.RESET;
		sendTo.sendMessage(SS);

		sendTo.sendMessage(ChatColor.GREEN + "-- Collar Color Array --" + ChatColor.RESET);
		StringBuilder COLOR = new StringBuilder();
		for (Map.Entry<String, Integer> item : ColorArray.entrySet()) {
			String color = item.getKey();
			int amount = item.getValue();
			COLOR.append(ChatColor.GRAY).append(color.toUpperCase()).append(": ").append(ChatColor.DARK_AQUA).append(amount).append(ChatColor.AQUA).append(" (").append(Math.round((amount / TOTAL) * 100)).append("%) ").append(ChatColor.RESET);
		}
		sendTo.sendMessage(COLOR.toString());

	}

	/**
	 * Heal tamed wolf router
	 */
    int healWolfRouter(String group, boolean withInventory) {
		int affected = 0;
		switch (group) {
			case "ALL":
				affected = healWolf(withInventory);
				break;
			case "SITTING":
				affected = healWolf(withInventory, true);
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
		return affected;
	}

	/**
	 * Class to sort food quality for `wolf heal`
	 */
	@SuppressWarnings("NullableProblems")
	class Food implements Comparable<Food> {
		private final int quality;
		private final ItemFood mItem;
		private final ItemStack bItem;
		private int quantity;

		Food(ItemFood mI, ItemStack bI, int quantity, int quality) {
			this.mItem = mI;
			this.bItem = bI;
			this.quantity = quantity;
			this.quality = quality;
		}

		public ItemFood getNMSItem() {
			return this.mItem;
		}

		ItemStack getBukkitItem() {
			return this.bItem;
		}

		int getQuality() {
			return this.quality;
		}

		int getQuantity() {
			return this.quantity;
		}

		void decreaseQuantity() {
			this.quantity--;
		}

		@Override
		public int compareTo(final Food o) {
			return this.quality - o.getQuality();
		}
	}

	private boolean wolfHealed(Wolf w) {
		double wolfHealth = w.getHealth();
		double origWolfHealth = wolfHealth;
		double wolfMaxHealth = w.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
		Player p = (Player) w.getOwner();

		ArrayList<Food> f = new ArrayList<>();
		for (ItemStack i : p.getInventory().getContents()) {
			if (i != null && i.getType().isEdible()) {
				net.minecraft.server.v1_12_R1.ItemStack itemStack = CraftItemStack.asNMSCopy(i);
				ItemFood itemFood = (ItemFood) itemStack.getItem();
				int quality = itemFood.getNutrition(itemStack);

				f.add(new Food(itemFood, i, i.getAmount(), quality));
			}
		}

		Collections.sort(f);
		Map<ItemStack, Integer> useMe = new HashMap<>();
		while (wolfHealth < wolfMaxHealth) {
			Food feedFood = null;
			double healthDiff = wolfMaxHealth - wolfHealth;
			for (Food food : f) {
				if (food.getQuantity() < 1) continue;
				feedFood = food;
				if (food.getQuality() > healthDiff) {
					break;
				}
			}
			if (feedFood == null) break;

			feedFood.decreaseQuantity();
			wolfHealth = wolfHealth + feedFood.getQuality();
			ItemStack feedFoodObj = feedFood.getBukkitItem();

			if (useMe.containsKey(feedFoodObj)) {
				int amount = useMe.get(feedFoodObj) + 1;
				useMe.put(feedFoodObj, amount);
			} else {
				useMe.put(feedFoodObj, 1);
			}
		}

		if (wolfHealth == origWolfHealth) return false;
		if (useMe.size() < 1) return false;

		Inventory inv = p.getInventory();
		for (Map.Entry<ItemStack, Integer> item : useMe.entrySet()) {
			ItemStack putItemStack = item.getKey();
			int removeItemAmount = item.getValue();

			while (removeItemAmount > 0) {
				int location = inv.first(putItemStack);
				ItemStack it = inv.getItem(location);
				int itAmount = it.getAmount();
				if (removeItemAmount >= itAmount) {
					it = null;
					removeItemAmount = removeItemAmount - itAmount;
				} else {
					itAmount = itAmount - removeItemAmount;
					it.setAmount(itAmount);
					removeItemAmount = 0;
				}
				inv.setItem(location, it);
			}
		}
		return true;
	}


	private void HEAL_WOLF(Wolf w, boolean withInventory){
		if (withInventory) {
			if (wolfHealed(w)) {
				w.setHealth(w.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
			}
		} else {
			w.setHealth(w.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
		}
	}

	/**
	 * Heals as many wolves as it can with(out) Inventory
	 *
	 * @param withInv whether to deduct inventory or not
	 * @return number of healed wolves
	 */
	private int healWolf(boolean withInv) {
		int count = 0;
		for (Wolf w : this.getWolves()) {
			if (w.getHealth() == w.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()) continue;
			this.HEAL_WOLF(w,withInv);
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
	private int healWolf(boolean withInv, boolean isSitting) {
		int count = 0;
		for (Wolf w : this.getWolves()) {
			if ((w.isSitting() == isSitting) & (w.getHealth() != w.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue())) {
				this.HEAL_WOLF(w,withInv);
				count++;
			}
		}
		return count;
	}

	/**
	 * Heals specified number of wolves with(out) Inventory
	 *
	 * @param color   color of wolf collar to heal
	 * @param withInv whether to deduct inventory or not
	 * @return number of healed wolves
	 */
	private int healWolf(boolean withInv, DyeColor color) {
		int count = 0;
		for (Wolf w : this.getWolves()) {
			if (w.getCollarColor() != color) continue;
			if (w.getHealth() == w.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()) continue;

			this.HEAL_WOLF(w,withInv);
			count++;
		}
		return count;
	}


	/**
	 * Untames tamed wolves based on state
	 *
	 * @param    isSitting    desired state of wolves
	 */
    int untameWolf(boolean isSitting) {
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
	 *
	 * @param    number    desired number of wolves
	 */
    int untameWolf(int number) {
		int numWolves = this.getWolves().size();

		if (number >= 0) {
			numWolves = number;
		} else {
			numWolves = numWolves + number; // Add number since negative already
		}

		int count = 0;
		for (Wolf w : this.getWolves()) {
			if (numWolves < 1) break;
			if (w.isTamed()) {
				this.setUntame(w);
				numWolves--;
				count++;
			}
		}
		return count;
	}

	/**
	 * Untames tamed wolves based on their collar color
	 *
	 * @param    c    specified DyeColor
	 */
    int untameWolf(DyeColor c) {
		int numWolves = 0;

		for (Wolf w : this.getWolves()) {
			if (w.getCollarColor() == c) {
				this.setUntame(w);
				numWolves++;
			}
		}

		return numWolves;
	}


	/**
	 * Untames an individual wolf
	 *
	 * @param target wolf entity to untame
	 */
    void untameWolf(LivingEntity target) {
		for (Wolf w : this.getWolves()) {
			if (target.getUniqueId() == w.getUniqueId()) {
				this.setUntame(w);
			}
		}
	}

	/**
	 * Set tamed wolf to standing then untames them
	 *
	 * @param    w    wolf to untame
	 */
	private void setUntame(Wolf w) {
		w.setSitting(false); // Make stand before untaming to prevent AI breaking
		w.setTamed(false);
	}

	/**
	 * Teleports standing wolves to Player
	 *
	 * @return number of wolves that return to player
	 */
    int returnToPlayer() {
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
	 *
	 * @return List of currently tamed wolves
	 */
    List<Wolf> getWolves() {
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
	 *
	 * @param target the LivingEntity in player crosshair
	 */
    void setTarget(LivingEntity target) {
		   WolfCommand wc =  (WolfCommand) Bukkit.getPluginManager().getPlugin("WolfCommand");
		   int distance = wc.getConfig().getInt("WOLF_ATTACK_DISTANCE");

		for (Wolf w : this.getWolves()) {
			if (!w.isSitting()) {
				w.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(distance);
				w.setTarget(target);
			}
		}
	}

	/**
	 * Gets the closest tamed wolf in player's crosshair
	 *
	 * @return target
	 */
    LivingEntity getWolfTarget() {
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
	 *
	 * @return target
	 */
    LivingEntity getTarget() {
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
