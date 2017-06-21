package net.jfabricationgames.bunkers_and_badasses.game_frame;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.jfabricationgames.toolbox.graphic.ImagePanel;

import net.jfabricationgames.bunkers_and_badasses.game_character.hero.Hero;

public class HeroCardPanel extends ImagePanel {
	
	private static final long serialVersionUID = 4748614983183177771L;
	
	private Hero hero;
	
	private SelectHeroCardFrame selectionListener;
	
	public HeroCardPanel(Hero hero) {
		super(hero.getCardImage());
		this.hero = hero;
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				select();
			}
		});
		setBackground(Color.GRAY);
	}
	
	public void setSelected(boolean selected) {
		if (selected) {
			setBackground(Color.LIGHT_GRAY);
		}
		else {
			setBackground(Color.GRAY);
		}
	}
	
	private void select() {
		selectionListener.heroCardSelected(this);
	}
	
	public void addSelectionListener(SelectHeroCardFrame selectionListener) {
		this.selectionListener = selectionListener;
	}
	
	public Hero getHero() {
		return hero;
	}
}