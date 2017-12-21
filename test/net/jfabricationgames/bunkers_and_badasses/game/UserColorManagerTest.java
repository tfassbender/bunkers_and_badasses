package net.jfabricationgames.bunkers_and_badasses.game;

import static org.junit.Assert.assertFalse;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import net.jfabricationgames.bunkers_and_badasses.user.User;

public class UserColorManagerTest {
	
	private UserColorManager manager;
	private User user1;
	private User user2;
	private User user3;
	private User user4;
	private User user5;
	
	@Before
	public void initUserColorManager() {
		manager = new UserColorManager();
		user1 = new User("user1");
		user2 = new User("user2");
		user3 = new User("user3");
		user4 = new User("user4");
		user5 = new User("user5");
	}
	
	@Test
	public void testChooseColors() {
		manager.chooseColors(Arrays.asList(user1, user2, user3, user4, user5));
		
		//test if it's really random by executing it many times
		Map<User, UserColor> colors = new HashMap<User, UserColor>(manager.getUserColors());
		boolean equal = true;
		for (int i = 0; i < 20; i++) {
			//20 times the same colors seems not randomly distributed
			manager.chooseColors(Arrays.asList(user1, user2, user3, user4, user5));
			equal &= colors.equals(manager.getUserColors());
		}
		assertFalse(equal);
		
		Map<User, TroopTexture> textures = new HashMap<User, TroopTexture>(manager.getUserTextures());
		equal = true;
		for (int i = 0; i < 20; i++) {
			//20 times the same textures seems not randomly distributed
			manager.chooseColors(Arrays.asList(user1, user2, user3, user4, user5));
			equal &= textures.equals(manager.getUserTextures());
		}
		assertFalse(equal);
	}
}