package net.jfabricationgames.bunkers_and_badasses.game;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import net.jfabricationgames.bunkers_and_badasses.user.User;

public class PointManagerTest {
	
	private PointManager manager;
	private User user1;
	private User user2;
	private User user3;
	private User user4;
	private User user5;
	
	@Before
	public void initPointManager() {
		manager = new PointManager(null);
		user1 = new User("user1");
		user2 = new User("user2");
		user3 = new User("user3");
		user4 = new User("user4");
		user5 = new User("user5");
		manager.initialize(Arrays.asList(user1, user2, user3, user4, user5));
	}
	
	@Test
	public void testInitialize() {
		assertEquals(0, manager.getPoints(user1));
		assertEquals(0, manager.getPoints(user2));
		assertEquals(0, manager.getPoints(user3));
		assertEquals(0, manager.getPoints(user4));
		assertEquals(0, manager.getPoints(user5));
	}
	
	@Test
	public void testGetSortedPointList() {
		manager.addPoints(user1, 17, getClass(), "test", null);
		manager.addPoints(user2, 42, getClass(), "test", null);
		manager.addPoints(user3, 1, getClass(), "test", null);
		manager.addPoints(user4, 2, getClass(), "test", null);
		manager.addPoints(user5, 3, getClass(), "test", null);
		
		List<PointManager.UserPoints> points = manager.getSortedPointList();
		
		assertEquals(user2, points.get(0).getUser());
		assertEquals(user1, points.get(1).getUser());
		assertEquals(user5, points.get(2).getUser());
		assertEquals(user4, points.get(3).getUser());
		assertEquals(user3, points.get(4).getUser());
		
		assertEquals(42, points.get(0).getPoints());
		assertEquals(17, points.get(1).getPoints());
		assertEquals(3, points.get(2).getPoints());
		assertEquals(2, points.get(3).getPoints());
		assertEquals(1, points.get(4).getPoints());
	}
	
	@Test
	public void testGetPosition() {
		manager.addPoints(user1, 17, getClass(), "test", null);
		manager.addPoints(user2, 42, getClass(), "test", null);
		manager.addPoints(user3, 1, getClass(), "test", null);
		manager.addPoints(user4, 2, getClass(), "test", null);
		manager.addPoints(user5, 3, getClass(), "test", null);
		
		assertEquals(0, manager.getPosition(user2));
		assertEquals(1, manager.getPosition(user1));
		assertEquals(2, manager.getPosition(user5));
		assertEquals(3, manager.getPosition(user4));
		assertEquals(4, manager.getPosition(user3));
	}
	
	@Test
	public void testAddPoints() {
		manager.addPoints(user1, 12, getClass(), "test", null);
		assertEquals(12, manager.getPoints(user1));
		
		manager.addPoints(user1, 5, getClass(), "test", null);
		assertEquals(17, manager.getPoints(user1));
		
		manager.addPoints(user2, 42, getClass(), "test", null);
		assertEquals(42, manager.getPoints(user2));
	}
}