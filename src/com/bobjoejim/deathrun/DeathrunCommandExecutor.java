package com.bobjoejim.deathrun;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DeathrunCommandExecutor extends Deathrun implements CommandExecutor {
	public DeathrunCommandExecutor(Deathrun plugin) {} // TODO: worldedit functionality (ex: not just one block)
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("deathrun")) { // main command
			if (args.length==0 || args[0].equalsIgnoreCase("help")) { // if no args or "help" then help. TODO: help pages
				//help
				return true;
			} else if (args[0].equalsIgnoreCase("forcestart")) { // forcestart the game
					if (sender.hasPermission("deathrun.forcestart")) {
						DeathrunStartEnd.drStart();
						return true;
					} else {
						sender.sendMessage(prefix+ChatColor.RED+"You don't have permission!");
						return false;
					}
				}
			} else if (args[0].equalsIgnoreCase("join")) { //TODO: add queue, add other people
				if (sender.hasPermission("deathrun.join")) {
					if (!isInGame(p)) {
						queue.add(p);
						keepInv.add(p.getInventory().getContents());
						sender.sendMessage(prefix+ChatColor.GREEN+"Successfully added you to the queue. You will be notified when your match is ready.");
						return true;
					} else {
						sender.sendMessage(prefix+ChatColor.RED+"You're already in a game!");
						return false;
					}
				} else {
					sender.sendMessage(prefix+ChatColor.RED+"You don't have permission!");
					return false;
				}
			} else if (args[0].equalsIgnoreCase("startpoint")) { // set start point TODO: args for locations
				if (sender.hasPermission("deathrun.startpoint")) {
					if (args.length==2) {
						if (args[1].equalsIgnoreCase("death")) {
							deathStartPoint = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ());
							sender.sendMessage(prefix+ChatColor.GREEN+"Death start location set at "+deathStartPoint.toString()+" successfully!");
							return true;
						} else if (args[1].equalsIgnoreCase("runner")) {
							runnerStartPoint = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ());
							sender.sendMessage(prefix+ChatColor.GREEN+"Runner start location set at "+runnerStartPoint.toString()+" successfully!");
							return true;
						} else if (args[1].equalsIgnoreCase("lobby")) {
							lobbyStartPoint = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ());
							sender.sendMessage(prefix+ChatColor.GREEN+"Lobby start location set at "+lobbyStartPoint.toString()+" successfully!");
							return true;
						} else {
							sender.sendMessage(prefix+ChatColor.RED+"Location must be death, runner or lobby!");
							return false;
						}
					} else {
						sender.sendMessage(prefix+ChatColor.RED+"Review your arguments count! /dr startpoint [death/runner/lobby]");
						return false;
					}
				} else {
					sender.sendMessage(prefix+ChatColor.RED+"You don't have permission!");
					return false;
				}
			} else if (args[0].equalsIgnoreCase("endpoint")) {
				if (sender.hasPermission("deathrun.endpoint")) {
					if (args.length==2) {
						if (args[1].equalsIgnoreCase("minigame")) {
							minigameTpPoint = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ());
							sender.sendMessage(prefix+ChatColor.GREEN+"Minigame TP location set at "+minigameTpPoint.toString()+" successfully!");
							return true;
						} else if (args[1].equalsIgnoreCase("endgame")) {
							gameEndPoint = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ());
							sender.sendMessage(prefix+ChatColor.GREEN+"Game end location set at "+gameEndPoint.toString()+" successfully!");
							return true;
						} else {
							sender.sendMessage(prefix+ChatColor.RED+"Location must be either minigame or endgame!");
							return false;
						}
					} else {
						sender.sendMessage(prefix+ChatColor.RED+"Review your arguments count! /dr endpoint [minigame/endgame]");
						return false;
					}
				} else {
					sender.sendMessage(prefix+ChatColor.RED+"You don't have permission!");
					return false;
				}
			} else if (args[0].equalsIgnoreCase("set")) { // set player as death or runner TODO: recode to incorporate players instead of deaths/runners
				if (args.length==3) {
					if (sender.hasPermission("deathrun.set")) {
						if (Bukkit.getServer().getPlayer(args[1])!=null) {
							if (isInGame(Bukkit.getServer().getPlayer(args[1]))) {
								int code=-1;
								if (args[2].equalsIgnoreCase("death")) {
									setDeath(Bukkit.getServer().getPlayer(args[1]), code);
									if (code==1) {
										sender.sendMessage(prefix+ChatColor.GREEN+"Successfully moved player from runner to death!");
										return true;
									} else if (code==2) {
										sender.sendMessage(prefix+ChatColor.RED+"That player is already a death!");
										return false;
									} else if (code==3) {
										sender.sendMessage(prefix+ChatColor.GREEN+"Successfully added player to death!");
									}
								} else if (args[2].equalsIgnoreCase("runner")) {
									setRunner(Bukkit.getServer().getPlayer(args[1]), code);
									if (code==1) {
										sender.sendMessage(prefix+ChatColor.GREEN+"Successfully moved player from death to runner!");
										return true;
									} else if (code==2) {
										sender.sendMessage(prefix+ChatColor.RED+"That player is already a runner!");
										return false;
									} else if (code==3) {
										sender.sendMessage(prefix+ChatColor.GREEN+"Successfully added player to runner!");
									}
								} else {
									sender.sendMessage(prefix+ChatColor.RED+"Team must be either death or runner!");
									return false;
								}
							} else {
								sender.sendMessage(prefix+ChatColor.RED+"That person is not in a Deathrun game!");
								return false;
							}
						} else {
							sender.sendMessage(prefix+ChatColor.RED+"That player isn't on the server!");
							return false;
						}
					} else {
						sender.sendMessage(prefix+ChatColor.RED+"You don't have permission!");
						return false;
					}
				} else {
					sender.sendMessage(prefix+ChatColor.RED+"Review your arguments count! /dr set [player] [death/runner]");
					return false;
				}
			} else if (args[0].equalsIgnoreCase("reload")) {
				this.reloadConfig();
				sender.sendMessage(prefix+ChatColor.GREEN+"Successfully reloaded.");
				return true;
			}
		return false;
	}
	public static void setDeath(Player p, int code) {
		if (runners.contains(p) && !deaths.contains(p)) { // in runners, not deaths
			runners.remove(p);
			deaths.add(p);
			code=0;
		}
		if (!runners.contains(p) && deaths.contains(p)) { // in deaths, not runners
			code=1;
		}
		if (!runners.contains(p) && !deaths.contains(p)) { // in neither, but in players
			deaths.add(p);
			code=2;
		}
		code=-1;
	}
	public static void setRunner(Player p, int code) {
		if (deaths.contains(p) && !runners.contains(p)) { // in runners, not deaths
			deaths.remove(p);
			runners.add(p);
			code=0;
		}
		if (!deaths.contains(p) && runners.contains(p)) { // in deaths, not runners
			code=1;
		}
		if (!deaths.contains(p) && !runners.contains(p)) { // in neither, but in players
			runners.add(p);
			code=2;
		}
		code=-1;
	}
	public static boolean isInGame(Player p) {
		if (players.contains(p)) {
			return true;
		}
		return false;
	}
}