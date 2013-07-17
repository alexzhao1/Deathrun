package com.bobjoejim.deathrun;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class DeathrunTeams extends Deathrun {

	public DeathrunTeams(Deathrun plugin) {}
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
