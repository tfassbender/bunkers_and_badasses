package net.jfabricationgames.bunkers_and_badasses.help;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import com.jfabricationgames.toolbox.graphic.ImageLoader;
import com.jfabricationgames.toolbox.graphic.ImagePanel;

import net.miginfocom.swing.MigLayout;

public class HelpMenuPanel extends JPanel {
	
	private static final long serialVersionUID = 6239258115998278377L;
	
	private String title;
	private String content;
	private String imagePath;
	
	private String panelName;
	
	private static BufferedImage sign;
	private static ImageLoader loader;
	
	static {
		loader = new ImageLoader();
		loader.setDefaultPathPrefix("net/jfabricationgames/bunkers_and_badasses/images/");
		sign = loader.loadImage("game_frame/logo_2.png");
	}
	
	public HelpMenuPanel(HelpContent content) {
		this(content.getTitle(), content.getContent(), content.getImagePath(), content.getPanelName(), content.getImagePreferedSize(), content.getImageMinimumSize());
	}
	public HelpMenuPanel(String title, String content, String imagePath, String panelName, int[] imagePreferedSize, Dimension imageMinimumSize) {
		this.title = title;
		this.content = content;
		this.imagePath = imagePath;
		this.panelName = panelName;
		
		setBackground(Color.GRAY);
		if (imagePreferedSize != null) {
			setLayout(new MigLayout("", "[500px,grow][:" + imagePreferedSize[0] + "px:500px,grow]", "[][10px:n:10px][grow][" + imagePreferedSize[1] + "px][grow]"));
		}
		else {
			setLayout(new MigLayout("", "[500px,grow][:300px:500px,grow]", "[][10px:n:10px][grow][300px][grow]"));
		}
		setLayout(new MigLayout("", "[500px,grow][:150px:350px,grow][150px:n:150px,grow]", "[][10px:n:10px][grow][300px][grow][150px:n:150px,grow]"));
		
		JLabel lblTitle = new JLabel("Title");
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
		add(lblTitle, "cell 0 0 3 1,alignx center");
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, "cell 0 2 1 4,grow");
		
		JTextPane txtpnTest = new JTextPane();
		txtpnTest.setBackground(Color.LIGHT_GRAY);
		txtpnTest.setEditable(false);
		txtpnTest.setText("Some text\n\n more Text\nlanger text ....... bla bla bla blabla bla bla blabla bla bla blabla bla bla blabla bla bla");
		scrollPane.setViewportView(txtpnTest);
		
		ImagePanel panel;
		if (imagePath != null) {
			panel = new ImagePanel(imagePath);
		}
		else {
			panel = new ImagePanel();
		}
		if (imageMinimumSize != null) {
			panel.setMinimumSize(imageMinimumSize);
		}
		panel = new ImagePanel();
		panel.setBackground(Color.GRAY);
		panel.setCentered(true);
		panel.setAdaptSizeKeepProportion(true);
		add(panel, "cell 1 3 2 1,grow");
		
		ImagePanel panel_1 = new ImagePanel(sign);
		panel_1.setAdaptSizeKeepProportion(true);
		panel_1.setCentered(true);
		panel_1.setBackground(Color.GRAY);
		add(panel_1, "cell 2 5,grow");
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
}