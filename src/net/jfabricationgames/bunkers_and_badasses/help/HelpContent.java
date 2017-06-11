package net.jfabricationgames.bunkers_and_badasses.help;

import java.awt.Dimension;
import java.io.Serializable;

public class HelpContent implements Serializable {
	
	private static final long serialVersionUID = -3670398768849519337L;
	
	private String title;
	private String content;
	private String imagePath;
	
	private String panelName;
	
	private int[] imagePreferedSize;
	private Dimension imageMinimumSize;
	
	private int id;
	private int superId;
	
	public HelpContent(String title, String content, String imagePath, String panelName, int[] imagePreferedSize, Dimension imageMinimumSize, int id, int superId) {
		this.title = title;
		this.content = content;
		this.imagePath = imagePath;
		this.panelName = panelName;
		this.imagePreferedSize = imagePreferedSize;
		this.imageMinimumSize = imageMinimumSize;
		this.id = id;
		this.superId = superId;
		if (imagePreferedSize[0] <= 0) {
			imagePreferedSize = null;
		}
		if (imageMinimumSize.getWidth() <= 0) {
			imageMinimumSize = null;
		}
	}
	
	public boolean isPanel() {
		return content != null && !content.isEmpty();
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
	public int getId() {
		return id;
	}
	public int getSuperId() {
		return superId;
	}
}