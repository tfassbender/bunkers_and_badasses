package net.jfabricationgames.bunkers_and_badasses.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import net.jfabricationgames.bunkers_and_badasses.game_character.hero.Angel;
import net.jfabricationgames.bunkers_and_badasses.game_character.hero.Arschgaul;
import net.jfabricationgames.bunkers_and_badasses.game_character.hero.Athena;
import net.jfabricationgames.bunkers_and_badasses.game_character.hero.Axton;
import net.jfabricationgames.bunkers_and_badasses.game_character.hero.Bloodwing;
import net.jfabricationgames.bunkers_and_badasses.game_character.hero.Brick;
import net.jfabricationgames.bunkers_and_badasses.game_character.hero.Claptrap;
import net.jfabricationgames.bunkers_and_badasses.game_character.hero.CrazyEarl;
import net.jfabricationgames.bunkers_and_badasses.game_character.hero.DrZed;
import net.jfabricationgames.bunkers_and_badasses.game_character.hero.Ellie;
import net.jfabricationgames.bunkers_and_badasses.game_character.hero.Hero;
import net.jfabricationgames.bunkers_and_badasses.game_character.hero.Lilith;
import net.jfabricationgames.bunkers_and_badasses.game_character.hero.Marcus;
import net.jfabricationgames.bunkers_and_badasses.game_character.hero.Maya;
import net.jfabricationgames.bunkers_and_badasses.game_character.hero.Modecai;
import net.jfabricationgames.bunkers_and_badasses.game_character.hero.Moxxi;
import net.jfabricationgames.bunkers_and_badasses.game_character.hero.MrTorgue;
import net.jfabricationgames.bunkers_and_badasses.game_character.hero.Nisha;
import net.jfabricationgames.bunkers_and_badasses.game_character.hero.Roland;
import net.jfabricationgames.bunkers_and_badasses.game_character.hero.Salvadore;
import net.jfabricationgames.bunkers_and_badasses.game_character.hero.Scooter;
import net.jfabricationgames.bunkers_and_badasses.game_character.hero.SirHammerlock;
import net.jfabricationgames.bunkers_and_badasses.game_character.hero.Springs;
import net.jfabricationgames.bunkers_and_badasses.game_character.hero.TinyTina;
import net.jfabricationgames.bunkers_and_badasses.game_character.hero.Wilhelm;
import net.jfabricationgames.bunkers_and_badasses.game_character.hero.Zero;
import net.jfabricationgames.bunkers_and_badasses.game_turn_cards.TurnGoal;
import net.jfabricationgames.bunkers_and_badasses.user.User;

public class HeroCardManager {
	
	public static final transient List<Class<? extends Hero>> HERO_CLASSES = createHeroClassList(); 
	
	private Map<User, List<Hero>> heroCards;
	
	private List<Hero> heroCardStack;
	
	private final int maxCardsPerPlayer;
	
	public HeroCardManager() {
		maxCardsPerPlayer = Game.getGameVariableStorage().getMaxHerosCards();
	}
	
	private static List<Class<? extends Hero>> createHeroClassList() {
		List<Class<? extends Hero>> heroClasses = new ArrayList<Class<? extends Hero>>();
		heroClasses.add(Angel.class);
		heroClasses.add(Arschgaul.class);
		heroClasses.add(Athena.class);
		heroClasses.add(Axton.class);
		heroClasses.add(Bloodwing.class);
		heroClasses.add(Brick.class);
		heroClasses.add(Claptrap.class);
		heroClasses.add(CrazyEarl.class);
		heroClasses.add(DrZed.class);
		heroClasses.add(Ellie.class);
		heroClasses.add(Lilith.class);
		heroClasses.add(Marcus.class);
		heroClasses.add(Maya.class);
		heroClasses.add(Modecai.class);
		heroClasses.add(Moxxi.class);
		heroClasses.add(MrTorgue.class);
		heroClasses.add(Nisha.class);
		heroClasses.add(Roland.class);
		heroClasses.add(Salvadore.class);
		heroClasses.add(Scooter.class);
		heroClasses.add(SirHammerlock.class);
		heroClasses.add(Springs.class);
		heroClasses.add(TinyTina.class);
		heroClasses.add(Wilhelm.class);
		heroClasses.add(Zero.class);
		return heroClasses;
	}
	
	/**
	 * Merge the data from the new hero card manager.
	 * 
	 * @param heroCardManager
	 * 		The new hero card manager.
	 */
	public void merge(HeroCardManager heroCardManager) {
		//if the card images are already loaded copy the references; otherwise load the images
		List<Hero> allHeroCards = new ArrayList<Hero>(heroCardManager.getHeroCardStack());
		for (User user : heroCards.keySet()) {
			allHeroCards.addAll(heroCards.get(user));
		}
		if (!heroCardStack.isEmpty() && heroCardStack.get(0).getImage() != null) {
			//copy the image references from the previous heroes to the new ones
			boolean instanceFound;
			for (Hero hero : allHeroCards) {
				instanceFound = false;
				for (Hero prev : heroCardStack) {
					if (!instanceFound && hero.getClass().equals(prev.getClass())) {
						hero.setImage(prev.getImage());
						instanceFound = true;
					}
				}
				//also search in the players heros
				if (!instanceFound) {
					for (User user : heroCards.keySet()) {
						for (Hero prev : heroCards.get(user)) {
							if (!instanceFound && hero.getClass().equals(prev.getClass())) {
								hero.setImage(prev.getImage());
								instanceFound = true;
							}
						}
					}
				}
			}
		}
		else {
			//load the images from the files
			for (Hero hero : allHeroCards) {
				hero.loadImage();
			}
		}
		heroCards = heroCardManager.getHeroCards();
		heroCardStack = heroCardManager.getHeroCardStack();
	}
	
	public void intitialize(List<User> players) {
		for (User user : players) {
			heroCards.put(user, new ArrayList<Hero>());
		}
		heroCardStack = new ArrayList<Hero>();
		//if there are to many players add every card twice or more
		while (heroCardStack.size() < maxCardsPerPlayer * players.size()) {
			for (Class<? extends Hero> hero : HERO_CLASSES) {
				try {
					heroCardStack.add(hero.newInstance());
				}
				catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}			
		}
		Collections.shuffle(heroCardStack);
	}
	
	/**
	 * Take some cards from the stack and add them to the players cards.
	 * 
	 * @param user
	 * 		The player that takes the cards.
	 * 
	 * @param cards
	 * 		The number of cards the player takes.
	 */
	public void takeCards(User user, int cards) {
		List<Hero> playersCards = heroCards.get(user);
		if (playersCards.size() + cards > maxCardsPerPlayer) {
			throw new IllegalArgumentException("The player want's to take more cards than allowed.");
		}
		for (int i = 0; i < cards; i++) {
			//take the last element to avoid moving the whole array list
			playersCards.add(heroCardStack.get(heroCardStack.size()-1));
		}
	}
	/**
	 * Put back the cards after they were played and shuffle the stack.
	 * 
	 * @param heroCards
	 * 		The cards that go back to the stack.
	 */
	public void putBackCards(Hero... heroCards) {
		for (Hero hero : heroCards) {
			heroCardStack.add(hero);
		}
		Collections.shuffle(heroCardStack);
	}
	
	public List<Hero> getHeroCards(User user) {
		return heroCards.get(user);
	}
	
	private List<Hero> getHeroCardStack() {
		return heroCardStack;
	}

	public Map<User, List<Hero>> getHeroCards() {
		return heroCards;
	}
	public void setHeroCards(Map<User, List<Hero>> heroCards) {
		this.heroCards = heroCards;
	}
}