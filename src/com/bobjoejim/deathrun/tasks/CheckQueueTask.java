package com.bobjoejim.deathrun.tasks;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.bobjoejim.deathrun.*;

public class CheckQueueTask extends BukkitRunnable {

	private Deathrun plugin;
	private int waitTime;
	public CheckQueueTask(Deathrun plugin, int waitTime) {
		this.plugin = plugin;
		this.waitTime = waitTime;
	}
	public void run() {
		if (this.plugin.queue.size() >= this.plugin.maxPlayers) {
			for (Player p: this.plugin.players) {
				p.sendMessage("The game is now starting! Good luck!");
			}
			DeathrunStartEnd.drStart();
		} else {
			if (this.plugin.queue.size()>=(this.plugin.maxPlayers-this.plugin.players.size())) {
				for (int i=0;i<(this.plugin.maxPlayers-this.plugin.players.size());i++) {
					if (this.plugin.queue.isEmpty()) break;
					this.plugin.players.add(this.plugin.queue.get(i));
					this.plugin.queue.remove(i);
					this.plugin.players.get(i).sendMessage(this.plugin.prefix+ChatColor.GREEN+"Your match is ready, you will be sent there shortly. Good luck!");
				}
				if (this.plugin.players.size() < this.plugin.maxPlayers) {
					for (Player p: this.plugin.players) {
						p.sendMessage(this.plugin.prefix+ChatColor.GRAY+"There are currently "+ChatColor.GOLD+this.plugin.players.size()+
								ChatColor.GRAY+" players waiting. The game will start when "+ChatColor.GOLD+(this.plugin.maxPlayers-this.plugin.players.size())+
								ChatColor.GRAY+" more players join or in "+ChatColor.GOLD+this.waitTime+ChatColor.GRAY+" more seconds.");
						waitTime--;
					}
				}
			}
		}
	}
}