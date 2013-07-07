package com.bobjoejim.deathrun;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

public class DeathrunUpdateChecker extends BukkitRunnable {
	private final String address = "https://raw.github.com/alexzhao1/Deathrun/master/plugin.yml";
	
	private Deathrun plugin = null;
	public DeathrunUpdateChecker(Deathrun plugin) {
		this.plugin = plugin;
	}
	public void run() {
		try {
			float remoteVersion = Float.parseFloat(getVersion());
			float localVersion = Float.parseFloat(plugin.getDescription().getVersion());
			if (remoteVersion > localVersion) {
				plugin.getLogger().log(Level.INFO, "An update is available! (Using: "+localVersion+" Current: "+remoteVersion);
				plugin.getConfig().set("updateAvailable", true);
			} else {
				plugin.getLogger().log(Level.INFO, "Plugin up to date! (Current: "+remoteVersion);
				plugin.getConfig().set("updateAvailable", false);
			}
		} catch (Exception e) {
			plugin.getLogger().log(Level.WARNING, "An error occured while trying to check for updates.");
		}
	}
	private String getVersion() throws Exception {
		URL url = new URL(address);
		BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
		String version = null;
		for (int i=0;i<3;i++) {
			version = br.readLine();
		}
		return version.substring(9, version.length());
	}
}
