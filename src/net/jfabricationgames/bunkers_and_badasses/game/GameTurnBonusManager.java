package net.jfabricationgames.bunkers_and_badasses.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.jfabricationgames.bunkers_and_badasses.game_turn_cards.TurnBonus;
import net.jfabricationgames.bunkers_and_badasses.user.User;

public class GameTurnBonusManager {
	
	private Map<User, TurnBonus> usersBonuses;
	
	private List<TurnBonus> choosableTurnBonuses;
	
	private static final List<TurnBonus> TURN_BONUSES = createTurnBonusList();
	
	public GameTurnBonusManager(PointManager pointManager) {
		for (TurnBonus bonus : TURN_BONUSES) {
			bonus.setPointManager(pointManager);
		}
	}
	
	private static List<TurnBonus> createTurnBonusList() {
		List<TurnBonus> turnBonuses = new ArrayList<TurnBonus>();
		//TODO add the turn bonuses to the list when known
		return turnBonuses;
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
		}
	}
	
	/**
	 * Choose the first turn bonus (without giving back one).
	 */
	public void chooseFirstTurnBonus(User user, TurnBonus bonus) {
		choosableTurnBonuses.remove(bonus);
		usersBonuses.put(user, bonus);
	}
	
	/**
	 * Choose a turn bonus for the next round and lay back one.
	 */
	public void chooseTurnBonus(User user, TurnBonus chosen, TurnBonus back) {
		choosableTurnBonuses.remove(chosen);
		choosableTurnBonuses.add(back);
		usersBonuses.put(user, chosen);
	}
	
	public void receiveAdditionalResources(User user, Game game) {
		usersBonuses.get(user).receiveAdditionalResources(user, game);
	}
	public void receivePointsFight(User user, Fight fight) {
		usersBonuses.get(user).receivePointsFight(user, fight);
	}
	public void receivePointsBuild(User user) {
		usersBonuses.get(user).receivePointsBuild(user);
	}
	
	public Map<User, TurnBonus> getUsersBonuses() {
		return usersBonuses;
	}
}