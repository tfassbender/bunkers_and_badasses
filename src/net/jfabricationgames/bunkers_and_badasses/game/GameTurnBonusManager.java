package net.jfabricationgames.bunkers_and_badasses.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.jfabricationgames.bunkers_and_badasses.game_turn_cards.TurnBonus;
import net.jfabricationgames.bunkers_and_badasses.game_turn_cards.TurnBonusAmmo;
import net.jfabricationgames.bunkers_and_badasses.game_turn_cards.TurnBonusAmmoBuild;
import net.jfabricationgames.bunkers_and_badasses.game_turn_cards.TurnBonusAmmoRetreat;
import net.jfabricationgames.bunkers_and_badasses.game_turn_cards.TurnBonusCredits;
import net.jfabricationgames.bunkers_and_badasses.game_turn_cards.TurnBonusCreditsFight;
import net.jfabricationgames.bunkers_and_badasses.game_turn_cards.TurnBonusCreditsRaid;
import net.jfabricationgames.bunkers_and_badasses.game_turn_cards.TurnBonusDefendFight;
import net.jfabricationgames.bunkers_and_badasses.game_turn_cards.TurnBonusEridium;
import net.jfabricationgames.bunkers_and_badasses.game_turn_cards.TurnBonusEridiumSupport;
import net.jfabricationgames.bunkers_and_badasses.game_turn_cards.TurnBonusEridiumSupportCommand;
import net.jfabricationgames.bunkers_and_badasses.game_turn_cards.TurnBonusMarch;
import net.jfabricationgames.bunkers_and_badasses.game_turn_cards.TurnBonusMineNeutrals;
import net.jfabricationgames.bunkers_and_badasses.game_turn_cards.TurnBonusRecruit;
import net.jfabricationgames.bunkers_and_badasses.server.BunkersAndBadassesServer;
import net.jfabricationgames.bunkers_and_badasses.user.User;

public class GameTurnBonusManager implements Serializable {
	
	private static final long serialVersionUID = -6139764381035657426L;
	
	private Map<User, TurnBonus> userBonuses;
	
	private List<TurnBonus> choosableTurnBonuses;
	private List<TurnBonus> turnBonusesGame;//all the turn bonuses for the whole game
	
	private static final transient List<TurnBonus> TURN_BONUSES;
	
	static {
		if (BunkersAndBadassesServer.IS_SERVER_APPLICATION) {
			TURN_BONUSES = null;
		}
		else {
			TURN_BONUSES = createTurnBonusList();			
		}
	}
	
	public GameTurnBonusManager(Game game) {
		for (TurnBonus bonus : TURN_BONUSES) {
			//bonus.setPointManager(pointManager);
			bonus.setGame(game);
		}
		userBonuses = new HashMap<User, TurnBonus>();
	}
	
	private static List<TurnBonus> createTurnBonusList() {
		List<TurnBonus> turnBonuses = new ArrayList<TurnBonus>();
		turnBonuses.add(new TurnBonusAmmo());
		turnBonuses.add(new TurnBonusAmmoBuild());
		turnBonuses.add(new TurnBonusAmmoRetreat());
		turnBonuses.add(new TurnBonusCreditsFight());
		turnBonuses.add(new TurnBonusCredits());
		turnBonuses.add(new TurnBonusCreditsRaid());
		turnBonuses.add(new TurnBonusDefendFight());
		turnBonuses.add(new TurnBonusEridium());
		turnBonuses.add(new TurnBonusEridiumSupport());
		turnBonuses.add(new TurnBonusEridiumSupportCommand());
		turnBonuses.add(new TurnBonusMarch());
		turnBonuses.add(new TurnBonusMineNeutrals());
		turnBonuses.add(new TurnBonusRecruit());
		return turnBonuses;
	}
	
	/**
	 * Merge the data from the new bonus manager.
	 * 
	 * @param bonusManager
	 * 		The new bonus manager.
	 */
	public void merge(GameTurnBonusManager bonusManager) {
		//copy the image references from the previous turn bonuses to the new ones
		boolean instanceFound;
		if (bonusManager.getTurnBonusesGame() != null) {
			for (TurnBonus bonus : bonusManager.getTurnBonusesGame()) {
				instanceFound = false;
				for (TurnBonus prev : TURN_BONUSES) {//load from TURN_BONUSES because these are surely loaded
					if (!instanceFound && bonus.getClass().equals(prev.getClass())) {
						bonus.setImage(prev.getImage());
						instanceFound = true;
					}
				}
			}			
		}
		//copy the images to the userBonuses
		for (User user : bonusManager.getUserBonuses().keySet()) {
			TurnBonus bonus = bonusManager.getUserBonuses().get(user);
			instanceFound = false;
			for (TurnBonus prev : TURN_BONUSES) {//load from TURN_BONUSES because these are surely loaded
				if (!instanceFound && bonus.getClass().equals(prev.getClass())) {
					bonus.setImage(prev.getImage());
					instanceFound = true;
				}
			}
		}
		//set the new bonuses
		this.userBonuses = bonusManager.getUserBonuses();
		this.choosableTurnBonuses = bonusManager.getChoosableTurnBonuses();
		this.turnBonusesGame = bonusManager.getTurnBonusesGame();
	}
	
	/**
	 * Reload the variables of all turn bonuses because they might not be loaded.
	 * 
	 * The variables are loaded before the data was received from the database.
	 */
	public void reloadVariables() {
		for (TurnBonus bonus : turnBonusesGame) {
			bonus.loadVariables();
		}
	}
	
	/**
	 * Select the turn bonus cards for the game.
	 */
	public void chooseTurnBonusForGame(int players) {
		choosableTurnBonuses = new ArrayList<TurnBonus>(players+3);
		turnBonusesGame = new ArrayList<TurnBonus>(players+3);
		TurnBonus[] bonuses = new TurnBonus[TURN_BONUSES.size()];
		for (int i = 0; i < bonuses.length; i++) {
			bonuses[i] = TURN_BONUSES.get(i);
		}
		TurnBonus swap;
		int choosable = bonuses.length;
		int random;
		for (int i = 0; i < players+3; i++) {
			random = (int) (Math.random() * (choosable- i)) + i;//select a random integer from i to choosable
			swap = bonuses[random];//swap the chosen color to position i
			bonuses[random] = bonuses[i];
			bonuses[i] = swap;
			choosableTurnBonuses.add(bonuses[i]);
			turnBonusesGame.add(bonuses[i]);
		}
	}
	
	/**
	 * Check whether the bonus can be chosen or was already chosen.
	 * 
	 * @param bonus
	 * 		The TurnBonus that is checked.
	 * 
	 * @return
	 * 		True if the turn bonus can be chosen.
	 */
	public boolean isTurnBonusChoosable(TurnBonus bonus) {
		boolean isChoosable = false;
		for (TurnBonus choosable : choosableTurnBonuses) {
			isChoosable |= choosable.getClass().equals(bonus.getClass());
		}
		return isChoosable;
	}
	
	/**
	 * Choose the first turn bonus (without giving back one).
	 */
	public void chooseFirstTurnBonus(User user, TurnBonus bonus) {
		if (!isTurnBonusChoosable(bonus)) {
			throw new IllegalArgumentException("This turn bonus cannot be chosen.");
		}
		removeBonus(choosableTurnBonuses, bonus);
		//choosableTurnBonuses.remove(bonus);
		userBonuses.put(user, bonus);
	}
	
	/**
	 * Choose a turn bonus for the next round and lay back one.
	 */
	public void chooseTurnBonus(User user, TurnBonus chosen) {
		removeBonus(choosableTurnBonuses, chosen);
		//choosableTurnBonuses.remove(chosen);
		choosableTurnBonuses.add(userBonuses.get(user));
		userBonuses.put(user, chosen);
	}
	
	/**
	 * Put back a turn bonus without taking a new one after the last turn ends.
	 */
	public void putBackTurnBonus(User user) {
		TurnBonus empty = new TurnBonus() {
			private static final long serialVersionUID = -6582681601257266132L;
		};
		choosableTurnBonuses.add(userBonuses.get(user));
		userBonuses.put(user, empty);
	}
	
	private void removeBonus(List<TurnBonus> list, TurnBonus bonus) {
		boolean removedBonus = false;
		for (int i = 0; i < list.size(); i++) {
			if (!removedBonus && list.get(i).getClass().equals(bonus.getClass())) {
				list.remove(i);
				removedBonus = false;
			}
		}
	}
	
	public List<TurnBonus> getBonuses() {
		return turnBonusesGame;
	}
	public List<TurnBonus> getSelectableBonuses() {
		return choosableTurnBonuses;
	}
	public TurnBonus getUsersBonus(User user) {
		return userBonuses.get(user);
	}
	
	public void receiveAdditionalResources(User user, Game game) {
		userBonuses.get(user).receiveAdditionalResources(user, game);
	}
	public void receivePointsFight(User user, Fight fight) {
		userBonuses.get(user).receivePointsFight(user, fight);
	}
	public void receivePointsBuild(User user) {
		userBonuses.get(user).receivePointsBuild(user);
	}
	
	public Map<User, TurnBonus> getUserBonuses() {
		return userBonuses;
	}
	
	public List<TurnBonus> getChoosableTurnBonuses() {
		return choosableTurnBonuses;
	}
	
	public List<TurnBonus> getTurnBonusesGame() {
		return turnBonusesGame;
	}
}