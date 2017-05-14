package net.jfabricationgames.bunkers_and_badasses.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.jfabricationgames.bunkers_and_badasses.user.User;

public class PointManager implements Serializable {
	
	private static final long serialVersionUID = -7680983446141485184L;
	
	private Map<User, Integer> points;
	
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
			return points - userPoints.getPoints();
		}
	}
	
	public void initialize(List<User> players) {
		points = new HashMap<User, Integer>();
		for (User user : players) {
			points.put(user, 0);
		}
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
			if (sortedPoints.get(i).equals(player)) {
				position = i;
			}
		}
		return position;
	}
	
	public void addPoints(User player, int points) {
		int current = this.points.get(player);
		this.points.put(player, current + points);
	}
	public void setPoints(User player, int points) {
		this.points.put(player, points);
	}
	public int getPoints(User player) {
		return points.get(player);
	}
}