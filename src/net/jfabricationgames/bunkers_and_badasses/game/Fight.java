package net.jfabricationgames.bunkers_and_badasses.game;

import java.util.List;

import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.jfabricationgames.bunkers_and_badasses.game_character.hero.Hero;
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
	
	public int getAttackingStrength() {
		//TODO
		return 0;
	}
	public int getDefendingStrength() {
		//TODO
		return 0;
	}
	
	public User getAttackingPlayer() {
		return attackingPlayer;
	}
	public void setAttackingPlayer(User attackingPlayer) {
		this.attackingPlayer = attackingPlayer;
	}
	
	public User getDefendingPlayer() {
		return defendingPlayer;
	}
	public void setDefendingPlayer(User defendingPlayer) {
		this.defendingPlayer = defendingPlayer;
	}
	
	public List<Field> getAttackSupporters() {
		return attackSupporters;
	}
	public void setAttackSupporters(List<Field> attackSupporters) {
		this.attackSupporters = attackSupporters;
	}
	
	public List<Field> getDefenceSupporters() {
		return defenceSupporters;
	}
	public void setDefenceSupporters(List<Field> defenceSupporters) {
		this.defenceSupporters = defenceSupporters;
	}
	
	public Hero getAttackingHero() {
		return attackingHero;
	}
	public void setAttackingHero(Hero attackingHero) {
		this.attackingHero = attackingHero;
	}
	
	public Hero getDefendingHero() {
		return defendingHero;
	}
	public void setDefendingHero(Hero defendingHero) {
		this.defendingHero = defendingHero;
	}
	
	public boolean isUseAttackingHeroEffect() {
		return useAttackingHeroEffect;
	}
	public void setUseAttackingHeroEffect(boolean useAttackingHeroEffect) {
		this.useAttackingHeroEffect = useAttackingHeroEffect;
	}
	
	public boolean isUseDefendingHeroEffect() {
		return useDefendingHeroEffect;
	}
	public void setUseDefendingHeroEffect(boolean useDefendingHeroEffect) {
		this.useDefendingHeroEffect = useDefendingHeroEffect;
	}
	
	public Field getAttackingField() {
		return attackingField;
	}
	public void setAttackingField(Field attackingField) {
		this.attackingField = attackingField;
	}
	
	public Field getDefendingField() {
		return defendingField;
	}
	public void setDefendingField(Field defendingField) {
		this.defendingField = defendingField;
	}
}