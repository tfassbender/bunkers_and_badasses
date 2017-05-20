package net.jfabricationgames.bunkers_and_badasses.game;

import java.io.Serializable;
import java.util.ArrayList;
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
	
	public GameTurnBonusManager(PointManager pointManager) {
		for (TurnBonus bonus : TURN_BONUSES) {
			bonus.setPointManager(pointManager);
		}
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
		//set the new bonuses
		this.userBonuses = bonusManager.getUserBonuses();
		this.choosableTurnBonuses = bonusManager.getChoosableTurnBonuses();
		this.turnBonusesGame = bonusManager.getTurnBonusesGame();
	}
	
	/**
	 * Select the turn bonus cards for the game.
	 */
	public void chooseTurnBonusForGame(int players) {
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
	 * Choose the first turn bonus (without giving back one).
	 */
	public void chooseFirstTurnBonus(User user, TurnBonus bonus) {
		choosableTurnBonuses.remove(bonus);
		userBonuses.put(user, bonus);
	}
	
	/**
	 * Choose a turn bonus for the next round and lay back one.
	 */
	public void chooseTurnBonus(User user, TurnBonus chosen) {
		choosableTurnBonuses.remove(chosen);
		choosableTurnBonuses.add(userBonuses.get(user));
		userBonuses.put(user, chosen);
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