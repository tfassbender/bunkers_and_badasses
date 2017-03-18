package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

public interface HeroSelectionListener {
	
	/**
	 * Receive the hero card that was selected by the frame or null if none was selected.
	 * 
	 * @param hero
	 * 		The selected hero card.
	 */
	public void receiveSelectedHero(Hero hero);
}