package com.bobjoejim.deathrun;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class GameStartTask extends BukkitRunnable { //TODO: put this in a class .-.

	private final Deathrun plugin;
	public GameStartTask(Deathrun plugin) {
		this.plugin = plugin;
	}
	public void run() {
		/*if (waitTime<=10) {
			for (Player p : players) {
				p.sendMessage(waitTime+" seconds until the game starts.");
				waitTime -= 10;
			}
		}*/
	}
}