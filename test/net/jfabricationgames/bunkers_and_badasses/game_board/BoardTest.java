package net.jfabricationgames.bunkers_and_badasses.game_board;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import net.jfabricationgames.bunkers_and_badasses.error.MovementException;
import net.jfabricationgames.bunkers_and_badasses.game_character.building.ArschgaulsPalace;
import net.jfabricationgames.bunkers_and_badasses.game_character.building.EmptyBuilding;
import net.jfabricationgames.bunkers_and_badasses.game_character.building.MoxxisTavern;
import net.jfabricationgames.bunkers_and_badasses.game_character.building.ScootersCatchARide;
import net.jfabricationgames.bunkers_and_badasses.game_character.building.TorguesBadassDome;
import net.jfabricationgames.bunkers_and_badasses.login.SimpleLoginClientInterpreter;
import net.jfabricationgames.bunkers_and_badasses.user.User;

public class BoardTest {
	
	private static Board board;
	private User user1;
	private User user2;
	
	@BeforeClass
	public static void loadBoard() {
		SimpleLoginClientInterpreter.loadDynamicVariables();
		BoardLoader loader = new BoardLoader();
		board = loader.loadBoard(new File("boards/helios_station.babbm"));
	}
	
	@Before
	public void clearBoard() {
		user1 = new User("user1");
		user2 = new User("user2");
		//remove all troops and buildings
		for (Field field : board.getFields()) {
			field.removeAllTroops();
			field.setBuilding(new EmptyBuilding());
			field.setAffiliation(null);
		}
		//add some start troops and buildings to some fields
		board.getFieldByName("Remote Access Platform").setBuilding(new ArschgaulsPalace());
		board.getFieldByName("Access Tunnel 27").setBuilding(new ArschgaulsPalace());
		board.getFieldByName("Access Tunnel 27").addNormalTroops(1);
		board.getFieldByName("Access Tunnel 27").setAffiliation(user1);
		board.getFieldByName("Hall of Wonders").addNormalTroops(2);
		board.getFieldByName("Hall of Wonders").setAffiliation(user1);
		board.getFieldByName("Central Terminal").addNormalTroops(2);
		board.getFieldByName("Central Terminal").setAffiliation(user1);
		board.getFieldByName("Immigration Station").addNormalTroops(3);
		board.getFieldByName("Immigration Station").addBadassTroops(1);
		board.getFieldByName("Immigration Station").setAffiliation(user1);
		board.getFieldByName("Jacks Office").addNormalTroops(3);
		board.getFieldByName("Jacks Office").addBadassTroops(1);
		board.getFieldByName("Jacks Office").setAffiliation(user1);
		board.getFieldByName("Remote Access Platform").addNormalTroops(3);
		board.getFieldByName("Remote Access Platform").addBadassTroops(1);
		board.getFieldByName("Remote Access Platform").setAffiliation(user2);
		board.getFieldByName("Plasma Factory A").addNormalTroops(2);
		board.getFieldByName("Plasma Factory A").setAffiliation(user2);
		board.getFieldByName("Plasma Factory B").addNormalTroops(2);
		board.getFieldByName("Plasma Factory B").setAffiliation(user2);
		board.getFieldByName("Supply Platform").addNormalTroops(1);
		board.getFieldByName("Supply Platform").setAffiliation(user2);
		board.getFieldByName("Hyperion Customer Service").addNormalTroops(2);
		board.getFieldByName("Hyperion Customer Service").addBadassTroops(2);
		board.getFieldByName("Hyperion Customer Service").setAffiliation(user2);
	}
	
	@Test
	public void testMoveTroops() {
		assertEquals(0, board.getFieldByName("Voice of Hyperion").getTroopStrength());
		assertNull(board.getFieldByName("Voice of Hyperion").getAffiliation());
		
		//move from Jacks Office to the empty field Voice of Hyperion
		board.moveTroops(board.getFieldByName("Jacks Office"), board.getFieldByName("Voice of Hyperion"), 1, 0);
		
		assertEquals(1, board.getFieldByName("Voice of Hyperion").getTroopStrength());
		assertEquals(user1, board.getFieldByName("Voice of Hyperion").getAffiliation());
		assertEquals(4, board.getFieldByName("Jacks Office").getTroopStrength());
		
		try {
			//try to move to an enemy field
			board.moveTroops(board.getFieldByName("Jacks Office"), board.getFieldByName("Remote Access Platform"), 1, 0);
			fail("A MovementException was expected here...");
		}
		catch (MovementException me) {
			//do nothing here because the exception was expected
		}
		
		try {
			//move more troops than there are on the field
			board.moveTroops(board.getFieldByName("Jacks Office"), board.getFieldByName("Remote Access Platform"), 1, 4);
			fail("A MovementException was expected here...");
		}
		catch (MovementException me) {
			//do nothing here because the exception was expected
		}
		
		//moving to a distant field is possible (e.g. with Scooter's Catch-A-Ride)
	}
	
	@Test
	public void testAddNeutralTroops() {
		Map<Field, int[]> neutralTroops = board.addNeutralTroops();
		//neutral troops on 3 fields
		assertEquals(3, neutralTroops.size());
		
		//remove all for less tests
		for (Field field : board.getFields()) {
			field.removeAllTroops();
			field.setBuilding(new EmptyBuilding());
			field.setAffiliation(null);
		}
		neutralTroops = board.addNeutralTroops();
		
		assertEquals(8, neutralTroops.size());
		
		//test if every combination of neutral troops and number of troops is possible (by many tests)
		boolean[][] troops = new boolean[3][3];
		//stalkers and threshers can only have two troops -> set 3 to true for testing
		troops[1][2] = true;
		troops[2][2] = true;
		
		for (int i = 0; i < 30; i++) {
			neutralTroops = board.addNeutralTroops();
			for (Field field : neutralTroops.keySet()) {
				int[] troop = neutralTroops.get(field);
				troops[troop[1]][troop[0]-1] = true;
			}
		}
		boolean allCombinationsFound = true;
		for (int i = 0; i < troops.length; i++) {
			for (int j = 0; j < troops[0].length; j++) {
				allCombinationsFound &= troops[i][j];
			}
		}
		assertTrue(allCombinationsFound);
	}
	
	@Test
	public void testGetUsersFields() {
		List<Field> fields = board.getUsersFields(user1);
		
		assertEquals(5, fields.size());
		assertTrue(fields.contains(board.getFieldByName("Access Tunnel 27")));
		assertTrue(fields.contains(board.getFieldByName("Hall of Wonders")));
		assertTrue(fields.contains(board.getFieldByName("Central Terminal")));
		assertTrue(fields.contains(board.getFieldByName("Immigration Station")));
		assertTrue(fields.contains(board.getFieldByName("Jacks Office")));
	}
	
	@Test
	public void testGetUsersRegions() {
		List<Region> regions = board.getUsersRegions(user1);
		
		assertTrue(regions.isEmpty());
		
		//move a troop to road to research to conquer the region (move all troops)
		board.moveTroops(board.getFieldByName("Central Terminal"), board.getFieldByName("Road to Research"), 2, 0);
		
		regions = board.getUsersRegions(user1);
		assertFalse(regions.isEmpty());
		assertEquals(1, regions.size());
	}
	
	@Test
	public void testGetUsersBuildings() {
		assertEquals(1, board.getUsersBuildings(user1).size());
		assertTrue(board.getUsersBuildings(user1).get(0) instanceof ArschgaulsPalace);
		
		board.getFieldByName("Jacks Office").setBuilding(new TorguesBadassDome());
		board.getFieldByName("Supply Platform").setBuilding(new ScootersCatchARide());//on the other users field
		board.getFieldByName("Power Core Alpha").setBuilding(new MoxxisTavern());//on an empty field
		
		assertEquals(2, board.getUsersBuildings(user1).size());
		assertTrue(board.getUsersBuildings(user1).contains(new TorguesBadassDome()));
		
		assertEquals(2, board.getUsersBuildings(user2).size());
		assertTrue(board.getUsersBuildings(user2).contains(new ScootersCatchARide()));
	}
}