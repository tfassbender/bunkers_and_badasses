package net.jfabricationgames.bunkers_and_badasses.game_board;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import net.jfabricationgames.bunkers_and_badasses.game_character.building.ArschgaulsPalace;
import net.jfabricationgames.bunkers_and_badasses.game_character.building.RolandsTurret;
import net.jfabricationgames.bunkers_and_badasses.game_command.DefendCommand;
import net.jfabricationgames.bunkers_and_badasses.login.SimpleLoginClientInterpreter;

public class FieldTest {
	
	@BeforeClass
	public static void loadVariables() {
		SimpleLoginClientInterpreter.loadDynamicVariables();
	}
	
	@Test
	public void testGetTroopStrength() {
		Field attackingField = new Field();
		Field defendingField = new Field();
		attackingField.addNormalTroops(5);
		attackingField.addBadassTroops(2);
		defendingField.addNormalTroops(3);
		defendingField.addBadassTroops(1);
		//add a defending command to the defending field (which should be ignored here)
		defendingField.setCommand(new DefendCommand());
		
		assertEquals(9, attackingField.getTroopStrength());
		assertEquals(5, defendingField.getTroopStrength());
	}
	
	@Test
	public void testGetDefenceStrength() {
		Field defendingField = new Field();
		defendingField.addNormalTroops(3);
		defendingField.addBadassTroops(1);
		assertEquals(5, defendingField.getDefenceStrength());
		
		//add a defending command to the defending field
		defendingField.setCommand(new DefendCommand());
		assertEquals(6, defendingField.getDefenceStrength());
		
		//add a turret as additional defense
		defendingField.setBuilding(new RolandsTurret());
		assertEquals(7, defendingField.getDefenceStrength());
		
		//upgrade the turret
		defendingField.getBuilding().extend();
		
		assertEquals(8, defendingField.getDefenceStrength());
	}
	
	@Test
	public void testIsCommandPlaceable() {
		Field field = new Field();
		Field field2 = new Field();
		Field field3 = new Field();
		field.setBuilding(new ArschgaulsPalace());
		field2.addNormalTroops(1);
		
		assertTrue(field.isCommandPlaceable());
		assertTrue(field2.isCommandPlaceable());
		assertFalse(field3.isCommandPlaceable());
	}
}