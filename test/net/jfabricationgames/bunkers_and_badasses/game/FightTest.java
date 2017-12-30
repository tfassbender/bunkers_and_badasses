package net.jfabricationgames.bunkers_and_badasses.game;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.jfabricationgames.bunkers_and_badasses.game_character.hero.MrTorgue;
import net.jfabricationgames.bunkers_and_badasses.game_character.hero.SirHammerlock;
import net.jfabricationgames.bunkers_and_badasses.game_character.hero.TinyTina;
import net.jfabricationgames.bunkers_and_badasses.game_command.DefendCommand;
import net.jfabricationgames.bunkers_and_badasses.login.SimpleLoginClientInterpreter;
import net.jfabricationgames.bunkers_and_badasses.user.User;

public class FightTest {
	
	private Fight fight;
	
	@BeforeClass
	public static void loadVariables() {
		SimpleLoginClientInterpreter.loadDynamicVariables();
	}
	
	@Before
	public void initFight() {
		fight = new Fight();
		
		//fields of the fight
		Field attackingField = new Field();
		Field defendingField = new Field();
		attackingField.addNormalTroops(5);
		attackingField.addBadassTroops(2);
		defendingField.addNormalTroops(3);
		defendingField.addBadassTroops(1);
		fight.setAttackingField(attackingField);
		fight.setDefendingField(defendingField);
		
		//add a defending command to the defending field
		defendingField.setCommand(new DefendCommand());
		
		//direct attacking troops
		fight.setAttackingNormalTroops(3);
		fight.setAttackingBadassTroops(1);
		
		//attacking support fields
		Field field = new Field();
		field.addNormalTroops(2);
		field.addBadassTroops(2);
		Field field2 = new Field();
		field2.addNormalTroops(0);
		field2.addBadassTroops(1);
		fight.setAttackSupporters(Arrays.asList(field, field2));
		
		//defending support fields
		Field field3 = new Field();
		field3.addNormalTroops(1);
		field3.addBadassTroops(1);
		fight.setDefenceSupporters(Arrays.asList(field3));
		
		//add heros
		fight.setAttackingHero(new SirHammerlock());//atk:1 def:2
		fight.setAttackingHeroChosen(true);
		fight.setUseAttackingHeroEffect(false);//use the strength, not the effect
		
		fight.setDefendingHero(new MrTorgue());//atk:3 def:3
		fight.setDefendingHeroChosen(true);
		fight.setUseDefendingHeroEffect(false);//use the strength, not the effect
	}
	
	@Test
	public void testMerge() {
		Fight fight2 = new Fight();
		User attacker = new User("attacker");
		User defender = new User("defender");
		fight2.setAttackingPlayer(attacker);
		fight2.setDefendingPlayer(defender);
		fight2.setAttackingNormalTroops(3);
		fight2.setAttackingBadassTroops(5);
		fight2.setDefendingField(new Field());
		fight2.getDefendingField().addNormalTroops(3);
		fight2.getDefendingField().addBadassTroops(3);
		fight2.setBattleState(Fight.STATE_SUPPORT);
		fight2.setAttackingHero(new TinyTina());
		fight2.setDefendingHeroChosen(false);
		
		fight.merge(fight2);
		
		assertEquals(attacker, fight.getAttackingPlayer());
		assertEquals(defender, fight.getDefendingPlayer());
		
		fight.calculateCurrentStrength();
		//troops without heros (also no support)
		assertEquals(13, fight.getAttackingStrength());
		assertEquals(9, fight.getDefendingStrength());
		
		assertEquals(Fight.STATE_SUPPORT, fight.getBattleState());
		assertEquals(new TinyTina(), fight2.getAttackingHero());
		assertFalse(fight2.isDefendingHeroChosen());
	}
	
	@Test
	public void testGetAttackingStrength() {
		fight.setBattleState(Fight.STATE_FIGHT_ENDED);
		fight.calculateCurrentStrength();
		assertEquals(14, fight.getAttackingStrength());
	}
	
	@Test
	public void testGetDefendingStrength() {
		fight.setBattleState(Fight.STATE_FIGHT_ENDED);
		fight.calculateCurrentStrength();
		assertEquals(12, fight.getDefendingStrength());
	}
	
	@Test
	public void testGetAttackingTroopStrength() {
		assertEquals(5, fight.getAttackingTroopStrength());
	}
	
	@Test
	public void testGetDefendingTroopStrength() {
		assertEquals(5, fight.getDefendingTroopStrength());
	}
	
	@Test
	public void testCalculateFallingTroops() {
		fight.setBattleState(Fight.STATE_FALLEN_TROOP_SELECTION);
		fight.calculateCurrentStrength();
		fight.calculateWinner();
		assertArrayEquals(new int[] {2, 4}, fight.calculateFallingTroops());
		
		//some more tests
		fight.setAttackingNormalTroops(5);
		fight.getDefendingField().addBadassTroops(1);
		fight.calculateCurrentStrength();
		fight.calculateWinner();
		assertArrayEquals(new int[] {3, 6}, fight.calculateFallingTroops());
		
		fight.setAttackingNormalTroops(6);
		fight.calculateCurrentStrength();
		fight.calculateWinner();
		assertArrayEquals(new int[] {3, 7}, fight.calculateFallingTroops());
	}
	
	@Test
	public void testCalculateMaxFallingSupportTroops() {
		fight.setBattleState(Fight.STATE_FALLEN_TROOP_SELECTION);
		fight.calculateCurrentStrength();
		fight.calculateWinner();
		assertEquals(2, fight.calculateMaxFallingSupportTroops(4, fight.getDefenceSupporters().get(0)));
		
		fight.setAttackingNormalTroops(5);
		fight.getDefendingField().addBadassTroops(1);
		fight.calculateCurrentStrength();
		fight.calculateWinner();
		assertArrayEquals(new int[] {3, 6}, fight.calculateFallingTroops());
		assertEquals(2, fight.calculateMaxFallingSupportTroops(6, fight.getDefenceSupporters().get(0)));
	}
	
	@Test
	public void testCalculateFallingTroopsSkagFight() {
		fight.getDefendingField().removeBadassTroops(1);//neutrals don't have badasses
		fight.setAttackingHero(null);//no heros in neutral fights
		fight.setDefendingHero(null);
		fight.setDefenceSupporters(new ArrayList<Field>());
		fight.getDefendingField().setCommand(null);
		fight.calculateCurrentStrength();
		fight.calculateWinner();
		assertEquals(3, fight.calculateFallingTroopsSkagFight());
		
		//another test
		fight.setAttackingBadassTroops(0);
		fight.setAttackingNormalTroops(2);
		assertEquals(1, fight.calculateFallingTroopsSkagFight());
	}
	
	@Test
	public void testCalculateFallingTroopsSkagFightLost() {
		fight.getDefendingField().removeBadassTroops(1);//neutrals don't have badasses
		fight.setAttackingBadassTroops(0);
		fight.setAttackingHero(null);//no heros in neutral fights
		fight.setDefendingHero(null);
		fight.setDefenceSupporters(new ArrayList<Field>());
		fight.getDefendingField().setCommand(null);
		fight.calculateCurrentStrength();
		fight.calculateWinner();
		assertArrayEquals(new int[] {3, 2}, fight.calculateFallingTroopsSkagFightLost());
	}
	
	@Test
	public void testCalculateRetreatFields() {
		User attacker = new User("attacker");
		User defender = new User("defender");
		User third = new User("third");
		Field neighbour1 = new Field();
		neighbour1.setAffiliation(defender);
		Field neighbour2 = new Field();
		neighbour2.setAffiliation(defender);
		Field neighbour3 = new Field();
		neighbour3.setAffiliation(third);
		Field neighbour4 = new Field();
		neighbour4.setAffiliation(attacker);
		fight.getDefendingField().setNeighbours(Arrays.asList(neighbour1, neighbour2, neighbour3, neighbour4));
		fight.setBattleState(Fight.STATE_RETREAT_FIELD);
		fight.calculateCurrentStrength();
		fight.calculateWinner();
		fight.setDefendingPlayer(defender);
		fight.setAttackingPlayer(attacker);
		
		List<Field> retreat = fight.calculateRetreatFields();
		
		//can't check for equal fields (not all info in fields to check equality)
		assertEquals(2, retreat.size());
	}
	
	@Test
	public void testRetreatPossible() {
		User attacker = new User("attacker");
		User defender = new User("defender");
		User third = new User("third");
		Field neighbour1 = new Field();
		neighbour1.setAffiliation(defender);
		Field neighbour2 = new Field();
		neighbour2.setAffiliation(defender);
		Field neighbour3 = new Field();
		neighbour3.setAffiliation(third);
		Field neighbour4 = new Field();
		neighbour4.setAffiliation(attacker);
		
		fight.getDefendingField().setNeighbours(Arrays.asList(neighbour4));
		fight.setBattleState(Fight.STATE_RETREAT_FIELD);
		fight.setDefendingPlayer(defender);
		fight.setAttackingPlayer(attacker);
		fight.calculateCurrentStrength();
		fight.calculateWinner();
		assertFalse(fight.retreatPossible());
		
		fight.getDefendingField().setNeighbours(Arrays.asList(neighbour3, neighbour4));
		assertFalse(fight.retreatPossible());
		
		fight.getDefendingField().setNeighbours(Arrays.asList(neighbour2, neighbour3, neighbour4));
		assertTrue(fight.retreatPossible());

		fight.getDefendingField().setNeighbours(Arrays.asList(neighbour1, neighbour2, neighbour3));
		assertTrue(fight.retreatPossible());
		
		fight.getDefendingField().setNeighbours(Arrays.asList(neighbour1));
		assertTrue(fight.retreatPossible());
	}
	
	@Test
	public void testCalculateWinner() {
		fight.calculateCurrentStrength();
		fight.calculateWinner();
		assertEquals(Fight.ATTACKERS, fight.getWinner());
	}
	
	@Test
	public void testGetLoosingSupporters() {
		fight.calculateCurrentStrength();
		fight.calculateWinner();
		assertEquals(1, fight.getLoosingSupporters().size());
	}
}