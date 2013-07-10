package com.bobjoejim.deathrun.tasks;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.bobjoejim.deathrun.Deathrun;

public class NotMaxPlayersBroadcastTask extends BukkitRunnable {
  private Deathrun plugin;
	private int waitTime;
	public NotMaxPlayersBroadcastTask(Deathrun plugin, int waitTime) {
		this.plugin = plugin;
		this.waitTime = waitTime;
	}
	public void run() {
		for (Player p: this.plugin.players) {
			p.sendMessage(this.plugin.prefix+ChatColor.GRAY+"There are currently "+ChatColor.GOLD+this.plugin.players.size()+
					ChatColor.GRAY+" players waiting. The game will start when "+ChatColor.GOLD+(this.plugin.maxPlayers-this.plugin.players.size())+
					ChatColor.GRAY+" more players join or in "+ChatColor.GOLD+this.waitTime+ChatColor.GRAY+" more seconds.");
			waitTime--;
		}
	}
}
