package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

public class MrTorgue extends Hero {
	
	private static final long serialVersionUID = 2488254569700119667L;

	public MrTorgue() {
		attack = 3;
		defence = 3;
		name = "Mr. Trogue";
		image = imageLoader.loadImage("mr_torgue_1.png");
		effectDescription = "EXPLOSIONSGER�USCH:\n\n3 normale Truppen (irgentwo auf dem Feld) werden zu Badasses aufger�stet";
	}
	
	@Override
	public void executeTurn() {
		//TODO
		//EXPLOSIONSGER�USCH: 3 normale Truppen werden zu Badasses aufger�stet
	}
}