package net.jfabricationgames.bunkers_and_badasses.game_frame;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import net.jfabricationgames.bunkers_and_badasses.game.Game;
import net.jfabricationgames.bunkers_and_badasses.game.UserResource;
import net.jfabricationgames.bunkers_and_badasses.user.User;
import net.miginfocom.swing.MigLayout;

public class ResourceInfoPanel extends JPanel {
	
	private static final long serialVersionUID = 8182279704090012287L;
	
	private JLabel lblLC;
	private JLabel lblGC;
	private JLabel lblUC;
	private JLabel lblLA;
	private JLabel lblGA;
	private JLabel lblUA;
	private JLabel lblLE;
	private JLabel lblGE;
	private JLabel lblUE;
	
	public ResourceInfoPanel() {
		setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		setBackground(Color.GRAY);
		setLayout(new MigLayout("", "[right][grow,center][grow,center][grow,center]", "[][grow][][grow][grow][grow][grow]"));
		
		JLabel lblResourcen = new JLabel("Resourcen:");
		lblResourcen.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblResourcen, "cell 0 0 4 1,alignx center");
		
		JLabel lblbrigeResourcen = new JLabel("\u00DCbrig:");
		lblbrigeResourcen.setToolTipText("Im Moment vorhandene Resourcen");
		lblbrigeResourcen.setFont(new Font("Tahoma", Font.PLAIN, 12));
		add(lblbrigeResourcen, "cell 1 2");
		
		JLabel lblErhaltnchsteRunde = new JLabel("Erhalt:");
		lblErhaltnchsteRunde.setToolTipText("<html>\r\nMomentaner Erhalt an Resourcen zu<br>\r\nBeginn der n\u00E4chsten Runde. (Resourcen<br>\r\nGewinnungsbefehle nicht ber\u00FCcksichtigt)<br>\r\n</html>");
		lblErhaltnchsteRunde.setFont(new Font("Tahoma", Font.PLAIN, 12));
		add(lblErhaltnchsteRunde, "cell 2 2");
		
		JLabel lblVerbrauchletzteRunde = new JLabel("Verbrauch:");
		lblVerbrauchletzteRunde.setToolTipText("<html>\r\nVerbrauchte Resourcen (f\u00FCr Befehle, <br>\r\nFelder, ...) in der letzten Runde\r\n</html>");
		lblVerbrauchletzteRunde.setFont(new Font("Tahoma", Font.PLAIN, 12));
		add(lblVerbrauchletzteRunde, "cell 3 2");
		
		JLabel labelCredits = new JLabel("Credits:");
		labelCredits.setFont(new Font("Tahoma", Font.PLAIN, 12));
		add(labelCredits, "cell 0 3");
		
		lblLC = new JLabel("");
		add(lblLC, "cell 1 3");
		
		lblGC = new JLabel("");
		add(lblGC, "cell 2 3");
		
		lblUC = new JLabel("");
		add(lblUC, "cell 3 3");
		
		JLabel labelMunition = new JLabel("Munition:");
		labelMunition.setFont(new Font("Tahoma", Font.PLAIN, 12));
		add(labelMunition, "cell 0 4");
		
		lblLA = new JLabel("");
		add(lblLA, "cell 1 4");
		
		lblGA = new JLabel("");
		add(lblGA, "cell 2 4");
		
		lblUA = new JLabel("");
		add(lblUA, "cell 3 4");
		
		JLabel labelEridium = new JLabel("Eridium:");
		labelEridium.setFont(new Font("Tahoma", Font.PLAIN, 12));
		add(labelEridium, "cell 0 5");
		
		lblLE = new JLabel("");
		add(lblLE, "cell 1 5");
		
		lblGE = new JLabel("");
		add(lblGE, "cell 2 5");
		
		lblUE = new JLabel("");
		add(lblUE, "cell 3 5");
	}
	
	public void updateResources(Game game, User user) {
		if (game == null || user == null) {
			lblLC.setText("");
			lblLA.setText("");
			lblLE.setText("");
			lblGC.setText("");
			lblGA.setText("");
			lblGE.setText("");
			lblUC.setText("");
			lblUA.setText("");
			lblUE.setText("");
		}
		else {
			//update the resources left
			UserResource resource = game.getResourceManager().getResources().get(user);
			lblLC.setText(Integer.toString(resource.getCredits()));
			lblLA.setText(Integer.toString(resource.getAmmo()));
			lblLE.setText(Integer.toString(resource.getEridium()));
			//update the resources the user gets through buildings
			int[] resources = game.getResourceManager().getResources().get(game.getLocalUser()).calculateTurnStartResources(game);
			lblGC.setText(Integer.toString(resources[0]));
			lblGA.setText(Integer.toString(resources[1]));
			lblGE.setText(Integer.toString(resources[2]));
			//update the resources the user used in the last turn
			UserResource resourcesUsed = game.getResourceManager().getResourceUse().get(user).get(game.getTurnManager().getTurn()-1);
			if (resourcesUsed != null) {
				lblUC.setText(Integer.toString(resourcesUsed.getCredits()));
				lblUA.setText(Integer.toString(resourcesUsed.getAmmo()));
				lblUE.setText(Integer.toString(resourcesUsed.getEridium()));			
			}
			else {
				lblUC.setText("-");
				lblUA.setText("-");
				lblUE.setText("-");
			}			
		}
	}
}