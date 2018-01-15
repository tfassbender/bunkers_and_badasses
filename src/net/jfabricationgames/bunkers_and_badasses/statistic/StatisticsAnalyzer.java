package net.jfabricationgames.bunkers_and_badasses.statistic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.jfabricationgames.bunkers_and_badasses.user.User;

public class StatisticsAnalyzer {
	
	private List<GameStatistic> statistics;
	
	private Map<Integer, UserStatistic> userStatistics;
	private Map<Integer, GameDetail> gameStatistics;
	private Map<Integer, Map<Integer, UserStatistic>> mapStatistics;
	private Map<Integer, String> maps;
	
	private int localUserId;
	
	public StatisticsAnalyzer(List<GameStatistic> statistics, User localUser) {
		this.statistics = statistics;
		createUserStatistics(statistics);
		localUserId = -1;
		for (GameStatistic stats : statistics) {
			if (stats.getUser().equals(localUser)) {
				localUserId = stats.getUser_id();
			}
		}
	}
	
	private void createUserStatistics(List<GameStatistic> statistics) {
		userStatistics = new HashMap<Integer, UserStatistic>();
		gameStatistics = new HashMap<Integer, GameDetail>();
		mapStatistics = new HashMap<Integer, Map<Integer, UserStatistic>>();
		maps = new HashMap<Integer, String>();
		for (GameStatistic stats : statistics) {
			//create the global user statistics
			UserStatistic userStats = userStatistics.get(stats.getUser_id());
			if (userStats == null) {
				userStats = new UserStatistic(stats.getUser_id(), stats.getUser(), stats.getGame_id());
				userStatistics.put(stats.getUser_id(), userStats);
			}
			userStats.addStatistic(stats);
			//create the map statistics
			Map<Integer, UserStatistic> mapStats = mapStatistics.get(stats.getMap_id());
			if (mapStats == null) {
				mapStats = new HashMap<Integer, UserStatistic>();
				mapStatistics.put(stats.getMap_id(), mapStats);
			}
			userStats = mapStats.get(stats.getUser_id());
			if (userStats == null) {
				userStats = new UserStatistic(stats.getUser_id(), stats.getUser(), stats.getGame_id());
				mapStats.put(stats.getUser_id(), userStats);
			}
			userStats.addStatistic(stats);
			//create the game statistics
			GameDetail game = gameStatistics.get(stats.getGame_id());
			if (game == null) {
				game = new GameDetail();
				gameStatistics.put(stats.getGame_id(), game);
			}
			game.addGameStatistic(stats);
			//add the game maps to the list
			maps.put(stats.getMap_id(), stats.getMap_name());
		}
		//calculate the statistics
		userStatistics.forEach((id, stats) -> stats.calculateAverages());
		mapStatistics.forEach((id, map) -> map.forEach((id2, stats) -> stats.calculateAverages()));
	}
	
	public List<UserStatistic> getSortedMapStatistics(int mapId) {
		if (mapStatistics.get(mapId) != null) {
			List<UserStatistic> sortedStats = new ArrayList<UserStatistic>(mapStatistics.get(mapId).size());
			mapStatistics.get(mapId).forEach((id, stats) -> sortedStats.add(stats));
			Collections.sort(sortedStats);
			return sortedStats;
		}
		return null;
	}
	public List<UserStatistic> getSortedUserStatistics() {
		List<UserStatistic> sortedStats = new ArrayList<UserStatistic>(userStatistics.size());
		userStatistics.forEach((id, stats) -> sortedStats.add(stats));
		Collections.sort(sortedStats);
		return sortedStats;
	}
	
	public int getLocalUserId() {
		return localUserId;
	}
	
	public List<GameStatistic> getStatistics() {
		return statistics;
	}
	
	public Map<Integer, UserStatistic> getUserStatistics() {
		return userStatistics;
	}
	public Map<Integer, String> getMaps() {
		return maps;
	}
	public Map<Integer, GameDetail> getGameStatistics() {
		return gameStatistics;
	}
}