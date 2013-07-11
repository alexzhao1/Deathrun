package com.bobjoejim.deathrun;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DeathrunUpdateChecker {
	private Deathrun plugin;
	private URL filesFeed;
	private String version;
	private String link;
	public DeathrunUpdateChecker(Deathrun plugin, String url) {
		this.plugin = plugin;
		try {
			filesFeed = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	public boolean updateAvailable() {
		try {
			InputStream input = this.filesFeed.openConnection().getInputStream();
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(input);
			Node latestFile = doc.getElementsByTagName("item").item(0);
			NodeList children = latestFile.getChildNodes();
			this.version = children.item(1).getTextContent().replaceAll("[a-zA-Z ]", "");
			this.link = children.item(3).getTextContent();
			if (!plugin.getDescription().getVersion().equals(this.version)) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public String getVersion() {
		return this.version;
	}
	public String getLink() {
		return this.link;
	}
}