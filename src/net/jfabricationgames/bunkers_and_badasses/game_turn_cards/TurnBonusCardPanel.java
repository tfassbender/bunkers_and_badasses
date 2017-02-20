package net.jfabricationgames.bunkers_and_badasses.game_turn_cards;

import java.awt.Color;

import com.jfabricationgames.toolbox.graphic.ImagePanel;

public class TurnBonusCardPanel extends ImagePanel {
	
	private static final long serialVersionUID = -3347751961668475675L;
	
	private TurnBonus turnBonus;
	
	public TurnBonusCardPanel(TurnBonus turnBonus) {
		this.turnBonus = turnBonus;
		setBackground(Color.GRAY);
		setCentered(true);
		setAdaptSizeKeepProportion(true);
		setImage(turnBonus.getImage());
		setToolTipText(turnBonus.getDescription());
		repaint();
	}
	
	public void setTurnBonus(TurnBonus turnBonus) {
		this.turnBonus = turnBonus;
		setImage(turnBonus.getImage());
		setToolTipText(turnBonus.getDescription());
		repaint();
	}
	public TurnBonus getTurnBonus() {
		return turnBonus;
	}
}