package net.jfabricationgames.bunkers_and_badasses.game_frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.jfabricationgames.bunkers_and_badasses.game.ConfirmDialogListener;
import net.miginfocom.swing.MigLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ConfirmDialog extends JDialog {
	
	private static final long serialVersionUID = -4318946299112098631L;
	
	private final JPanel contentPanel = new JPanel();
	private JPanel panel_1;
	
	public ConfirmDialog(String text, ConfirmDialogListener listener, int confirmType) {
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setAlwaysOnTop(true);
		setResizable(false);
		setTitle("Bestätigung - Bunkers and Badasses");
		setBounds(100, 100, 400, 150);
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
			panel.setLayout(new MigLayout("", "[grow][][grow]", "[grow][][][grow]"));
			
			JLabel lblMessage = new JLabel(text);
			lblMessage.setFont(new Font("Tahoma", Font.BOLD, 16));
			panel.add(lblMessage, "cell 0 1 3 1,alignx center");
			
			panel_1 = new JPanel();
			panel_1.setBackground(Color.GRAY);
			panel.add(panel_1, "cell 1 2,grow");
			panel_1.setLayout(new MigLayout("", "[][]", "[]"));
			
			JButton btnOk = new JButton("Ok");
			btnOk.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					listener.receiveConfirmAnswer(true, confirmType);
					dispose();
				}
			});
			btnOk.setBackground(Color.GRAY);
			panel_1.add(btnOk, "cell 0 0");
			
			JButton btnAbbrechen = new JButton("Abbrechen");
			btnAbbrechen.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					listener.receiveConfirmAnswer(false, confirmType);
					dispose();
				}
			});
			btnAbbrechen.setBackground(Color.GRAY);
			panel_1.add(btnAbbrechen, "cell 1 0");
		}
	}
}