package net.jfabricationgames.bunkers_and_badasses.game_turn_cards;

import java.awt.Color;

import com.jfabricationgames.toolbox.graphic.ImagePanel;

public class TurnGoalCardPanel extends ImagePanel {
	
	private static final long serialVersionUID = 6735151457780957843L;
	
	private TurnGoal turnGoal;
	
	public TurnGoalCardPanel() {
		this(null);
	}
	public TurnGoalCardPanel(TurnGoal turnGoal) {
		this.turnGoal = turnGoal;
		setBackground(Color.GRAY);
		setCentered(true);
		setAdaptSizeKeepProportion(true);
		if (turnGoal != null) {
			setImage(turnGoal.getImage());
			setToolTipText(turnGoal.getDescription());			
		}
		repaint();
	}
	
	public void setTurnGoal(TurnGoal turnGoal) {
		this.turnGoal = turnGoal;
		if (turnGoal != null) {
			setImage(turnGoal.getImage());
			setToolTipText(turnGoal.getDescription());
			repaint();			
		}
	}
	public TurnGoal getTurnGoal() {
		return turnGoal;
	}
	
	public void setMarked(boolean marked) {
		if (marked) {
			setBackground(Color.LIGHT_GRAY);
		}
		else {
			setBackground(Color.GRAY);
		}
	}
}