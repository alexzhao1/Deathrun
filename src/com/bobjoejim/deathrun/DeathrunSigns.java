package com.bobjoejim.deathrun;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class DeathrunSigns implements Listener {

	@EventHandler
	public void createClassSign(SignChangeEvent event) {
		String[] lines = event.getLines();
		if (lines[0].equalsIgnoreCase("[deathrun]") || lines[0].equalsIgnoreCase("[dr]")) {
			event.setLine(0, ChatColor.GOLD+"[Deathrun]");
			String signType = lines[1];
			if (signType.equalsIgnoreCase("join")) {
				
			} else if (signType.equalsIgnoreCase("leave")) {
				
			} else if (signType.equalsIgnoreCase("info")) {
				
			} else if (signType.equalsIgnoreCase("spectate")) {
				
			} else {
				event.setLine(1, ChatColor.RED+"Invalid line");
			}
			/*if (Kit.isKit(kitName)) {
				int price = Kit.getPrice(kitName);
				String isDonator;
				if (Kit.isDonator(kitName)) {
					isDonator = "Donator Kit";
				} else {
					isDonator = "XP Kit";
				}
				event.setLine(0, ChatColor.GREEN + "[Class]");
				event.setLine(1, kitName);
				if (price == -1) {
					event.setLine(2, "Price: Free");
				} else {
					event.setLine(2, "Price: " + price);
				}
				event.setLine(3, ChatColor.AQUA + isDonator);
			} else {
				event.setLine(1, ChatColor.RED + "Invalid Kit");
			}
		}*/
	}
	/*@EventHandler
	public void useClassSign(PlayerInteractEvent event) {
		Action action = event.getAction();
		Player player = event.getPlayer();
		Block block = event.getClickedBlock();
		if (action == Action.LEFT_CLICK_BLOCK && block.getType().equals(Material.SIGN_POST)
				|| action == Action.LEFT_CLICK_BLOCK && block.getType().equals(Material.WALL_SIGN)) {
			Sign sign = (Sign) block.getState();
			if (sign.getLine(0).equals(ChatColor.GREEN + "[Class]")) {
				if (player.hasPermission("xpbuy.signs")) {
					String kitName = sign.getLine(1).toLowerCase();
					if (Kit.isKit(kitName)) {
						if (isUpdated(sign)) {
							if (Kit.isDonator(kitName)) {
								pay(player, kitName, true);
							} else {
								pay(player, kitName, false);
							}
						} else {
							player.sendMessage(prefix + ChatColor.RED + "This kit has been updated. Click again to purchase.");
							updateSign(sign);
							event.setCancelled(true);
						}
					} else {
						player.sendMessage(prefix + ChatColor.RED + "Sorry, this kit no longer exists.");
						updateSign(sign);
					}
				} else {
					player.sendMessage(prefix + ChatColor.RED + "You don't have permission!");
					event.setCancelled(true);
				}
			}
		}*/
	}
}
