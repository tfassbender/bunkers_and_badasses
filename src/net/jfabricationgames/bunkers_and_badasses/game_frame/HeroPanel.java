package net.jfabricationgames.bunkers_and_badasses.game_frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;

import net.jfabricationgames.bunkers_and_badasses.game.Game;
import net.jfabricationgames.bunkers_and_badasses.game_character.hero.Hero;
import net.miginfocom.swing.MigLayout;

public class HeroPanel extends JPanel {
	
	private static final long serialVersionUID = 1419669675329077343L;
	
	private DefaultListModel<Hero> heroesListModel = new DefaultListModel<Hero>();
	
	private JButton btnEinsetzen;
	
	public HeroPanel(GameFrame gameFrame) {
		setBackground(Color.GRAY);

		setLayout(new MigLayout("", "[grow][grow]", "[][5px][grow][]"));
		
		JLabel lblHeroes = new JLabel("Helden Karten:");
		lblHeroes.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblHeroes, "cell 0 0 2 1,alignx center");
		
		JScrollPane scrollPane_heroes = new JScrollPane();
		scrollPane_heroes.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		add(scrollPane_heroes, "cell 0 2 2 1,grow");
		
		JList<Hero> list_heroes = new JList<Hero>(heroesListModel);
		list_heroes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_heroes.setFont(new Font("Tahoma", Font.PLAIN, 12));
		list_heroes.setBackground(Color.LIGHT_GRAY);
		scrollPane_heroes.setViewportView(list_heroes);
		
		JButton btnbersicht = new JButton("\u00DCbersicht");
		btnbersicht.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				gameFrame.getSelectHeroCardFrame().update();
				gameFrame.getSelectHeroCardFrame().setVisible(true);
				gameFrame.getSelectHeroCardFrame().requestFocus();
				gameFrame.getSelectHeroCardFrame().setCardSelectionEnabled(false, null);
			}
		});
		btnbersicht.setToolTipText("<html>\r\n\u00DCbersicht \u00FCber die vorhandenen <br>\r\nHelden Karten\r\n</html>");
		btnbersicht.setBackground(Color.GRAY);
		
		add(btnbersicht, "cell 0 3,alignx center");
		
		btnEinsetzen = new JButton("Einsetzen");
		btnEinsetzen.setEnabled(false);
		btnEinsetzen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameFrame.getSelectHeroCardFrame().setVisible(true);
				gameFrame.getSelectHeroCardFrame().requestFocus();
				gameFrame.getSelectHeroCardFrame().setCardSelectionEnabled(true, gameFrame);
			}
		});
		btnEinsetzen.setToolTipText("<html>\r\nEine der vorhandenen Helden Karten<br>\r\n(deren Spezialfunktion) einsetzen\r\n</html>");
		btnEinsetzen.setBackground(Color.GRAY);
		add(btnEinsetzen, "cell 1 3,alignx center");
	}
	
	public JButton getBtnEinsetzen() {
		return btnEinsetzen;
	}
	public void setBtnEinsetzen(JButton btnEinsetzen) {
		this.btnEinsetzen = btnEinsetzen;
	}
	
	public void updateHeroCards(Game game) {
		heroesListModel.removeAllElements();
		List<Hero> heros = game.getHeroCardManager().getHeroCards(game.getLocalUser());
		for (int i = 0; i < heros.size(); i++) {
			heroesListModel.addElement(heros.get(i));
		}
	}
}