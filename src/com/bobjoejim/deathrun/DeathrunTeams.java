package com.bobjoejim.deathrun;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class DeathrunTeams extends DeathrunCommandExecutor {

	public DeathrunTeams(Deathrun plugin) {
		super(plugin);
	}
	public static void initDeath() {
		for (Player p : deaths) {
			p.setGameMode(GameMode.ADVENTURE);
			p.sendMessage(prefix+ChatColor.GREEN+"You are now a death, defeat the runners to win!");
			p.teleport(deathStartPoint);
			p.setWalkSpeed(1);
		}
	}
	public static void initRunner() {
		for (Player p : runners) {
			p.setGameMode(GameMode.ADVENTURE);
			p.sendMessage(prefix+ChatColor.GREEN+"You are now a runner, get to the end to win!");
			p.teleport(runnerStartPoint);
		}
	}
	public static boolean isDeath(Player p) {
		if (deaths.contains(p)) {
			return true;
		}
		return false;
	}
	public static boolean isRunner(Player p) {
		if (runners.contains(p)) {
			return true;
		}
		return false;
	}
}
