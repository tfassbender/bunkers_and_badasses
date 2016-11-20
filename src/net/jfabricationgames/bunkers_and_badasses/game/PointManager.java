package net.jfabricationgames.bunkers_and_badasses.game;

import java.util.Map;

import net.jfabricationgames.bunkers_and_badasses.user.User;

public class PointManager {
	
	private Map<User, Integer> points;
	
	public class UserPoints {
		
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
	}
	
	public void addPoints(User player, int points) {
		//TODO
	}
	
	public int getPoints(User player) {
		//TODO
		return 0;
	}
}