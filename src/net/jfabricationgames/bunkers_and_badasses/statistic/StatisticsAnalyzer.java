package net.jfabricationgames.bunkers_and_badasses.statistic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatisticsAnalyzer {
	
	private List<GameStatistic> statistics;
	
	private Map<Integer, UserStatistic> userStatistics;
	private Map<Integer, Map<Integer, UserStatistic>> mapStatistics;
	
	public StatisticsAnalyzer(List<GameStatistic> statistics) {
		this.statistics = statistics;
		createUserStatistics(statistics);
	}
	
	private void createUserStatistics(List<GameStatistic> statistics) {
		userStatistics = new HashMap<Integer, UserStatistic>();
		mapStatistics = new HashMap<Integer, Map<Integer, UserStatistic>>();
		for (GameStatistic stats : statistics) {
			//create the global user statistics
			UserStatistic userStats = userStatistics.get(stats.getUser_id());
			if (userStats == null) {
				userStats = new UserStatistic(stats.getUser_id(), stats.getUser());
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
				userStats = new UserStatistic(stats.getUser_id(), stats.getUser());
				mapStats.put(stats.getUser_id(), userStats);
			}
			userStats.addStatistic(stats);
		}
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
	
	public List<GameStatistic> getStatistics() {
		return statistics;
	}
	
	public Map<Integer, UserStatistic> getUserStatistics() {
		return userStatistics;
	}
}