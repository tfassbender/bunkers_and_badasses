package net.jfabricationgames.bunkers_and_badasses.game_turn_cards;

import java.awt.Color;

import com.jfabricationgames.toolbox.graphic.ImagePanel;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class TurnBonusCardPanel extends ImagePanel {
	
	private static final long serialVersionUID = -3347751961668475675L;
	
	private TurnBonus turnBonus;
	
	private TurnBonusCardSelectionListener selectionListener;
	
	private boolean changeColorOnFocusEvent = false;
	
	public TurnBonusCardPanel(TurnBonus turnBonus) {
		addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				if (selectionListener != null) {
					selectionListener.turnBonusCardSelected(turnBonus);
				}
				if (changeColorOnFocusEvent) {
					setBackground(Color.LIGHT_GRAY);
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				setBackground(Color.GRAY);
			}
		});
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
	
	public TurnBonusCardSelectionListener getSelectionListener() {
		return selectionListener;
	}
	public void setSelectionListener(TurnBonusCardSelectionListener selectionListener) {
		this.selectionListener = selectionListener;
	}
	
	public boolean isChangeColorOnFocusEvent() {
		return changeColorOnFocusEvent;
	}
	public void setChangeColorOnFocusEvent(boolean changeColorOnFocusEvent) {
		this.changeColorOnFocusEvent = changeColorOnFocusEvent;
	}
}