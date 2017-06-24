package net.jfabricationgames.bunkers_and_badasses.game_frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.jfabricationgames.toolbox.graphic.ImageLoader;
import com.jfabricationgames.toolbox.graphic.ImagePanel;

import net.miginfocom.swing.MigLayout;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Font;

public class ErrorDialog extends JDialog {
	
	private static final long serialVersionUID = 82135828331446041L;
	
	private final JPanel contentPanel = new JPanel();
	
	private static ImageLoader imageLoader;
	private static BufferedImage errorImage;
	
	static {
		imageLoader = new ImageLoader();
		errorImage = imageLoader.loadImage("net/jfabricationgames/bunkers_and_badasses/images/others/error_img_1.jpg");
	}
	
	public ErrorDialog(String errorText) {
		setResizable(false);
		setAlwaysOnTop(true);
		setTitle("Fehler - Bunkers and Badasses");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit().getImage(ErrorDialog.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/jfg/icon.png")));
		setBounds(100, 100, 500, 450);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.DARK_GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[grow]", "[grow]"));
		{
			JPanel panel = new JPanel();
			panel.setBackground(Color.GRAY);
			contentPanel.add(panel, "cell 0 0,grow");
			panel.setLayout(new MigLayout("", "[20px][grow][20px]", "[200px][10px][grow][10px]"));
			
			ImagePanel panel_image = new ImagePanel(errorImage);
			panel_image.setAdaptSizeKeepProportion(true);
			panel_image.setCentered(true);
			panel_image.setBackground(Color.GRAY);
			panel.add(panel_image, "cell 0 0 3 1,grow");
			
			JScrollPane scrollPane = new JScrollPane();
			panel.add(scrollPane, "cell 1 2,grow");
			
			JTextArea txtrError = new JTextArea();
			txtrError.setBackground(Color.LIGHT_GRAY);
			txtrError.setWrapStyleWord(true);
			txtrError.setLineWrap(true);
			txtrError.setEditable(false);
			txtrError.setFont(new Font("Tahoma", Font.PLAIN, 13));
			txtrError.setText(errorText);
			scrollPane.setViewportView(txtrError);
		}
	}
}