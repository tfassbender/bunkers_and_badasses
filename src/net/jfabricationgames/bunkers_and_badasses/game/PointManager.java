package net.jfabricationgames.bunkers_and_badasses.game;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.jfabricationgames.bunkers_and_badasses.user.User;
import net.jfabricationgames.logger.JFGLogger;
import net.jfabricationgames.logger.JFGLoggerManager;

public class PointManager implements Serializable {
	
	private static final long serialVersionUID = -7680983446141485184L;
	
	public enum PointType {
		FIGHT,
		FIELD,
		REGION,
		GOAL,
		BONUS,
		SKILL;
	}
	
	private Map<User, Integer> points;
	
	private static JFGLogger pointLogger;
	
	private Game game;
	
	static {
		try {
			pointLogger = new JFGLogger("bunkers_and_badasses_point_logs", 1000);
			JFGLoggerManager.addLogger(pointLogger);
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public class UserPoints implements Comparable<UserPoints> {
		
		private User user;
		private int points;
		
		public UserPoints(User user, int points) {
			this.user = user;
			this.points = points;
		}
		
		public User getUser() {
			return user;
		}
		public int getPoints() {
			return points;
		}
		
		@Override
		public String toString() {
			return user.getUsername() + ": " + points;
		}

		@Override
		public int compareTo(UserPoints userPoints) {
			return userPoints.getPoints() - points;
		}
	}
	
	public PointManager(Game game) {
		this.game = game;
	}
	
	public void initialize(List<User> players) {
		points = new HashMap<User, Integer>();
		for (User user : players) {
			points.put(user, 0);
		}
	}
	
	public void merge(PointManager manager) {
		this.points = manager.points;
	}
	
	public List<UserPoints> getSortedPointList() {
		List<UserPoints> userPoints = new ArrayList<UserPoints>();
		for (User user : points.keySet()) {
			userPoints.add(new UserPoints(user, points.get(user)));
		}
		Collections.sort(userPoints);
		return userPoints;
	}
	
	public int getPosition(User player) {
		List<UserPoints> sortedPoints = getSortedPointList();
		int position = -1;
		for (int i = 0; i < sortedPoints.size(); i++) {
			if (sortedPoints.get(i).getUser().equals(player)) {
				position = i;
			}
		}
		return position;
	}
	
	public void addPoints(User player, int points, Class<?> clazz, String cause, PointType type) {
		pointLogger.addLog(points + " Points added to user [" + player + "] by class [" + clazz.getName() + "]: " + cause);
		if (game != null) {
			//game is null in unit tests
			GameStatistic stats = game.getStatisticManager().getStatistics(player);
			switch (type) {
				case BONUS:
					stats.setPoints_bonuses(stats.getPoints_bonuses() + points);
					break;
				case FIELD:
					stats.setPoints_fields(stats.getPoints_fields() + points);
					break;
				case FIGHT:
					stats.setPoints_fight(stats.getPoints_fight() + points);
					break;
				case GOAL:
					stats.setPoints_goals(stats.getPoints_goals() + points);
					break;
				case REGION:
					stats.setPoints_regions(stats.getPoints_regions() + points);
					break;
				case SKILL:
					stats.setPoints_skills(stats.getPoints_skills() + points);
					break;
				default:
					throw new IllegalArgumentException("Unknown point type");
			}
		}
		addPoints(player, points);
	}
	private void addPoints(User player, int points) {
		int current = this.points.get(player);
		this.points.put(player, current + points);
	}
	public int getPoints(User player) {
		return points.get(player);
	}
}