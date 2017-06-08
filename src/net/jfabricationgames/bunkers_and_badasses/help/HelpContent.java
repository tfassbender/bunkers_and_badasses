package net.jfabricationgames.bunkers_and_badasses.help;

import java.awt.Dimension;

public class HelpContent {
	
	private String title;
	private String content;
	private String imagePath;
	
	private String panelName;
	
	private int[] imagePreferedSize;
	private Dimension imageMinimumSize;
	
	public HelpContent(String title, String content, String imagePath, String panelName, int[] imagePreferedSize, Dimension imageMinimumSize) {
		this.title = title;
		this.content = content;
		this.imagePath = imagePath;
		this.panelName = panelName;
		this.imagePreferedSize = imagePreferedSize;
		this.imageMinimumSize = imageMinimumSize;
	}
	
	public boolean isPanel() {
		return !content.isEmpty();
	}
	
	public String getTitle() {
		return title;
	}
	public String getContent() {
		return content;
	}
	public String getImagePath() {
		return imagePath;
	}
	public String getPanelName() {
		return panelName;
	}
	public int[] getImagePreferedSize() {
		return imagePreferedSize;
	}
	public Dimension getImageMinimumSize() {
		return imageMinimumSize;
	}
}