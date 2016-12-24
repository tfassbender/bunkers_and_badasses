package net.jfabricationgames.bunkers_and_badasses.game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.jfabricationgames.bunkers_and_badasses.user.User;

public class UserColorManager {
	
	public static final int COLOR_ALPHA = 255;//the alpha value for all user colors
	
	private Map<User, UserColor> userColors = new HashMap<User, UserColor>();
	
	/**
	 * Choose random colors for all players and write them to the map.
	 * 
	 * @param users
	 * 		A list of all users.
	 */
	public void chooseRandomColors(List<User> users) {
		UserColor[] colors = UserColor.values();
		UserColor swap;
		int choosableColors = UserColor.getAvailableColors();
		int random;
		for (int i = 0; i < users.size(); i++) {
			random = (int) (Math.random() * (choosableColors - i)) + i;//select a random integer from i to choosableColors
			swap = colors[random];//swap the chosen color to position i
			colors[random] = colors[i];
			colors[i] = swap;
			userColors.put(users.get(i), colors[i]);//add the color
		}
	}
	
	public Map<User, UserColor> getUserColors() {
		return userColors;
	}
	public void setUserColors(Map<User, UserColor> userColors) {
		this.userColors = userColors;
	}
}