package net.jfabricationgames.bunkers_and_badasses.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.jfabricationgames.bunkers_and_badasses.user.User;

public class UserColorManager implements Serializable {
	
	private static final long serialVersionUID = -7691778554986063225L;
	
	public static final int COLOR_ALPHA = 255;//the alpha value for all user colors
	
	private Map<User, UserColor> userColors;
	
	private Map<User, TroopTexture> userTextures;
	
	public UserColorManager() {
		userColors = new HashMap<User, UserColor>();
		userTextures = new HashMap<User, TroopTexture>();
	}
	
	/**
	 * Choose random colors and textures for all players.
	 * 
	 * @param users
	 * 		A list of all users.
	 */
	public void chooseColors(List<User> users) {
		chooseRandomColors(users);
		chooseRandomTextures(users);
	}
	
	/**
	 * Choose random colors for all players and write them to the map.
	 * 
	 * @param users
	 * 		A list of all users.
	 */
	private void chooseRandomColors(List<User> users) {
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
	
	/**
	 * Choose random textures for all players and write them to the map.
	 * 
	 * @param users
	 * 		A list of all users.
	 */
	private void chooseRandomTextures(List<User> users) {
		List<TroopTexture> textures = new ArrayList<TroopTexture>(3);
		textures.add(TroopTexture.BANDITS);
		textures.add(TroopTexture.HYPERION);
		textures.add(TroopTexture.VAULT_GUARDS);
		//make all textures possible also in two player games
		Collections.shuffle(textures);
		List<User> textureUsers = new ArrayList<User>(users);//don't change anything in users! use the clone!
		int numUsers = textureUsers.size();
		for (int i = 0; i < numUsers; i++) {
			int userIndex = (int) (Math.random() * textureUsers.size());
			User user = textureUsers.get(userIndex);
			textureUsers.remove(userIndex);
			userTextures.put(user, textures.get(i%3));
		}
	}
	
	public void merge(UserColorManager manager) {
		this.userColors = manager.getUserColors();
		this.userTextures = manager.getUserTextures();
	}
	
	public Map<User, UserColor> getUserColors() {
		return userColors;
	}
	public void setUserColors(Map<User, UserColor> userColors) {
		this.userColors = userColors;
	}
	
	public Map<User, TroopTexture> getUserTextures() {
		return userTextures;
	}
	
	public UserColor getColor(User user) {
		return userColors.get(user);
	}
}