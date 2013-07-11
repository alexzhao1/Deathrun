package com.bobjoejim.deathrun;

import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.player.PlayerInteractEvent;

public class DeathrunSigns implements Listener { // TODO: make another sign class

	private Deathrun plugin;
	@EventHandler
	public void createClassSign(SignChangeEvent event) {
		String[] lines = event.getLines();
		if (lines[0].equalsIgnoreCase("[deathrun]") || lines[0].equalsIgnoreCase("[dr]")) {
			event.setLine(0, ChatColor.GOLD+"[Deathrun]");
			String signType = lines[1];
			if (signType.equalsIgnoreCase("join")) {
				event.setLine(1, "Enter Deathrun");
				event.setLine(2, "match");
			} else if (signType.equalsIgnoreCase("leave")) {
				event.setLine(1, "Leave lobby");
			} else if (signType.equalsIgnoreCase("info")) {
				event.setLine(1, "Map: TESTMAP"); // TODO: will be changed when multiple arenas. var mapName
				event.setLine(2, ""); // players. TODO: live updating
				event.setLine(3, ""); // info (including time). TODO: live updating
				this.plugin.infoSigns.add(event.getBlock().getLocation());
			} else if (signType.equalsIgnoreCase("spectate")) {
				event.setLine(1, "Spectate match");
			} else {
				event.setLine(1, ChatColor.RED+"Invalid line");
			}
		}
	}
	@EventHandler
	public void useClassSign(PlayerInteractEvent event) {
		Action action = event.getAction();
		Player player = event.getPlayer();
		Block block = event.getClickedBlock();
		if (action == Action.RIGHT_CLICK_BLOCK && block.getType().equals(Material.SIGN_POST)
				|| action == Action.RIGHT_CLICK_BLOCK && block.getType().equals(Material.WALL_SIGN)) {
			Sign sign = (Sign) block.getState();
			if (sign.getLine(0).equals(ChatColor.GOLD+"[Deathrun]")) {
				String signType = sign.getLine(1);
				if (signType.equals("Enter Deathrun")) {
					DeathrunCommandExecutor.join(player);
				} else if (signType.equals("Leave lobby")) {
					player.teleport(this.plugin.getServer().getWorlds().get(0).getSpawnLocation());
				} else if (signType.contains("Map: ")) {
					event.setCancelled(true);
					// do absolutely nothing. yay.
				} else if (signType.equals("Spectate match")) {
					// TODO: set up spectating
				} else {
					plugin.getLogger().log(Level.WARNING, "Error occured with sign at "+sign.getX()+", "+sign.getY()+", "+sign.getZ());
				}
			}
		}
	}
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if (event.getBlock().getType().equals(Material.SIGN_POST)
				|| event.getBlock().getType().equals(Material.WALL_SIGN)) {
			Sign sign = (Sign) event.getBlock().getState();
			if (sign.getLine(0).equals(ChatColor.GOLD+"[Deathrun]")) {
				if (sign.getLine(1).contains("Map: ")) {
					this.plugin.infoSigns.remove(sign.getLocation());
				}
			}
		}
	}
}
