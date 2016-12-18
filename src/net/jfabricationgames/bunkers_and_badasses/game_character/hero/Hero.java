package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

import net.jfabricationgames.bunkers_and_badasses.game.Fight;

public abstract class Hero {
	
	protected HeroEffectTime time;
	
	protected int attack;
	protected int defence;
	
	protected int usedAttack;
	protected int usedDefence;
	
	public abstract void executeFight(Fight fight);
}