package com.bobjoejim.deathrun;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
public class DeathrunListeners implements Listener {
	@EventHandler
	public void onPlayerDamage(EntityDamageByEntityEvent event) {
		Entity damager = event.getDamager();
		Entity hurt = event.getEntity();
		if (damager instanceof Player && hurt instanceof Player) {
			if (DeathrunCommandExecutor.isInGame((Player) damager) && DeathrunCommandExecutor.isInGame((Player) hurt)) {
				if (DeathrunTeams.isDeath((Player) damager) && DeathrunTeams.isDeath((Player) hurt)) {
					event.setCancelled(true);
				} else if (DeathrunTeams.isRunner((Player) damager) && DeathrunTeams.isRunner((Player) hurt)) {
					event.setCancelled(true);
				}
			}
		}
	}
	@EventHandler
	public void onPlayerRegen(EntityRegainHealthEvent event) {
		if (event.getEntityType() == EntityType.PLAYER) {
			Player p = (Player) event.getEntity();
			if (DeathrunCommandExecutor.isInGame(p)) {
				event.setCancelled(true);
			}
		}
	}
	@EventHandler
	public void onItemPickup(PlayerPickupItemEvent event) {
		Player p = event.getPlayer();
		if (DeathrunCommandExecutor.isInGame(p)) {
			event.setCancelled(true);
		}
	}
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		Player p = event.getPlayer();
		if (DeathrunCommandExecutor.isInGame(p)) {
			p.teleport(DeathrunCommandExecutor.lobbyStartPoint);
			p.sendMessage(DeathrunCommandExecutor.prefix+ChatColor.RED+"You died! Spectate by right-clicking the \"Spectate\" sign.");
			
		}
	}
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player p = event.getPlayer();
		if (DeathrunCommandExecutor.isInGame(p)) {
			p.setHealth(0);
			if (DeathrunTeams.isDeath(p)) {
				DeathrunCommandExecutor.deaths.remove(p);
				p.setWalkSpeed(0);
			}
			if (DeathrunTeams.isRunner(p)) DeathrunCommandExecutor.runners.remove(p);
			DeathrunCommandExecutor.players.remove(p);
			DeathrunCommandExecutor.keepInv.remove(p);
			Bukkit.getServer().broadcastMessage(DeathrunCommandExecutor.prefix+ChatColor.GREEN+p.getName()+" was killed for leaving the game!");
		}
	}
}