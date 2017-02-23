package net.jfabricationgames.bunkers_and_badasses.game_turn_cards;

import java.awt.Color;

import com.jfabricationgames.toolbox.graphic.ImagePanel;

public class TurnGoalCardPanel extends ImagePanel {
	
	private static final long serialVersionUID = 6735151457780957843L;
	
	private TurnGoal turnGoal;
	
	public TurnGoalCardPanel(TurnGoal turnGoal) {
		this.turnGoal = turnGoal;
		setBackground(Color.GRAY);
		setCentered(true);
		setAdaptSizeKeepProportion(true);
		setImage(turnGoal.getImage());
		setToolTipText(turnGoal.getDescription());
		repaint();
	}
	
	public void setTurnBonus(TurnGoal turnGoal) {
		this.turnGoal = turnGoal;
		setImage(turnGoal.getImage());
		setToolTipText(turnGoal.getDescription());
		repaint();
	}
	public TurnGoal getTurnGoal() {
		return turnGoal;
	}
}