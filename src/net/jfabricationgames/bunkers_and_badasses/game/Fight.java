package net.jfabricationgames.bunkers_and_badasses.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.jfabricationgames.bunkers_and_badasses.game_character.hero.Hero;
import net.jfabricationgames.bunkers_and_badasses.user.User;

public class Fight {
	
	private User attackingPlayer;
	private User defendingPlayer;
	
	private Field attackingField;
	private Field defendingField;
	
	private int battleState;
	private int winner;
	
	private List<Field> possibleSupporters;
	private List<Field> supportRejections;
	private List<Field> attackSupporters;
	private List<Field> defenceSupporters;
	
	private int attackingNormalTroops;
	private int attackingBadassTroops;
	
	private int attackingSupportStrength;
	private int defendingSupportStrength;
	
	private int currentAttackingStrength;
	private int currentDefendingStrength;
	
	private Hero attackingHero;
	private Hero defendingHero;
	
	private boolean useAttackingHeroEffect;
	private boolean useDefendingHeroEffect;
	
	private boolean attackingHeroChosen;
	private boolean defendingHeroChosen;
	
	private Field retreatField;
	private boolean retreatFieldChosen;
	
	private int fallingTroopsTotal;
	private int fallingTroopsLooser;
	private Map<Field, Integer> fallingTroopsSupport;
	private boolean fallingTroopsChosen;
	
	private Map<Field, int[]> fallenTroops;
	private boolean fallenTroopsChosen;
	
	public static final int ATTACKERS = 1;
	public static final int DEFENDERS = 2;
	
	public static final int STATE_SUPPORT = 1;
	public static final int STATE_HEROS = 2;
	public static final int STATE_RETREAT_FIELD = 3;
	public static final int STATE_FALLEN_TROOP_SELECTION = 4;
	public static final int STATE_FALLEN_TROOP_REMOVING = 5;
	
	public Fight() {
		possibleSupporters = new ArrayList<Field>();
		supportRejections = new ArrayList<Field>();
		attackSupporters = new ArrayList<Field>();
		defenceSupporters = new ArrayList<Field>();
		fallingTroopsSupport = new HashMap<Field, Integer>();
		fallenTroops = new HashMap<Field, int[]>();
	}
	
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
	
	public int[] calculateFallingTroops() {
		//TODO
		return null;
	}
	public int calculateMaxFallingSupportTroops() {
		//TODO
		return 0;
	}
	
	public List<Field> calculateRetreatFields() {
		//TODO
		return null;
	}
	
	public void calculateCurrentStrength() {
		//TODO
	}
	
	public List<Field> getLoosingSupporters() {
		if (winner == ATTACKERS) {
			return defenceSupporters;
		}
		else if (winner == DEFENDERS) {
			return attackSupporters;
		}
		else {
			return null;
		}
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
	
	public int getBattleState() {
		return battleState;
	}
	public void setBattleState(int battleState) {
		this.battleState = battleState;
	}
	
	public int getWinner() {
		return winner;
	}
	public void setWinner(int winner) {
		this.winner = winner;
	}
	
	public List<Field> getPossibleSupporters() {
		return possibleSupporters;
	}
	public void setPossibleSupporters(List<Field> possibleSupporters) {
		this.possibleSupporters = possibleSupporters;
	}
	
	public List<Field> getSupportRejections() {
		return supportRejections;
	}
	public void setSupportRejections(List<Field> supportRejections) {
		this.supportRejections = supportRejections;
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
	
	public int getAttackingNormalTroops() {
		return attackingNormalTroops;
	}
	public void setAttackingNormalTroops(int attackingNormalTroops) {
		this.attackingNormalTroops = attackingNormalTroops;
	}
	
	public int getAttackingBadassTroops() {
		return attackingBadassTroops;
	}
	public void setAttackingBadassTroops(int attackingBadassTroops) {
		this.attackingBadassTroops = attackingBadassTroops;
	}
	
	public int getAttackingSupportStrength() {
		return attackingSupportStrength;
	}
	public void setAttackingSupportStrength(int attackingSupportStrength) {
		this.attackingSupportStrength = attackingSupportStrength;
	}
	
	public int getDefendingSupportStrength() {
		return defendingSupportStrength;
	}
	public void setDefendingSupportStrength(int defendingSupportStrength) {
		this.defendingSupportStrength = defendingSupportStrength;
	}
	
	public int getCurrentAttackingStrength() {
		return currentAttackingStrength;
	}
	public void setCurrentAttackingStrength(int currentAttackingStrength) {
		this.currentAttackingStrength = currentAttackingStrength;
	}
	
	public int getCurrentDefendingStrength() {
		return currentDefendingStrength;
	}
	public void setCurrentDefendingStrength(int currentDefendingStrength) {
		this.currentDefendingStrength = currentDefendingStrength;
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
	
	public boolean isAttackingHeroChosen() {
		return attackingHeroChosen;
	}
	public void setAttackingHeroChosen(boolean attackingHeroChosen) {
		this.attackingHeroChosen = attackingHeroChosen;
	}
	
	public boolean isDefendingHeroChosen() {
		return defendingHeroChosen;
	}
	public void setDefendingHeroChosen(boolean defendingHeroChosen) {
		this.defendingHeroChosen = defendingHeroChosen;
	}
	
	public Field getRetreatField() {
		return retreatField;
	}
	public void setRetreatField(Field retreatField) {
		this.retreatField = retreatField;
	}
	
	public boolean isRetreatFieldChosen() {
		return retreatFieldChosen;
	}
	public void setRetreatFieldChosen(boolean retreatFieldChosen) {
		this.retreatFieldChosen = retreatFieldChosen;
	}
	
	public int getFallingTroopsTotal() {
		return fallingTroopsTotal;
	}
	public void setFallingTroopsTotal(int fallingTroopsTotal) {
		this.fallingTroopsTotal = fallingTroopsTotal;
	}
	
	public int getFallingTroopsLooser() {
		return fallingTroopsLooser;
	}
	public void setFallingTroopsLooser(int fallingTroopsLooser) {
		this.fallingTroopsLooser = fallingTroopsLooser;
	}
	
	public Map<Field, Integer> getFallingTroopsSupport() {
		return fallingTroopsSupport;
	}
	public void setFallingTroopsSupport(Map<Field, Integer> fallingTroopsSupport) {
		this.fallingTroopsSupport = fallingTroopsSupport;
	}
	
	public boolean isFallenTroopsChosen() {
		return fallenTroopsChosen;
	}
	public void setFallenTroopsChosen(boolean fallenTroopsChosen) {
		this.fallenTroopsChosen = fallenTroopsChosen;
	}
	
	public Map<Field, int[]> getFallenTroops() {
		return fallenTroops;
	}
	public void setFallenTroops(Map<Field, int[]> fallenTroops) {
		this.fallenTroops = fallenTroops;
	}
	
	public boolean isFallingTroopsChosen() {
		return fallingTroopsChosen;
	}
	public void setFallingTroopsChosen(boolean fallingTroopsChosen) {
		this.fallingTroopsChosen = fallingTroopsChosen;
	}
}