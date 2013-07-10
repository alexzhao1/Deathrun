package com.bobjoejim.deathrun.tasks;

import org.bukkit.scheduler.BukkitRunnable;

import com.bobjoejim.deathrun.Deathrun;

public class CheckQueueTask extends BukkitRunnable {

  private Deathrun plugin;
	public CheckQueueTask(Deathrun plugin) {
		this.plugin = plugin;
	}
	public void run() {
		if (this.plugin.queue.size() >= this.plugin.maxPlayers) {
			
		} else {
			
		}
	}
}
