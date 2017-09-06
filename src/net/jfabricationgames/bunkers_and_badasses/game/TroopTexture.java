package net.jfabricationgames.bunkers_and_badasses.game;

public enum TroopTexture {
	
	BANDITS(0),
	HYPERION(1),
	VAULT_GUARDS(2);
	
	private final int id;
	
	private TroopTexture(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
}