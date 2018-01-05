package net.jfabricationgames.bunkers_and_badasses.game_frame;

import javax.swing.JDialog;
import java.awt.Color;
import java.awt.Toolkit;
import net.miginfocom.swing.MigLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class InfoDialog extends JDialog {
	
	private static final long serialVersionUID = -5224943555971779368L;
	
	public InfoDialog(String headline, String infoText) {
		setTitle("Info - Bunkers and Badasses");
		setIconImage(Toolkit.getDefaultToolkit().getImage(InfoDialog.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/jfg/icon.png")));
		setBackground(Color.DARK_GRAY);
		setAlwaysOnTop(true);
		setBounds(100, 100, 400, 300);
		setResizable(false);
		
		setLocationRelativeTo(null);
		
		getContentPane().setBackground(Color.DARK_GRAY);
		getContentPane().setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		getContentPane().add(panel, "cell 0 0,grow");
		panel.setLayout(new MigLayout("", "[grow]", "[][][grow][]"));
		
		JLabel lblHeadline = new JLabel(headline);
		lblHeadline.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel.add(lblHeadline, "cell 0 0,alignx center");
		
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane, "cell 0 2,grow");
		
		JTextArea txtrInfoText = new JTextArea();
		txtrInfoText.setText(infoText);
		txtrInfoText.setWrapStyleWord(true);
		txtrInfoText.setLineWrap(true);
		txtrInfoText.setBackground(Color.LIGHT_GRAY);
		txtrInfoText.setEditable(false);
		scrollPane.setViewportView(txtrInfoText);
		
		JButton btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnOk.setBackground(Color.GRAY);
		panel.add(btnOk, "cell 0 3,alignx center");
	}
}