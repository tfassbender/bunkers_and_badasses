package net.jfabricationgames.bunkers_and_badasses.game;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import net.jfabricationgames.bunkers_and_badasses.user.User;

public class PlayerOrderTest {
	
	private PlayerOrder playerOrder;
	private User user1;
	private User user2;
	private User user3;
	private User user4;
	private User user5;
	
	@Before
	public void initPlayerOrder() {
		playerOrder = new PlayerOrder(5, null);
		user1 = new User("user1");
		user2 = new User("user2");
		user3 = new User("user3");
		user4 = new User("user4");
		user5 = new User("user5");
		playerOrder.chooseRandomOrder(Arrays.asList(user1, user2, user3, user4, user5));
	}
	
	@Test
	public void testChooseRandomOrder() {
		assertEquals(5, playerOrder.getOrder().length);
		
		//test if it's really random by executing it many times
		User[] last = playerOrder.getOrder();
		boolean equal = true;
		for (int i = 0; i < 20; i++) {
			//20 times the same order seems not randomly distributed
			playerOrder.chooseRandomOrder(Arrays.asList(user1, user2, user3, user4, user5));
			equal &= Arrays.equals(last, playerOrder.getOrder());
		}
		assertFalse(equal);
	}
	
	@Test
	public void testGetNext() {
		User[] order = playerOrder.getOrder();
		assertEquals(order[1], playerOrder.getNext());
		
		playerOrder.nextMove();
		assertEquals(order[2], playerOrder.getNext());
		
		for (int i = 0; i < 3; i++) {
			playerOrder.nextMove();
		}
		assertEquals(order[0], playerOrder.getNext());
	}
	
	@Test
	public void testGetNextTurnOrder() {
		User[] order = playerOrder.getOrder();
		assertArrayEquals(new User[] {null, null, null, null, null}, playerOrder.getNextTurnOrder());
		
		playerOrder.userPassed(order[1]);
		playerOrder.userPassed(order[3]);
		playerOrder.userPassed(order[0]);
		assertArrayEquals(new User[] {order[1], order[3], order[0], null, null}, playerOrder.getNextTurnOrder());
		
		playerOrder.userPassed(order[2]);
		playerOrder.userPassed(order[4]);
		assertArrayEquals(new User[] {order[1], order[3], order[0], order[2], order[4]}, playerOrder.getNextTurnOrder());
	}
	
	@Test
	public void testGetActivePlayer() {
		User[] order = playerOrder.getOrder();
		
		assertEquals(order[0], playerOrder.getActivePlayer());
		
		playerOrder.nextMove();
		assertEquals(order[1], playerOrder.getActivePlayer());
		
		playerOrder.nextMove();
		assertEquals(order[2], playerOrder.getActivePlayer());
		
		playerOrder.userPassed(order[2]);
		playerOrder.nextMove();
		assertEquals(order[3], playerOrder.getActivePlayer());
		
		playerOrder.userPassed(order[4]);
		playerOrder.nextMove();
		assertEquals(order[0], playerOrder.getActivePlayer());
	}
	
	@Test
	public void testIsPlayersTurn() {
		User[] order = playerOrder.getOrder();
		
		assertTrue(playerOrder.isPlayersTurn(order[0]));
		
		playerOrder.nextMove();
		assertTrue(playerOrder.isPlayersTurn(order[1]));
		
		playerOrder.nextMove();
		assertTrue(playerOrder.isPlayersTurn(order[2]));
		
		playerOrder.userPassed(order[2]);
		playerOrder.nextMove();
		assertTrue(playerOrder.isPlayersTurn(order[3]));
		
		playerOrder.userPassed(order[4]);
		playerOrder.nextMove();
		assertTrue(playerOrder.isPlayersTurn(order[0]));
	}
	
	@Test
	public void testIsTurnEnd() {
		User[] order = playerOrder.getOrder();
		
		assertFalse(playerOrder.isTurnEnd());
		
		playerOrder.userPassed(order[0]);
		playerOrder.userPassed(order[2]);
		
		assertFalse(playerOrder.isTurnEnd());
		
		playerOrder.userPassed(order[4]);
		
		assertFalse(playerOrder.isTurnEnd());
		
		playerOrder.userPassed(order[1]);
		playerOrder.userPassed(order[3]);
		
		assertTrue(playerOrder.isTurnEnd());
	}
}