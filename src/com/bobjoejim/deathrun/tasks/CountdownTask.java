package com.bobjoejim.deathrun.tasks;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.bobjoejim.deathrun.Deathrun;

public class CountdownTask extends BukkitRunnable {
  private ArrayList<Location> infoSigns;
	private Deathrun plugin;
	private int waitTime;
	public CountdownTask(Deathrun plugin, ArrayList<Location> infoSigns, int waitTime) {
		this.plugin = plugin;
		this.infoSigns = infoSigns;
		this.waitTime = waitTime;
	}
	public void run() {
		Sign sign;
		for (int i=0;i<infoSigns.size();i++) {
			sign = (Sign) infoSigns.get(i).getBlock();
			sign.setLine(3, ChatColor.BOLD+"Waiting "+ChatColor.RESET+" 0:"+waitTime);
			sign.update();
		}
		if (waitTime<=10) {
			for (Player p: this.plugin.players) {
				p.sendMessage(this.plugin.prefix+ChatColor.GOLD+waitTime+" seconds until the game starts!");
			}
		}
		waitTime--;
	}
	public int getWaitTime() {
		return this.waitTime;
	}
}
