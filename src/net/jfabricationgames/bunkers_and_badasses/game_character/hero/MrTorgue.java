package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

public class MrTorgue extends Hero {
	
	static {
		staticImage = imageLoader.loadImage("mr_torgue_1.png");
	}
	
	public MrTorgue() {
		attack = 3;
		defence = 3;
	}
	
	@Override
	public void executeTurn() {
		//TODO
		//EXPLOSIONSGERÄUSCH: 3 normale Truppen werden zu Badasses aufgerüstet
	}
}