package net.jfabricationgames.bunkers_and_badasses.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.jfabricationgames.bunkers_and_badasses.game_character.hero.Athena;
import net.jfabricationgames.bunkers_and_badasses.game_character.hero.DrZed;
import net.jfabricationgames.bunkers_and_badasses.game_character.hero.Hero;
import net.jfabricationgames.bunkers_and_badasses.game_character.hero.Hero.ExecutionType;
import net.jfabricationgames.bunkers_and_badasses.game_command.RetreatCommand;
import net.jfabricationgames.bunkers_and_badasses.user.User;

public class Fight implements Serializable {
	
	private static final long serialVersionUID = 3623504762354487614L;
	
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
	
	private int[] defendingFieldTroops;//only used for Brick
	
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
	
	private boolean blockHeroExecution;
	
	private Field retreatField;
	private boolean retreatFieldChosen;
	
	private int fallingTroopsTotal;
	private int fallingTroopsLooser;
	private int fallingTroopsWinner;
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
	public static final int STATE_FIGHT_ENDED = 6;//move everything on the board in this state
	
	public Fight() {
		possibleSupporters = new ArrayList<Field>();
		supportRejections = new ArrayList<Field>();
		attackSupporters = new ArrayList<Field>();
		defenceSupporters = new ArrayList<Field>();
		fallingTroopsSupport = new HashMap<Field, Integer>();
		fallenTroops = new HashMap<Field, int[]>();
		battleState = STATE_SUPPORT;
		blockHeroExecution = false;
	}
	
	/**
	 * Merge the fights by overriding all data with the data from the new fight.
	 * 
	 * This method is just used to keep the fight references clear.
	 * 
	 * @param fight
	 * 		The fight with the new data.
	 */
	public void merge(Fight fight) {
		attackingPlayer = fight.attackingPlayer;
		defendingPlayer = fight.defendingPlayer;
		attackingField = fight.attackingField;
		defendingField = fight.defendingField;
		battleState = fight.battleState;
		winner = fight.winner;
		possibleSupporters = fight.possibleSupporters;
		supportRejections = fight.supportRejections;
		attackSupporters = fight.attackSupporters;
		defenceSupporters = fight.defenceSupporters;
		attackingNormalTroops = fight.attackingNormalTroops;
		attackingBadassTroops = fight.attackingBadassTroops;
		attackingSupportStrength = fight.attackingSupportStrength;
		defendingSupportStrength = fight.defendingSupportStrength;
		currentAttackingStrength = fight.currentAttackingStrength;
		currentDefendingStrength = fight.currentDefendingStrength;
		attackingHero = fight.attackingHero;
		defendingHero = fight.defendingHero;
		useAttackingHeroEffect = fight.useAttackingHeroEffect;
		useDefendingHeroEffect = fight.useDefendingHeroEffect;
		attackingHeroChosen = fight.attackingHeroChosen;
		defendingHeroChosen = fight.defendingHeroChosen;
		retreatField = fight.retreatField;
		retreatFieldChosen = fight.retreatFieldChosen;
		fallingTroopsTotal = fight.fallingTroopsTotal;
		fallingTroopsLooser = fight.fallingTroopsLooser;
		fallingTroopsSupport = fight.fallingTroopsSupport;
		fallingTroopsChosen = fight.fallingTroopsChosen;
		fallenTroops = fight.fallenTroops;
		fallenTroopsChosen = fight.fallenTroopsChosen;
	}
	
	public int getAttackingStrength() {
		return currentAttackingStrength;
	}
	public int getDefendingStrength() {
		return currentDefendingStrength;
	}
	
	public int getAttackingTroopStrength() {
		return 2*attackingBadassTroops + attackingNormalTroops;
	}
	public int getDefendingTroopStrength() {
		return defendingField.getTroopStrength();
	}
	
	protected void executeHeroEffects(ExecutionType executionType) {
		List<Hero> heros = new ArrayList<Hero>(2);
		if (attackingHero != null && useAttackingHeroEffect && attackingHero.getExecutionType() == executionType) {
			heros.add(attackingHero);
		}
		if (defendingHero != null && useDefendingHeroEffect && defendingHero.getExecutionType() == executionType) {
			heros.add(defendingHero);
		}
		if (!heros.isEmpty()) {
			Comparator<Hero> byPriority = (h1, h2) -> -Integer.compare(h1.getExecutionPriority(), h2.getExecutionPriority());//reversed order
			Collections.sort(heros, byPriority);
			for (Hero hero : heros) {
				if (!blockHeroExecution) {
					//execute if not Axton blocks the execution
					hero.execute(this);					
				}
			}			
		}
	}
	
	public int[] calculateFallingTroops() {
		/*int[] fallingTroops = new int[2];
		//int overhead;
		int minTroops;//use the minimum of troops instead of the overhead
		Field winningField;
		Field loosingField;
		//calculateCurrentStrength();
		//overhead = Math.abs(currentAttackingStrength - currentDefendingStrength);
		int winningTroopStrength;
		boolean winnerHasBandits;
		if (winner == ATTACKERS) {
			winningField = attackingField;
			loosingField = defendingField;
			winningTroopStrength = attackingNormalTroops + 2*attackingBadassTroops;
			winnerHasBandits = attackingNormalTroops > 0;
		}
		else {
			winningField = defendingField;
			loosingField = attackingField;
			winningTroopStrength = defendingField.getTroopStrength();
			winnerHasBandits = defendingField.getNormalTroops() > 0;
		}
		minTroops = Math.min(getAttackingTroopStrength(), loosingField.getTroopStrength());
		//fallingTroops[0] = overhead/2;
		//fallingTroops[1] = overhead;
		fallingTroops[0] = minTroops/2;
		fallingTroops[1] = minTroops;
		//if (fallingTroops[0] == 0 && minTroops != 0) {
			//only zero troops if overhead is 0
			//fallingTroops[0] = 1;
		//}
		if (winningField.getTroops().size() == 1) {
			//attacker must remain one troop
			fallingTroops[0] = 0;
		}
		//not more falling troops that troops in the fields
		fallingTroops[1] = Math.min(fallingTroops[1], loosingField.getTroopStrength());
		//winner must remain one troop
		if (winnerHasBandits) {
			fallingTroops[1] = Math.min(fallingTroops[1], winningTroopStrength-1);			
		}
		else {
			fallingTroops[1] = Math.min(fallingTroops[1], winningTroopStrength-2);
		}
		if (fallingTroops[0] > fallingTroops[1]) {
			fallingTroops[0] = fallingTroops[1];
		}
		//if the attacker wins one attacking troop remains
		if (winner == ATTACKERS && fallingTroops[1] >= attackingNormalTroops + 2*attackingBadassTroops - 1) {
			if (winnerHasBandits) {
				fallingTroops[1] = attackingNormalTroops + 2*attackingBadassTroops - 1;
			}
			else {
				fallingTroops[1] = attackingNormalTroops + 2*attackingBadassTroops - 2;
			}
		}
		//no negative falling troops
		fallingTroops[0] = Math.max(0, fallingTroops[0]);
		fallingTroops[1] = Math.max(0, fallingTroops[1]);
		//max greater or equal min
		fallingTroops[0] = Math.min(fallingTroops[0], fallingTroops[1]);
		return fallingTroops;*/
		
		
		//new implementation based on the new rules (version 0.4.1; january 2018)
		//see project_doc/fight/falling_troops_calculation_activity.png for details
		int[] fallingTroops = new int[2];//[0]: min; [1]: max
		int maxFallingTroopsAttacker;
		int maxFallingTroopsDefender;
		
		//calculate maximum falling troops
		int attackingTroops = getAttackingTroopStrength();
		for (Field field : attackSupporters) {
			attackingTroops += field.getTroopStrength();
		}
		int defendingTroops = defendingField.getTroopStrength();
		for (Field field : defenceSupporters) {
			defendingTroops += field.getTroopStrength();
		}
		fallingTroops[1] = Math.min(attackingTroops, defendingTroops);
		maxFallingTroopsAttacker = attackingTroops;
		maxFallingTroopsDefender = defendingTroops;
		
		//winner keeps at least one troop
		boolean banditsLeft;
		if (winner == ATTACKERS) {
			banditsLeft = attackingNormalTroops > 0;
		}
		else {
			banditsLeft = defendingField.getNormalTroops() > 0;
		}
		if (winner == ATTACKERS) {
			if (banditsLeft) {
				maxFallingTroopsAttacker -= 1;
			}
			else {
				maxFallingTroopsAttacker -= 2;
			}
		}
		else {
			if (banditsLeft) {
				maxFallingTroopsDefender -= 1;
			}
			else {
				maxFallingTroopsDefender -= 2;
			}
		}
		/*if (winner == ATTACKERS && attackingTroops <= defendingTroops || winner == DEFENDERS && attackingTroops >= defendingTroops) {
			if (banditsLeft) {
				fallingTroops[1] -= 1;
			}
			else {
				fallingTroops[1] -= 2;
			}
		}*/
		
		//support troops keep at least one troop (also loosing supporters)
		/*List<Field> supportFields;
		if (attackingTroops > defendingTroops) {
			supportFields = defenceSupporters;
		}
		else {
			supportFields = attackSupporters;
		}
		for (Field field : supportFields) {
			if (field.getNormalTroops() > 0) {
				fallingTroops[1] -= 1;
			}
			else {
				fallingTroops[1] -= 2;
			}
		}*/
		for (Field field : attackSupporters) {
			if (field.getNormalTroops() > 0) {
				maxFallingTroopsAttacker -= 1;
			}
			else {
				maxFallingTroopsAttacker -= 2;
			}
		}
		for (Field field : defenceSupporters) {
			if (field.getNormalTroops() > 0) {
				maxFallingTroopsDefender -= 1;
			}
			else {
				maxFallingTroopsDefender -= 2;
			}
		}
		//choose the minimum of the falling troops
		fallingTroops[1] = Math.min(maxFallingTroopsAttacker, maxFallingTroopsDefender);
		
		//minimum is half maximum
		fallingTroops[0] = fallingTroops[1]/2;//floored by integer division
		
		//check for negative values
		fallingTroops[0] = Math.max(0, fallingTroops[0]);
		fallingTroops[1] = Math.max(0, fallingTroops[1]);
		//check for max greater or equal min
		fallingTroops[0] = Math.min(fallingTroops[0], fallingTroops[1]);
		
		return fallingTroops;
	}
	public int calculateMaxFallingSupportTroops(int totalFallingTroops, Field field) {
		/*int maximumFallingSupportTroops = totalFallingTroops/2;
		if (field.getNormalTroops() > 0) {
			maximumFallingSupportTroops = Math.min(maximumFallingSupportTroops, field.getTroopStrength()-1);			
		}
		else {
			maximumFallingSupportTroops = Math.min(maximumFallingSupportTroops, field.getTroopStrength()-2);
		}
		if (field.getTroops().size() == 1) {
			maximumFallingSupportTroops = 0;
		}
		maximumFallingSupportTroops = Math.max(maximumFallingSupportTroops, 0);
		return maximumFallingSupportTroops;*/
		
		
		//new implementation based on the new rules (version 0.4.1; january 2018)
		int maxFallingTroops = 0;
		if (field.getNormalTroops() > 0) {
			maxFallingTroops = Math.min(totalFallingTroops, field.getTroopStrength() - 1);
		}
		else {
			maxFallingTroops = Math.min(totalFallingTroops, field.getTroopStrength() -2);
		}
		return maxFallingTroops;
	}
	public int calculateFallingTroopsWinner(int totalFallingTroops) {
		if (winner == DEFENDERS) {
			Field winningField = getWinningField();
			if (winningField.getNormalTroops() > 0) {
				return Math.min(totalFallingTroops, winningField.getTroopStrength()-1);
			}
			else {
				return Math.min(totalFallingTroops, winningField.getTroopStrength()-2);
			}			
		}
		else {
			//use just the attacking troops
			if (getAttackingNormalTroops() > 0) {
				return Math.min(totalFallingTroops, getAttackingTroopStrength()-1);
			}
			else {
				return Math.min(totalFallingTroops, getAttackingTroopStrength()-2);
			}
		}
	}
	
	public int calculateFallingTroopsSkagFight() {
		int fallingTroops = getDefendingStrength();//as much troops as there are skags
		fallingTroops = Math.min(fallingTroops, attackingNormalTroops + 2*attackingBadassTroops - 1);//one attacker survives
		if (attackingNormalTroops == 0 && fallingTroops > attackingBadassTroops*2 - 2) {
			fallingTroops = 0;//if there are only crimson raiders one of them survives
		}
		return fallingTroops;
	}
	public int[] calculateFallingTroopsSkagFightLost() {
		int[] fallingTroops = new int[] {attackingNormalTroops + 2*attackingBadassTroops, attackingNormalTroops + 2*attackingBadassTroops};
		if (getDefendingStrength() - fallingTroops[1] <= 0) {
			fallingTroops[1] = getDefendingStrength()-1;//one skag survives			
		}
		return fallingTroops;
	}
	
	public List<Field> calculateRetreatFields() {
		List<Field> retreatFields = new ArrayList<Field>();
		if (winner == ATTACKERS) {
			for (Field field : defendingField.getNeighbours()) {
				if (field.getAffiliation() != null && field.getAffiliation().equals(defendingPlayer)) {
					retreatFields.add(field);
				}
			}
		}
		else {
			//check if the attack comes from a distant field (via Scooter's Catch-A-Ride)
			boolean distantAttack = true;
			for (Field field : defendingField.getNeighbours()) {
				distantAttack &= !(field.getName().equals(attackingField.getName()));
			}
			if (distantAttack) {
				//retreat fields are all fields that are connected to the attacking and defending field (there has to be at least one) 
				for (Field field : defendingField.getNeighbours()) {
					if (field.getAffiliation() != null && field.getAffiliation().equals(attackingPlayer)) {
						boolean isMidField = false;
						for (Field midField : attackingField.getNeighbours()) {
							isMidField |= midField.getName().equals(field.getName());
						}
						if (isMidField) {
							retreatFields.add(field);							
						}
					}
				}
			}
			else {
				retreatFields.add(attackingField);				
			}
		}
		return retreatFields;
	}
	
	public boolean retreatPossible() {
		boolean retreatPossible = false;
		for (Field field : defendingField.getNeighbours()) {
			retreatPossible |= field.getAffiliation() != null && field.getAffiliation().equals(defendingPlayer);
		}
		return retreatPossible;
	}
	
	public void calculateCurrentStrength() {
		//attacking troops
		currentAttackingStrength = attackingNormalTroops + 2*attackingBadassTroops;
		attackingSupportStrength = 0;
		//attacking support
		for (Field supporter : attackSupporters) {
			attackingSupportStrength += supporter.getTroopStrength();
		}
		currentAttackingStrength += attackingSupportStrength;
		//attacking hero
		if (!blockHeroExecution && attackingHero != null && !useAttackingHeroEffect && battleState >= STATE_HEROS) {
			currentAttackingStrength += attackingHero.getAttack();
		}
		//defending troops and building
		currentDefendingStrength = defendingField.getDefenceStrength();
		defendingSupportStrength = 0;
		//defending support
		for (Field supporter : defenceSupporters) {
			defendingSupportStrength += supporter.getTroopStrength();
		}
		currentDefendingStrength += defendingSupportStrength;
		//defend command (already added by getDefenceStrength())
		/*if (defendingField.getCommand() != null && defendingField.getCommand() instanceof DefendCommand) {
			currentDefendingStrength++;
		}*/
		//defending hero
		if (!blockHeroExecution && defendingHero != null && !useDefendingHeroEffect && battleState >= STATE_HEROS) {
			currentDefendingStrength += defendingHero.getDefence();
		}
		
		//hero effects (Bloodwing)
		executeHeroEffects(ExecutionType.DURING_FIGHT);
	}
	
	public void calculateWinner() {
		calculateCurrentStrength();
		if (currentAttackingStrength > currentDefendingStrength) {
			winner = ATTACKERS;
		}
		else {
			winner = DEFENDERS;
		}
	}
	
	public List<Field> getWinningSupporters() {
		if (winner == ATTACKERS) {
			return attackSupporters;
		}
		else if (winner == DEFENDERS) {
			return defenceSupporters;
		}
		else {
			return null;
		}
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
	
	public User getWinningPlayer() {
		if (winner == ATTACKERS) {
			return attackingPlayer;
		}
		else if (winner == DEFENDERS) {
			return defendingPlayer;
		}
		else {
			return null;
		}
	}
	public User getLoosingPlayer() {
		if (winner == ATTACKERS) {
			return defendingPlayer;
		}
		else if (winner == DEFENDERS) {
			return attackingPlayer;
		}
		else {
			return null;
		}
	}
	
	public void addFallenTroops(Map<Field, int[]> troops) {
		if (fallenTroops == null) {
			fallenTroops = troops;
		}
		else {
			for (Field field : troops.keySet()) {
				fallenTroops.put(field, troops.get(field));
			}
		}
	}
	
	public boolean allSupportersAnswered() {
		return getAttackSupporters().size() + getDefenceSupporters().size() + getSupportRejections().size() == getPossibleSupporters().size();
	}
	
	public boolean isAllFallenTroopsChosen() {
		//TODO check for multiple fields
		return getFallenTroops().keySet().size() >= getFallingTroopsSupport().keySet().size() + 2;
	}
	
	public Field getWinningField() {
		if (winner == ATTACKERS) {
			return attackingField;
		}
		else if (winner == DEFENDERS) {
			return defendingField;
		}
		else {
			return null;
		}
	}
	public Field getLoosingField() {
		if (winner == ATTACKERS) {
			return defendingField;
		}
		else if (winner == DEFENDERS) {
			return attackingField;
		}
		else {
			return null;
		}
	}
	
	public boolean isRetreatAnnounced() {
		return defendingField != null && defendingField.getCommand() != null && defendingField.getCommand() instanceof RetreatCommand;
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
	
	public int[] getDefendingFieldTroops() {
		return defendingFieldTroops;
	}
	public void setDefendingFieldTroops(int[] defendingFieldTroops) {
		this.defendingFieldTroops = defendingFieldTroops;
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
	
	public boolean isBlockHeroExecution() {
		return blockHeroExecution;
	}
	public void setBlockHeroExecution(boolean blockHeroExecution) {
		this.blockHeroExecution = blockHeroExecution;
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
		int falling = fallingTroopsTotal;
		if (getDefendingHero() != null && isUseDefendingHeroEffect() && getDefendingHero() instanceof DrZed ||
				getAttackingHero() != null && isUseAttackingHeroEffect() && getAttackingHero() instanceof DrZed) {
			falling = fallingTroopsTotal / 2;
		}
		return falling;
	}
	public void setFallingTroopsTotal(int fallingTroopsTotal) {
		this.fallingTroopsTotal = fallingTroopsTotal;
	}
	
	public int getFallingTroopsLooser() {
		int falling = fallingTroopsLooser;
		if (getWinner() == ATTACKERS && getDefendingHero() != null && isUseDefendingHeroEffect() && getDefendingHero() instanceof Athena ||
				getWinner() == DEFENDERS && getAttackingHero() != null && isUseAttackingHeroEffect() && getAttackingHero() instanceof Athena) {
			falling = 0;
		}
		else if (getDefendingHero() != null && isUseDefendingHeroEffect() && getDefendingHero() instanceof DrZed ||
				getAttackingHero() != null && isUseAttackingHeroEffect() && getAttackingHero() instanceof DrZed) {
			falling = fallingTroopsLooser / 2;
		}
		return falling;
	}
	public void setFallingTroopsLooser(int fallingTroopsLooser) {
		this.fallingTroopsLooser = fallingTroopsLooser;
	}
	
	public int getFallingTroopsWinner() {
		return fallingTroopsWinner;
	}
	public void setFallingTroopsWinner(int fallingTroopsWinner) {
		this.fallingTroopsWinner = fallingTroopsWinner;
	}
	
	/**
	 * Return a (maybe changed, by hero effects) copy of the falling troop support map.
	 * 
	 * WARNING: if you want to change the values in the fight you need to use the set method after applying changes to the map
	 */
	public Map<Field, Integer> getFallingTroopsSupport() {
		Map<Field, Integer> fallingTroops = new HashMap<Field, Integer>();
		fallingTroops.putAll(fallingTroopsSupport);
		if (getWinner() == ATTACKERS && getDefendingHero() != null && isUseDefendingHeroEffect() && getDefendingHero() instanceof Athena ||
				getWinner() == DEFENDERS && getAttackingHero() != null && isUseAttackingHeroEffect() && getAttackingHero() instanceof Athena) {
			User player;
			if (getWinner() == ATTACKERS) {
				player = getDefendingPlayer();
			}
			else {
				player = getAttackingPlayer();
			}
			for (Field field : fallingTroops.keySet()) {
				if (field.getAffiliation() != null && field.getAffiliation().equals(player)) {
					fallingTroops.put(field, 0);
				}
			}
		}
		else if (getDefendingHero() != null && isUseDefendingHeroEffect() && getDefendingHero() instanceof DrZed ||
				getAttackingHero() != null && isUseAttackingHeroEffect() && getAttackingHero() instanceof DrZed) {
			User user;
			if (getDefendingHero() != null && isUseDefendingHeroEffect() && getDefendingHero() instanceof DrZed) {
				user = getDefendingPlayer();
			}
			else {
				user = getAttackingPlayer();
			}
			for (Field field : fallingTroops.keySet()) {
				if (field.getAffiliation() != null && field.getAffiliation().equals(user)) {
					int fallingSupport = fallingTroops.get(field);
					fallingTroops.put(field, fallingSupport / 2 + (fallingSupport % 2 == 0 ? 0 : 1));
				}
			}
		}
		return fallingTroops;
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