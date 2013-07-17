package com.bobjoejim.deathrun;

import java.awt.Color;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class DeathrunStartEnd extends Deathrun {
	private static Random random = new Random();
	public static boolean inProgress = false;
	public static boolean deathsWin = false;
	
	public DeathrunStartEnd(Deathrun plugin) {}
	public static void enterLobby() {
		final int waitTime = Deathrun.waitTime;
		for (int i=0;i<players.size();i++) {
			players.get(i).teleport(lobbyStartPoint);
			for (Player p : players) {
				p.sendMessage(waitTime+" seconds until the game starts.");
			}
		}
	}
	public static void drStart() { // TODO: cancel tasks
		boolean isOneDeath; // setting runners/deaths
		if (players.size()<=6) {
			isOneDeath=true;
		} else {
			isOneDeath=false;
		}
		int code = 0;
		for (int i=0;i<players.size();i++) {
			DeathrunTeams.setRunner(players.get(i), code);
		}
		DeathrunTeams.setDeath(players.get(random.nextInt(players.size())), code);
		if (!isOneDeath) {
			DeathrunTeams.setDeath(runners.get(random.nextInt(runners.size())), code);
		}
		DeathrunTeams.initRunner();
		DeathrunTeams.initDeath();
	}
	/*public static void checkQueue() { // TODO: FIX THE QUEUE IT'S COMPLETELY BROKEN I THINK
		if (queue.size()>=Deathrun.minPlayers) {
			if (queue.size()>=Deathrun.maxPlayers) { // if queue size exceeds max players
				for (int i=0;i<Deathrun.maxPlayers;i++) {
					players.add(queue.get(0));
					queue.remove(0);
					players.get(0).sendMessage(prefix+ChatColor.GREEN+"Your Deathrun match is ready, you will be sent there now! Good luck!");
				}
				for (Player p : queue) {
					p.sendMessage(prefix+ChatColor.GREEN+"You are now number "+(queue.indexOf(p)+1)+" in the Deathrun queue.");
				}
				drStart();
			}
		}
	}*/
	public static void checkQueue() {
		if (queue.size() >= Deathrun.minPlayers) {
			if (queue.size() >= Deathrun.maxPlayers) {
				for (int i=0;i<Deathrun.maxPlayers;i++) {
					
				}
			}
		}
		/*
		 * TODO: add a task for this.
		 * ok here's the plan:
		 * when queue size > min players send a message/start a task
		 * basically either after set time limit or queue size > maxPlayers game starts
		 * this task: when waitTime ends start the game.
		 * another task where periodic messages: "Game will start when _ more players join"?
		 * but yeah that's basically it. also update all infosigns
		 */
	}
	public static void drEnd() {
		for (Player p : deaths) {
			p.setWalkSpeed(0);
		}
		for (int i=0;i<players.size();i++) {
			players.get(i).getInventory().setContents(keepInv.get(i));
		}
	}
	public static void resetGame() {
		for (int i=0;i<players.size();i++) {
			players.get(i).getInventory().clear();
			players.get(i).getInventory().setContents(keepInv.get(i));
			players.get(i).teleport(gameEndPoint);
		}
		players.clear();
		keepInv.clear();
		deaths.clear();
		runners.clear();
	}
}