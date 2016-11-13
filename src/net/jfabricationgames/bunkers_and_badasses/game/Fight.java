package net.jfabricationgames.bunkers_and_badasses.game;

import java.util.List;

import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.jfabricationgames.bunkers_and_badasses.game_character.Hero;
import net.jfabricationgames.bunkers_and_badasses.user.User;

public class Fight {
	
	private User attackingPlayer;
	private User defendingPlayer;
	
	private List<Field> attackSupporters;
	private List<Field> defenceSupporters;
	
	private Hero attackingHero;
	private Hero defendingHero;
	
	private boolean useAttackingHeroEffect;
	private boolean useDefendingHeroEffect;
	
	private Field attackingField;
	private Field defendingField;
	
	private static final int ATTACKERS = 1;
	private static final int DEFENDERS = 2;
	
	public void askForHero() {
		//TODO
	}
	
	public void receiveHero(Hero hero, int group) {
		//TODO
	}
	
	public void executeBattle() {
		//TODO
	}
	
	public void executeHerosEffect(Hero hero) {
		//TODO
	}
}