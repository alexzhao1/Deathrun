package com.bobjoejim.deathrun;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Deathrun extends JavaPlugin {
	public static String prefix = ChatColor.GOLD + "[Deathrun]";
	public static Location deathStartPoint;
	public static Location runnerStartPoint;
	public static Location lobbyStartPoint;
	public static Location gameEndPoint;
	public static Location minigameTpPoint;
	public static ArrayList<Player> players = new ArrayList<Player>();
	public static ArrayList<Player> deaths = new ArrayList<Player>();
	public static ArrayList<Player> runners = new ArrayList<Player>();
	public static ArrayList<Player> queue = new ArrayList<Player>();
	public static ArrayList<ItemStack[]> keepInv = new ArrayList<ItemStack[]>();
	public static FileConfiguration config;
	public static int minPlayers;
	public static int maxPlayers;
	public static int waitTime;
	@Override
	public void onEnable() {
		getCommand("dr").setExecutor(new DeathrunCommandExecutor(this));
		saveDefaultConfig();
		config = getConfig();
		loadConfigStuff();
		getServer().getPluginManager().registerEvents(new DeathrunListeners(), this);
		getServer().getPluginManager().registerEvents(new DeathrunSigns(), this);
		DeathrunUpdateChecker update = new DeathrunUpdateChecker(this);
		this.getServer().getScheduler().runTaskAsynchronously(this, update);
	}
	@Override
	public void onDisable() {
		
	}
	public void loadConfigStuff() {
		minPlayers = config.getInt("minplayers");
		maxPlayers = config.getInt("maxplayers");
		waitTime = config.getInt("waittime");
	}
	public boolean updateAvailable() {
		return config.getBoolean("updateAvailable");
	}
	/**
	 * TODO: Questions:
	 * lobby? ANSWER: yes
	 * reward? ANSWER: idk
	 * arenas? (multiple games at once?) ANSWER: eventually.
	 */
}
