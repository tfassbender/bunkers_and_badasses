package net.jfabricationgames.bunkers_and_badasses.game;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.BeforeClass;
import org.junit.Test;

import net.jfabricationgames.bunkers_and_badasses.error.ResourceException;
import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.jfabricationgames.bunkers_and_badasses.game_command.MarchCommand;
import net.jfabricationgames.bunkers_and_badasses.game_command.SupportCommand;
import net.jfabricationgames.bunkers_and_badasses.login.SimpleLoginClientInterpreter;
import net.jfabricationgames.bunkers_and_badasses.user.User;

public class UserResourceTest {
	
	@BeforeClass
	public static void initDynamicVariables() {
		SimpleLoginClientInterpreter.loadDynamicVariables();
	}
	
	@Test
	public void testGetCreditsForCommand() {
		//the first example in the manual
		Field field = new Field();
		field.addNormalTroops(1);
		field.addBadassTroops(1);
		assertEquals(22, UserResource.getCreditsForCommand(new MarchCommand(), field));
		
		//the second example in the manual
		Field field2 = new Field();
		field2.addNormalTroops(3);
		field2.addBadassTroops(2);
		assertEquals(75, UserResource.getCreditsForCommand(new SupportCommand(), field2));
	}
	
	@Test
	public void testGetAmmoForCommand() {
		//the first example in the manual
		Field field = new Field();
		field.addNormalTroops(1);
		field.addBadassTroops(1);
		assertEquals(41, UserResource.getAmmoForCommand(new MarchCommand(), field));
		
		//the second example in the manual
		Field field2 = new Field();
		field2.addNormalTroops(3);
		field2.addBadassTroops(2);
		assertEquals(135, UserResource.getAmmoForCommand(new SupportCommand(), field2));
	}
	
	@Test
	public void testCollectSkillResources() {
		SkillProfile skill = new SkillProfile();
		UserResource resource = new UserResource(new User("test"), null);
		
		//__global_user_skill__ (default profile)
		skill.setPoints(1);
		skill.setEridium(2);
		skill.setCredits(2);
		skill.setAmmo(2);
		skill.setEridiumBuilding(2);
		skill.setAmmoBuilding(2);
		skill.setCreditsBuilding(2);
		skill.setHero(0);
		resource.collectSkillResources(skill);
		
		assertEquals(50, resource.getCredits());
		assertEquals(75, resource.getAmmo());
		assertEquals(5, resource.getEridium());
		
		//another profile...
		skill.setPoints(1);
		skill.setEridium(3);
		skill.setCredits(2);
		skill.setAmmo(1);
		skill.setEridiumBuilding(2);
		skill.setAmmoBuilding(1);
		skill.setCreditsBuilding(1);
		skill.setHero(2);
		resource = new UserResource(new User("test"), null);
		resource.collectSkillResources(skill);
		
		assertEquals(50, resource.getCredits());
		assertEquals(25, resource.getAmmo());
		assertEquals(10, resource.getEridium());
	}
	
	@Test
	public void testCollectCommandResources() {
		UserResource resource = new UserResource(new User("test"), null);
		
		resource.collectCommandResources(1);//credits
		assertEquals(50, resource.getCredits());
		
		resource.collectCommandResources(2);//ammo
		assertEquals(75, resource.getAmmo());
		
		resource.collectCommandResources(3);//eridium
		assertEquals(10, resource.getEridium());
	}
	
	@Test
	public void testPayCommand() {
		UserResource resource = new UserResource(new User("test"), null);
		
		//add some resources
		resource.setCredits(100);
		resource.setAmmo(200);
		
		//the first example in the manual
		Field field = new Field();
		field.addNormalTroops(1);
		field.addBadassTroops(1);
		
		//the second example in the manual
		Field field2 = new Field();
		field2.addNormalTroops(3);
		field2.addBadassTroops(2);
		
		resource.payCommand(new MarchCommand(), field);
		assertEquals(78, resource.getCredits());
		assertEquals(159, resource.getAmmo());
		
		resource.payCommand(new SupportCommand(), field2);
		assertEquals(3, resource.getCredits());
		assertEquals(24, resource.getAmmo());
		
		try {
			//try to pay the second command again (not enough resources)
			resource.payCommand(new SupportCommand(), field2);
			fail("A ResourceException was expected here...");
		}
		catch (ResourceException re) {
			//do nothing here because the exception was expected
		}
	}
	
	@Test
	public void testPayBackCommand() {
		UserResource resource = new UserResource(new User("test"), null);
		
		//add some resources
		resource.setCredits(100);
		resource.setAmmo(200);
		
		//the first example in the manual
		Field field = new Field();
		field.addNormalTroops(1);
		field.addBadassTroops(1);
		
		//the second example in the manual
		Field field2 = new Field();
		field2.addNormalTroops(3);
		field2.addBadassTroops(2);
		
		resource.payCommand(new MarchCommand(), field);
		resource.payCommand(new SupportCommand(), field2);
		
		//pay back the commands in reverse order
		resource.payBackCommand(new MarchCommand(), field);
		assertEquals(25, resource.getCredits());
		assertEquals(65, resource.getAmmo());
		
		resource.payBackCommand(new SupportCommand(), field2);
		assertEquals(100, resource.getCredits());
		assertEquals(200, resource.getAmmo());
	}
	
	@Test
	public void testGetRecroutedTroopCosts() {
		assertArrayEquals(new int[] {15, 25, 0}, UserResource.getRecroutedTroopCosts(1, 1, 0));
		assertArrayEquals(new int[] {15, 25, 0}, UserResource.getRecroutedTroopCosts(0, 1, 1));
		assertArrayEquals(new int[] {20, 35, 0}, UserResource.getRecroutedTroopCosts(1, 1, 1));
		assertArrayEquals(new int[] {45, 75, 0}, UserResource.getRecroutedTroopCosts(1, 3, 2));
	}
	
	@Test
	public void testPayFields() {
		UserResource resource = new UserResource(new User("test"), null);
		resource.setCredits(50);
		resource.payFields(9);
		
		assertEquals(5, resource.getCredits());
		
		resource.payFields(5);
		//not enough credits left for the command (can't get under zero; no exception)
		assertEquals(0, resource.getCredits());
	}
}