package net.jfabricationgames.bunkers_and_badasses.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Arrays;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import net.jfabricationgames.bunkers_and_badasses.error.BunkersAndBadassesException;
import net.jfabricationgames.bunkers_and_badasses.game_character.hero.Hero;
import net.jfabricationgames.bunkers_and_badasses.login.SimpleLoginClientInterpreter;
import net.jfabricationgames.bunkers_and_badasses.user.User;

public class HeroCardManagerTest {
	
	private HeroCardManager manager;
	private User user1;
	private User user2;
	private User user3;
	
	@BeforeClass
	public static void loadVariables() {
		SimpleLoginClientInterpreter.loadDynamicVariables();
	}
	
	@Before
	public void initHeroCardManager() {
		manager = new HeroCardManager();
		user1 = new User("user1");
		user2 = new User("user2");
		user3 = new User("user3");
		manager.intitialize(Arrays.asList(user1, user2, user3));
	}
	
	@Test
	public void testIntitialize() {
		User user4 = new User("user4");
		User user5 = new User("user5");
		User user6 = new User("user6");
		
		manager.intitialize(Arrays.asList(user1, user2, user3, user4, user5));
		assertEquals(25, manager.getHeroCardStack().size());

		manager.intitialize(Arrays.asList(user1, user2, user3, user4, user5, user6));
		assertEquals(50, manager.getHeroCardStack().size());
	}
	
	@Test
	public void testTakeCards() {
		manager.takeCards(user1, 5);
		assertEquals(5, manager.getHeroCards().get(user1).size());
		
		manager.takeCards(user2, 3);
		assertEquals(3, manager.getHeroCards().get(user2).size());
		
		try {
			manager.takeCards(user1, 1);//to many cards
			fail("An Exception was expected here...");
		}
		catch (BunkersAndBadassesException babe) {
			//do nothing here because the exception was expected
		}
	}
	
	@Test
	public void testHeroCardUsed() {
		manager.takeCards(user1, 5);
		assertEquals(5, manager.getHeroCards().get(user1).size());
		
		Hero card = manager.getHeroCards(user1).get(0);
		Hero card2 = manager.getHeroCards(user1).get(1);
		manager.heroCardUsed(card, user1);
		assertEquals(4, manager.getHeroCards().get(user1).size());
		
		try {
			manager.heroCardUsed(card, user1);
			fail("An Exception was expected here...");
		}
		catch (BunkersAndBadassesException babe) {
			//do nothing here because the exception was expected
		}
		
		try {
			manager.heroCardUsed(card2, user2);//user2 has no cards
			fail("An Exception was expected here...");
		}
		catch (BunkersAndBadassesException babe) {
			//do nothing here because the exception was expected
		}
		
		assertEquals(21, manager.getHeroCardStack().size());//23 cards left in the stack
	}
}