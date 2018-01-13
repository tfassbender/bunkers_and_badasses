package net.jfabricationgames.bunkers_and_badasses.statistic;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.jfabricationgames.bunkers_and_badasses.game.Game;
import net.jfabricationgames.bunkers_and_badasses.game.PointManager.UserPoints;
import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.jfabricationgames.bunkers_and_badasses.game_board.Region;
import net.jfabricationgames.bunkers_and_badasses.user.User;

public class GameStatisticManager implements Serializable {
	
	private static final long serialVersionUID = -6456884908796734821L;
	
	private Map<User, GameStatistic> statistics;
	
	public static final int MERGE_PLANING_COMMIT = 1;
	public static final int MERGE_TURN_OVER = 2;
	
	/**
	 * Merge the statistics of a single player (the player that sent the update) to this statistics.
	 * 
	 * @param stat
	 * 		The statistics of the player that sent the update.
	 */
	public void merge(Map<User, GameStatistic> stats, int mergeType, User user) {
		switch (mergeType) {
			case MERGE_PLANING_COMMIT:
				//add only the players new statistics
				if (statistics.get(user) != null) {
					statistics.put(user, stats.get(user));					
				}
				else {
					throw new IllegalArgumentException("Unknown user: " + user);
				}
				break;
			case MERGE_TURN_OVER:
				//just set the statistics to the updated once from the other player
				statistics = stats;
				break;
			default:
				throw new IllegalArgumentException("Unknown mergeType (" + mergeType + ")");
		}
	}
	
	/**
	 * Add the values of the game's end.
	 * 
	 * @param game
	 * 		The ended game.
	 */
	public void addEndValues(Game game) {
		//the player's positions
		List<UserPoints> sortedPoints = game.getPointManager().getSortedPointList();
		for (int i = 0; i < sortedPoints.size(); i++) {
			GameStatistic stats = statistics.get(sortedPoints.get(i).getUser());
			stats.setPosition(i+1);
			stats.setPoints(sortedPoints.get(i).getPoints());
		}
		//controlled troops, fields, regions
		for (User user : game.getPlayers()) {
			GameStatistic stats = statistics.get(user);
			int troops = 0;
			int regionPoints = 0;
			for (Field field : game.getBoard().getUsersFields(user)) {
				troops += field.getNormalTroops() + 2 * field.getBadassTroops();
			}
			for (Region region : game.getBoard().getUsersRegions(user)) {
				regionPoints += region.getPoints();
			}
			stats.setTroops_controlled_end(troops);
			stats.setFields_end(game.getBoard().getUsersFields(user).size());
			stats.setRegions_end(game.getBoard().getUsersRegions(user).size());
			stats.setRegions_value_end(regionPoints);
		}
	}
	
	/**
	 * Update max controlled troops, fields and regions.
	 */
	public void updateValues(Game game) {
		for (User user : game.getPlayers()) {
			GameStatistic stats = statistics.get(user);
			int troops = 0;
			int regionPoints = 0;
			for (Field field : game.getBoard().getUsersFields(user)) {
				troops += field.getNormalTroops() + 2 * field.getBadassTroops();
			}
			for (Region region : game.getBoard().getUsersRegions(user)) {
				regionPoints += region.getPoints();
			}
			stats.setTroops_controlled_max(Math.max(stats.getTroops_controlled_max(), troops));
			stats.setFields_max(Math.max(stats.getFields_max(), game.getBoard().getUsersFields(user).size()));
			stats.setRegions_max(Math.max(stats.getRegions_max(), game.getBoard().getUsersRegions(user).size()));
			stats.setRegions_value_max(Math.max(stats.getRegions_value_max(), regionPoints));
		}
	}
	
	/**
	 * Initialize with all players.
	 */
	public void initialize(List<User> players) {
		statistics = new HashMap<User, GameStatistic>();
		for (User user : players) {
			GameStatistic statistic = new GameStatistic(user);
			statistic.setPlayers(players.size());
			statistics.put(user, statistic);
		}
	}
	
	public Map<User, GameStatistic> getStatistics() {
		return statistics;
	}
	public GameStatistic getStatistics(User user) {
		return statistics.get(user);
	}
}