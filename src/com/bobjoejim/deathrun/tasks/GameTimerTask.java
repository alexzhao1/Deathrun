package com.bobjoejim.deathrun.tasks;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.bobjoejim.deathrun.Deathrun;

public class GameTimerTask extends BukkitRunnable {

	private Deathrun plugin;
	private int gameLength;
	public GameTimerTask(Deathrun plugin, int gameLength) {
		this.plugin = plugin;
		this.gameLength = gameLength;
	}
	public void run() {
		if (gameLength % 60 == 0) {
			for (Player p: this.plugin.players) {
				p.sendMessage(this.plugin.prefix+ChatColor.RED+(gameLength/60)+" minutes to match end.");
			}
			gameLength--;
		}
	}
}